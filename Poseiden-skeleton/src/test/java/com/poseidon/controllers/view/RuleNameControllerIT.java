package com.poseidon.controllers.view;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.services.RuleNameService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class RuleNameControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RuleNameService ruleNameService;

    @Autowired
    private RuleNameRepository ruleNameRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        ruleNameRepository.deleteAll();
    }

    @Test
    @Tag("/ruleName/list")
    @DisplayName("Get - list")
    public void givenZeroRulename_whenGetList_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ruleName/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/ruleName/add")
    @DisplayName("Get - add")
    public void givenRulename_whenGetAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ruleName/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("ruleName"))
                .andReturn();
    }

    @Test
    @Tag("/ruleName/validate")
    @DisplayName("Post - validate - OK")
    public void givenValidRuleName_whenValidate_thenReturnSaved()
            throws Exception {

        RuleName rulename = new RuleName("name", "description", "json",
                "template", "sql str", "sql part");
        String jsonContent = objectMapper.writeValueAsString(rulename);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/ruleName/validate")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
                .andReturn();
    }

    @Test
    @Tag("/ruleName/update/{id}")
    @DisplayName("Post - update")
    public void givenOneRuleName_whenUpdate_thenReturnUpdated()
            throws Exception {

        RuleName rulename = new RuleName("name", "description", "json",
                "template", "sql str", "sql part");
        ruleNameRepository.save(rulename);
        String jsonContent = objectMapper.writeValueAsString(rulename);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/ruleName/update/1")
                        .contentType(MediaType.ALL).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
                .andReturn();
    }

    @Test
    @Tag("/ruleName/delete")
    @DisplayName("Delete - OK")
    public void givenRuleName_whenDelete_thenReturnDeleted() throws Exception {

        RuleName rulename = new RuleName("name", "description", "json",
                "template", "sql str", "sql part");
        ruleNameRepository.save(rulename);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/ruleName/delete/1")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/ruleName/list"))
                .andReturn();
    }

}
