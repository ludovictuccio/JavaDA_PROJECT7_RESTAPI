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

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.CurvePointService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CurvePointControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        curvePointRepository.deleteAll();
    }

    @Test
    @Tag("/curvePoint/list")
    @DisplayName("Get - list")
    public void givenZeroCurvePoints_whenGetList_thenreturnOk()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/curvePoint/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/add")
    @DisplayName("Get - add")
    public void givenCurvePoint_whenGetAdd_thenReturnOk() throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/curvePoint/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.model()
                        .attributeExists("curvePoint"))
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/validate")
    @DisplayName("Post - validate - OK")
    public void givenValidCurvePoint_whenValidate_thenReturnSaved()
            throws Exception {

        CurvePoint curvePoint = new CurvePoint(1, 10, null, 90d, 50d, null);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .contentType(APPLICATION_JSON)
                        .param("curveId", curvePoint.getCurveId().toString())
                        .param("term", curvePoint.getTerm().toString())
                        .param("value", curvePoint.getValue().toString()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/curvePoint/list"))
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/validate")
    @DisplayName("Post - validate - Error - Letters for double value")
    public void givenLettersForDouble_whenValidate_thenReturnErrors()
            throws Exception {

        CurvePoint curvePoint = new CurvePoint(1, 10, null, 90d, 50d, null);
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/curvePoint/validate")
                        .contentType(APPLICATION_JSON)
                        .param("curveId", curvePoint.getCurveId().toString())
                        .param("term", curvePoint.getTerm().toString())
                        .param("value", "f"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andExpect(MockMvcResultMatchers.view().name("curvePoint/add"))
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/update/{id}")
    @DisplayName("Post - update")
    public void givenOnecurvepoint_whenUpdate_thenReturnUpdated()
            throws Exception {

        CurvePoint curvePoint = new CurvePoint(1, 10, null, 90d, 50d, null);
        curvePointService.saveCurvePoint(curvePoint.getCurveId(),
                curvePoint.getTerm(), curvePoint.getValue());
        curvePointRepository.save(curvePoint);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/curvePoint/update/1")
                        .contentType(MediaType.ALL).param("curveId", "10")
                        .param("term", "20").param("value", "30"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/curvePoint/list"))
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/delete")
    @DisplayName("Delete - OK")
    public void givenCurvePoint_whenDelete_thenReturnDeleted()
            throws Exception {

        CurvePoint curvePoint = new CurvePoint(1, 10, null, 90d, 50d, null);
        curvePointService.saveCurvePoint(curvePoint.getCurveId(),
                curvePoint.getTerm(), curvePoint.getValue());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/curvePoint/delete/1")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/curvePoint/list"))
                .andReturn();
    }

    @Test
    @Tag("/curvePoint/delete")
    @DisplayName("Delete - ERROR - Bad id")
    public void givenCurvePoint_whenDeleteBadId_thenReturnError()
            throws Exception {

        CurvePoint curvePoint = new CurvePoint(1, 10, null, 90d, 50d, null);
        curvePointService.saveCurvePoint(curvePoint.getCurveId(),
                curvePoint.getTerm(), curvePoint.getValue());

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/curvePoint/delete/99")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.redirectedUrl("/curvePoint/list"))
                .andReturn();
    }
}
