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

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.services.BidListService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class BidListControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private BidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        bidListRepository.deleteAll();
    }

    @Test
    @Tag("/bidList/list")
    @DisplayName("Get - list")
    public void givenBidList_whenGetList_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bidList/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().attribute("bidList",
                        bidListService.findAllBids()))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/bidList/add")
    @DisplayName("Get - add")
    public void givenGetMapping_whenAdd_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/bidList/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("bidList"))
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("/bidList/validate")
    @DisplayName("Post - validate")
    public void givenPostValidate_whenPost_thenreturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .contentType(APPLICATION_JSON)
                        .param("account", "account").param("type", "type")
                        .param("bidQuantity", "15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"))
                .andReturn();
    }

    @Test
    @Tag("/bidList/validate")
    @DisplayName("Post - validate - error - letter for quantity")
    public void givenPostValidate_whenPostInvaliQuantity_thenreturnError()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .contentType(APPLICATION_JSON)
                        .param("account", "account").param("type", "type")
                        .param("bidQuantity", "letters"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
                .andReturn();
    }

    @Test
    @Tag("/bidList/validate")
    @DisplayName("Post - validate - error - empty account")
    public void givenPostValidate_whenPostEmptyAccount_thenreturnError()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/bidList/validate")
                        .contentType(APPLICATION_JSON).param("account", "")
                        .param("type", "type").param("bidQuantity", "15"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.view().name("bidList/add"))
                .andReturn();
    }

    @Test
    @Tag("/bidList/update/{id}")
    @DisplayName("Post - update")
    public void givenUpdate_whenPostWithValidInfos_thenreturnOk()
            throws Exception {
        BidList bid = new BidList();
        bid.setBidListId(1);
        bid.setAccount("account");
        bid.setType("type");
        bid.setBidQuantity(10d);
        bidListRepository.save(bid);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/bidList/update/1")
                .contentType(MediaType.ALL).param("account", "new account")
                .param("type", "new type").param("bidQuantity", "15000"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/bidList/list"))
                .andReturn();
    }

}
