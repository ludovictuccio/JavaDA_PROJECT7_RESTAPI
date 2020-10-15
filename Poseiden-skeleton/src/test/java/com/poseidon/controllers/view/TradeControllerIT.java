package com.poseidon.controllers.view;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

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
import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private TradeRepository tradeRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        tradeRepository.deleteAll();
    }

    @Test
    @Tag("/trade/list")
    @DisplayName("Get - list")
    public void givenZeroTrade_whenGetList_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/trade/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/trade/add")
    @DisplayName("Get - add")
    public void givenTrade_whenGetAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/trade/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("trade"))
                .andReturn();
    }

    @Test
    @Tag("/trade/validate")
    @DisplayName("Post - validate - ERROR - Blank creation name")
    public void givenBlankCreationName_whenValidate_thenReturnSaved()
            throws Exception {

        Trade trade = new Trade("account", "type", 100d, 10d, null, null,
                LocalDateTime.now().minusMonths(10), null, null, null, null,
                null, "", null, null, null, null, null);

        String jsonContent = objectMapper.writeValueAsString(trade);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/trade/validate")
                        .contentType(MediaType.ALL).content(jsonContent))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.view().name("trade/add"))
                .andReturn();
    }

    @Test
    @Tag("/trade/delete")
    @DisplayName("Delete - OK")
    public void givenTrade_whenDelete_thenReturnDeleted() throws Exception {

        Trade trade = new Trade("account", "type", 100d, 10d, null, null,
                LocalDateTime.now().minusMonths(10), null, null, null, null,
                null, "creation name", null, null, null, null, null);
        tradeRepository.save(trade);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/trade/delete/1")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/trade/list"))
                .andReturn();
    }

    @Test
    @Tag("/trade/delete")
    @DisplayName("Delete - Error - Invalid id")
    public void aaaa() throws Exception {

        Trade trade = new Trade("account", "type", 100d, 10d, null, null,
                LocalDateTime.now().minusMonths(10), null, null, null, null,
                null, "creation name", null, null, null, null, null);
        tradeRepository.save(trade);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/trade/delete/7777777")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print()).andReturn();

    }

}
