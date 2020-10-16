package com.poseidon.controllers.api;

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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import com.poseidon.services.ITradeService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class TradeControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ITradeService tradeService;

    @Autowired
    private TradeRepository tradeRepository;

    private static String uri = "/v1/trade";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        tradeRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void givenValidInfos_whenCreate_thenReturnCreated()
            throws Exception {

        Trade tradeToCreate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.of(1999, 11, 11, 11, 01), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side");
        tradeToCreate.setTradeId(1);
        String jsonContent = objectMapper.writeValueAsString(tradeToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Revision name entry")
    public void givenRevisionName_whenCreate_thenReturnBadRequest()
            throws Exception {

        Trade tradeToCreate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.of(1999, 11, 11, 11, 01), "security", "status",
                "trader", "benchmark", "book", "creationName", "revision name",
                "dealName", "dealType", "sourceListId", "side");
        tradeToCreate.setTradeId(1);
        String jsonContent = objectMapper.writeValueAsString(tradeToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - No account entry")
    public void givenNoAccount_whenCreate_thenReturnBadRequest()
            throws Exception {

        Trade tradeToCreate = new Trade("", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.of(1999, 11, 11, 11, 01), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side");
        tradeToCreate.setTradeId(1);
        String jsonContent = objectMapper.writeValueAsString(tradeToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Trade date after actual date ")
    public void givenBadTradeDate_whenCreate_thenReturnBadRequest()
            throws Exception {

        Trade tradeToCreate = new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().plusMonths(2), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side");
        tradeToCreate.setTradeId(1);
        String jsonContent = objectMapper.writeValueAsString(tradeToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 trades in db")
    public void givenTwoTradesInDb_whenGet_thenReturnListWithTwoTrades()
            throws Exception {
        tradeService.saveTrade(new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side"));
        tradeService.saveTrade(new Trade("account 2", "type2", 20d, 20d, 20d,
                20d, LocalDateTime.now().minusMonths(2), "security2", "status",
                "trader", "benchmark", "book2", "creationName2", "",
                "dealName2", "dealType2", "sourceListId", "side2"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 trade in db")
    public void givenZeroTradesInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Invalid id")
    public void givenTradeToUpdate_whenInvalidId_thenReturnBadRequest()
            throws Exception {
        tradeService.saveTrade(new Trade("account", "type", 10d, 10d, 10d, 10d,
                LocalDateTime.now().minusMonths(2), "security", "status",
                "trader", "benchmark", "book", "creationName", "", "dealName",
                "dealType", "sourceListId", "side"));

        Trade tradeToUpdate = new Trade("account 2", "type2", 20d, 20d, 20d,
                20d, LocalDateTime.now().minusMonths(2), "security2", "status",
                "trader", "benchmark", "book2", "creationName2", "",
                "dealName2", "dealType2", "sourceListId", "side2");
        String jsonContent = objectMapper.writeValueAsString(tradeToUpdate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.put(uri)
                        .contentType(APPLICATION_JSON).param("id", "99")
                        .content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow id")
    public void givenZeroTrade_whenDeleteInvalidId_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(APPLICATION_JSON).param("id", "99"))
                .andExpect(status().isBadRequest());
    }

}
