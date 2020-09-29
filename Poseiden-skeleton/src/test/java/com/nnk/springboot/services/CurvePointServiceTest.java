package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class CurvePointServiceTest {

    @Autowired
    public ICurvePointService curvePointService;

    @MockBean
    private CurvePointRepository curvePointRepository;

    private CurvePoint curvepoint;
    private CurvePoint curvepointTwo;
    private CurvePoint result;

    private List<CurvePoint> allCurvepoints;

    private LocalDateTime creationDateCurve1;
    private LocalDateTime creationDateCurve2;

    @BeforeEach
    public void setUpPerTest() {
        creationDateCurve1 = LocalDateTime.of(2000, 12, 31, 23, 59, 00);
        creationDateCurve2 = LocalDateTime.of(2020, 12, 31, 23, 59, 00);

        allCurvepoints = new ArrayList<>();

        curvepoint = new CurvePoint();
        curvepoint.setCreationDate(creationDateCurve1);
        curvepoint.setCurveId(10);
        curvepoint.setTerm(10d);
        curvepoint.setValue(100d);
        allCurvepoints.add(curvepoint);

        curvepointTwo = new CurvePoint();
        curvepointTwo.setCreationDate(creationDateCurve2);
        curvepoint.setCurveId(20);
        curvepointTwo.setTerm(20d);
        curvepointTwo.setValue(200d);
        allCurvepoints.add(curvepointTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - OK - Positive id")
    public void givenOneCurvePoint_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(15, 15d, 150d);

        // THEN
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(15);
        assertThat(result.getTerm()).isEqualTo(15d);
        assertThat(result.getValue()).isEqualTo(150d);
        verify(curvePointRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - OK - Positive id 0")
    public void givenOneCurvePoint_whenSaveWithIdZero_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(0, 15d, 150d);

        // THEN
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(0);
        assertThat(result.getTerm()).isEqualTo(15d);
        assertThat(result.getValue()).isEqualTo(150d);
        verify(curvePointRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - OK - Null term")
    public void givenOneCurvePoint_whenSaveWithNullTerm_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(15, null, 150d);

        // THEN
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(15);
        assertThat(result.getTerm()).isEqualTo(null);
        assertThat(result.getValue()).isEqualTo(150d);
        verify(curvePointRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - OK - null value")
    public void givenOneCurvePoint_whenSaveWithNullValue_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(15, 15d, null);

        // THEN
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(15);
        assertThat(result.getTerm()).isEqualTo(15d);
        assertThat(result.getValue()).isEqualTo(null);
        verify(curvePointRepository, times(1)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - ERROR - Empty Curve id ")
    public void givenOneCurvePoint_whenSaveWithEmptyCurveId_thenReturnNotSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(null, 15d, 150d);

        // THEN
        assertThat(result).isNull();
        verify(curvePointRepository, times(0)).save(result);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - ERROR - Negative id ")
    public void givenOneCurvePoint_whenSaveWithNegativeCurveId_thenReturnNotSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(-15, 15d, 150d);

        // THEN
        assertThat(result).isNull();
        verify(curvePointRepository, times(0)).save(result);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK - 2 curve points")
    public void givenTwoCurvePoints_whenFindAll_thenReturnTwo() {
        // GIVEN
        when(curvePointService.findAllCurvePoints()).thenReturn(allCurvepoints);

        // WHEN
        List<CurvePoint> resultList = curvePointService.findAllCurvePoints();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(2);
//        assertThat(curvePointRepository.findAll().get(0).getCurveId())
//                .isEqualTo(10);
//        assertThat(curvePointRepository.findAll().get(1).getCurveId())
//                .isEqualTo(20);

    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / 0 size")
    public void givenZeroCurvePoints_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allCurvepoints.clear();
        when(curvePointService.findAllCurvePoints()).thenReturn(allCurvepoints);

        // WHEN
        List<CurvePoint> resultList = curvePointService.findAllCurvePoints();

        // THEN
        assertThat(resultList.size()).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(0);
    }

}
