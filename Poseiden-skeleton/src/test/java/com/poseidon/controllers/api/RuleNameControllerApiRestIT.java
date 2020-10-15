package com.poseidon.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.services.IRuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RuleNameControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IRuleNameService ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ruleNameRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void givenValidInfos_whenCreate_thenReturnCreated()
            throws Exception {

        RuleName rulenameToCreate = new RuleName("name", "description", "json",
                "template", "sql str", "sql part");
        String jsonContent = objectMapper.writeValueAsString(rulenameToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rulename")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK -  Empty attributes")
    public void givenEmptyAttributes_whenCreate_thenReturnCreated()
            throws Exception {

        RuleName rulenameToCreate = new RuleName("name", "", "", "", "", "");
        String jsonContent = objectMapper.writeValueAsString(rulenameToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rulename")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR -  attributes size > max")
    public void givenDescriptionsuperiorThanMaxSizeAllowed_whenCreate_thenReturnBadRequest()
            throws Exception {

        RuleName rulenameToCreate = new RuleName("name",
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456 dfhh",
                "json", "template", "sql str", "sql part");
        String jsonContent = objectMapper.writeValueAsString(rulenameToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/rulename")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 ruleName in db")
    public void givenTwoRuleNameInDb_whenGet_thenReturnListWithTwoRuleName()
            throws Exception {
        ruleNameService.saveRuleName(new RuleName("name", "description", "json",
                "template", "sql str", "sql part"));
        ruleNameService.saveRuleName(new RuleName("name 2", "description 2",
                "json 2", "template 2", "sql str 2", "sql part 2"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/rulename")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 ruleName in db")
    public void givenZeroRuleNameInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/rulename")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Invalid id")
    public void givenRulenameToUpdate_whenInvalidId_thenReturnBadRequest()
            throws Exception {
        ruleNameService.saveRuleName(new RuleName("name", "description", "json",
                "template", "sql str", "sql part"));
        ruleNameRepository.findAll().get(0).setId(1);

        RuleName rulenameToUpdate = new RuleName("name", "description", "json",
                "template", "sql str", "sql part");
        String jsonContent = objectMapper.writeValueAsString(rulenameToUpdate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put("/api/rulename")
                        .contentType(APPLICATION_JSON).param("id", "99")
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow id")
    public void givenZeroRuleName_whenDeleteInvalidId_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/rulename")
                        .contentType(APPLICATION_JSON).param("id", "99"))
                .andExpect(status().isBadRequest());
    }
}
