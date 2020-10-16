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

import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.ICurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurvePointControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ICurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    private static String uri = "/v1/curvePoint";

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        curvePointRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - Max value")
    public void givenMaxValue_whenCreate_thenReturnCreated() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "127")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "0")
                        .param("term", "150").param("value", "1500"))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - negative Curve Id ")
    public void givenNegativeCurveId_whenCreate_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "-1")
                        .param("term", "150").param("value", "1500"))
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "a")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "/")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "15")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "15")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "15")
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
                .perform(MockMvcRequestBuilders.post(uri)
                        .contentType(APPLICATION_JSON).param("curveId", "15")
                        .param("term", "150").param("value", "-"))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 curvepoints in db")
    public void givenTwoCurvePointsInDb_whenGet_thenReturnListWithTwoBid()
            throws Exception {
        curvePointService.saveCurvePoint(125, 150d, 160d);
        curvePointService.saveCurvePoint(15, 10d, 10d);
        this.mockMvc
                .perform(MockMvcRequestBuilders.get(uri)
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
                .perform(MockMvcRequestBuilders.get(uri)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow  id in DB")
    public void givenCurveToDelete_whenUnknowId_thenReturnBadRequest()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(APPLICATION_JSON).param("id", "9999"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Invalid id (letter)")
    public void givenCurveToDelete_whenLetterForCurveId_thenReturnBadRequest()
            throws Exception {

        curvePointService.saveCurvePoint(95, 150d, 160d);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(APPLICATION_JSON).param("id", "letter"))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Invalid id (character)")
    public void givenCurveToDelete_whenCharacterEntry_thenReturnBadRequest()
            throws Exception {

        curvePointService.saveCurvePoint(95, 150d, 160d);
        this.mockMvc
                .perform(MockMvcRequestBuilders.delete(uri)
                        .contentType(APPLICATION_JSON).param("id", "*"))
                .andExpect(status().isBadRequest());
    }

}
