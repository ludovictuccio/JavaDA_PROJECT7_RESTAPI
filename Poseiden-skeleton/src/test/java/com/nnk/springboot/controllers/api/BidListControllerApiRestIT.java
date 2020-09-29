package com.nnk.springboot.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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
import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;
import com.nnk.springboot.services.IBidListService;

@SpringBootTest
@AutoConfigureMockMvc
//@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BidListControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IBidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @BeforeEach
    public void setUpPerTest() {
        bidListRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void givenValidInfos_whenPost_thenReturnCreated() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/bidList/add")
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
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList/add")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "gg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - negative")
    public void givenNegativeQuantity_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList/add")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "-50"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - character")
    public void givenCharacterForQuantity_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList/add")
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
                .perform(MockMvcRequestBuilders.post("/api/bidList/add")
                        .contentType(APPLICATION_JSON).param("bidAccount", "")
                        .param("bidType", "type").param("bidQuantity", "100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad Type - Empty")
    public void givenEmptyType_whenPost_thenReturnBadRequest()
            throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/api/bidList/add")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "").param("bidQuantity", "100"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("Get")
    @DisplayName("Get - OK - 1 bid in db")
    public void givenOneBidInDb_whenGet_thenReturnListWithOneBid()
            throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/bidList/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("Get")
    @DisplayName("Get - OK - 0 bid in db")
    public void givenZeroBidInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/bidList/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("PUT")
    @DisplayName("Put - OK")
    public void aaaaa() throws Exception {
        BidList bid = new BidList();
        bid.setAccount("Account");
        bid.setType("Type");
        bid.setBidQuantity(15d);
        bidListRepository.save(bid);

        BidList bidToUpdate = new BidList(
                bidListRepository.findAll().get(0).getBidListId(),
                "NEW ACCOUNT", "NEW TYPE", 1500d, null, null, null, null, null,
                "NEW BOOK", null, null, null, null, null, null, null, null,
                null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList/update")
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
                bidListRepository.findAll().get(0).getBidListId(),
                "NEW ACCOUNT", "NEW TYPE", 1500d, null, null, null, null, null,
                "NEW BOOK", null, null, null, null, null, null, null, null,
                null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList/update")
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
                bidListRepository.findAll().get(0).getBidListId(),
                "NEW ACCOUNT", "NEW TYPE", 1500d, null, null, null, null, null,
                "NEW BOOK", null, null, null, null, null, null, null, null,
                null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList/update")
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

        BidList bidToUpdate = new BidList(99999, "NEW ACCOUNT", "NEW TYPE",
                1500d, null, null, null, null, null, "NEW BOOK", null, null,
                null, null, null, null, null, null, null, null, null, null);
        String jsonContent = objectMapper.writeValueAsString(bidToUpdate);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/api/bidList/update")
                .contentType(APPLICATION_JSON).param("bidAccount", "Account")
                .param("bidType", "Type").content(jsonContent))
                .andExpect(status().isBadRequest());
    }

}
