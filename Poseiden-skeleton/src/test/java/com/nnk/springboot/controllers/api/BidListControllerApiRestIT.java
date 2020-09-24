package com.nnk.springboot.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
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

    // @Autowired
    // private ObjectMapper objectMapper;

    @Autowired
    private IBidListService bidListService;

    @Autowired
    private BidListRepository bidListRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK")
    public void aaa() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "15"))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - letters")
    public void aaaa() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "gg"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Bad quantity - negative")
    public void aaaaa() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/bidList")
                .contentType(APPLICATION_JSON).param("bidAccount", "myAccount")
                .param("bidType", "type").param("bidQuantity", "-50"))
                .andExpect(status().isBadRequest());
    }

}
