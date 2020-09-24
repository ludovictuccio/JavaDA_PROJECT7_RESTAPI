package com.nnk.springboot.controllers.view;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class BidListControllerIT {

//    @LocalServerPort // for independents test
//    private int port;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Autowired
//    private WebApplicationContext wac;
//
//    @Mock
//    private View view;
//
//    // @Autowired
//    // private ObjectMapper objectMapper;
//    @Autowired
//    private IBidListService bidListService;
//
//    @Autowired
//    private BidListRepository bidListRepository;
//
//    private List<BidList> allBidList = new ArrayList<>();
//
//    @Before
//    public void setupMockMvc() {
//        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
//        // objectMapper = new ObjectMapper();
//        // String jsonContentValid = objectMapper.writeValueAsString(validUser);
//        // List<BidList> allBidList = new ArrayList<>();
//        allBidList.add(new BidList());
//        allBidList.get(0).setBidListId(10);
//        bid.setAccount("Account");
//        bid.setType("Type");
//        bid.setBidQuantity(10d);
//        // allBidList.add(bid);
//        bidListService.saveBidList(bid);
//    }
//
//    @Test
//    @Tag("/bidList/list")
//    @DisplayName("Get Home - OK")
//    public void aaa() throws Exception {
//
//        List<BidList> result = bidListService.findAllBids();
//
//        this.mockMvc.perform(MockMvcRequestBuilders.get("/bidList/list"))
//                .andDo(print())
//                .andExpect(MockMvcResultMatchers.model().attribute("bidList",
//                        result))
//                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
//                .andExpect(MockMvcResultMatchers.view().name("bidList/list"))
//                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
//    }
}
