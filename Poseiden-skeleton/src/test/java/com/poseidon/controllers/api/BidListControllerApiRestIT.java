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
import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BidListControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        bidListRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void givenValidInfos_whenPost_thenReturnCreated() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/bidList")
                        .contentType(APPLICATION_JSON)
                        .param("bidAccount", "myAccount")
                        .param("bidType", "type").param("bidQuantity", "15"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - letters")
    public void givenLettersForQuantity_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "gg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - negative")
    public void givenNegativeQuantity_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "-50"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - character")
    public void givenCharacterForQuantity_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "*"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad Account - Empty")
    public void givenEmptyAccount_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/bidList")
                        .contentType(APPLICATION_JSON).param("bidAccount", "")
                        .param("bidType", "type").param("bidQuantity", "100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad Type - Empty")
    public void givenEmptyType_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "").param("bidQuantity", "100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 1 bid in db")
    public void givenOneBidInDb_whenGet_thenReturnListWithOneBid()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/bidList")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 bid in db")
    public void givenZeroBidInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/bidList")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - OK")
    public void givenBidToUpdate_whenCorrectValues_thenReturnOk()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(
                bidListRepository.findAll().get(0).getBidListId(), "Account",
                "Type", 15d, null, null, null, null,
                LocalDateTime.now().minusMonths(1), null, null, null, null,
                "NEW BOOK", null, "revision name", null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "Type").content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Bad account param")
    public void givenBidToUpdate_whenBadAccountInParam_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(
                bidListRepository.findAll().get(0).getBidListId(), "Account",
                "Type", 15d, null, null, null, null,
                LocalDateTime.now().minusMonths(1), null, null, null, null,
                "NEW BOOK", null, "revision name", null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "other")
                .param("bidType", "Type").content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Bad type param")
    public void givenBidToUpdate_whenBadTypeInParam_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(
                bidListRepository.findAll().get(0).getBidListId(), "Account",
                "Type", 15d, null, null, null, null,
                LocalDateTime.now().minusMonths(1), null, null, null, null,
                "NEW BOOK", null, "revision name", null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "other").content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Id different in bid content")
    public void givenBidToUpdate_whenBadIdInBidContent_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(9999, "Account", "Type", 15d, null,
                null, null, null, LocalDateTime.now().minusMonths(1), null,
                null, null, null, "NEW BOOK", null, "revision name", null, null,
                null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "Type").content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - ERROR - Bidlist date after actual date")
    public void givenBidlistDateInvalid_whenBadIdInBidContent_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(9999, "Account", "Type", 15d, null,
                null, null, null, LocalDateTime.now().plusMonths(1), null, null,
                null, null, "NEW BOOK", null, "revision name", null, null, null,
                null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "Type").content(jsonContent))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Empty Account")
    public void givenBidToDelete_whenBadAccount_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);
        bidListRepository.findAll().get(0).setBidListId(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/bidList")
                        .contentType(APPLICATION_JSON).param("bidId", "1")
                        .param("bidAccount", ""))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Bad id")
    public void givenBidToDelete_whenBadId_thenReturnBadRequest()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);
        bidListRepository.findAll().get(0).setBidListId(1);

        this.mockMvc
                .perform(MockMvcRequestBuilders.delete("/api/bidList")
                        .contentType(APPLICATION_JSON).param("bidId", "-15")
                        .param("bidAccount", "Account"))
                .andExpect(status().isBadRequest());
    }

}
