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
import com.nnk.springboot.repositories.CurvePointRepository;
import com.nnk.springboot.services.ICurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurvePointControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private ICurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void setUpPerTest() {
        curvePointRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - Max value")
    public void givenMaxValue_whenCreate_thenReturnCreated() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "127")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - Min value")
    public void givenMinValue_whenCreate_thenReturnCreated() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "-128")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Already existing ID in DB")
    public void givenCurveIdAlreadyExisting_whenCreate_thenReturnBadRequest()
            throws Exception {

        curvePointService.saveCurvePoint(125, 150d, 160d);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "125")
                        .param("term", "1520").param("value", "12"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for id")
    public void givenLetterForId_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "a")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for id")
    public void givenCharacterForId_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "/")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for term")
    public void givenCharacterForTerm_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "*").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for term")
    public void givenLetterForTerm_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "p").param("value", "1500"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Letter for value")
    public void givenLetterForValue_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "150").param("value", "l"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Character for value")
    public void givenCharacterForValue_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/curvePoint/add")
                        .contentType(APPLICATION_JSON).param("id", "15")
                        .param("term", "150").param("value", "-"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 curvepoints in db")
    public void givenTwoCurvePointsInDb_whenGet_thenReturnListWithOneBid()
            throws Exception {
        curvePointService.saveCurvePoint(125, 150d, 160d);
        curvePointService.saveCurvePoint(15, 10d, 10d);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/curvePoint/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 curvepoint in db")
    public void givenZeroCurvePointInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/curvePoint/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }
}
