package com.poseidon.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatNullPointerException;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;

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
        curvepoint.setId(1);
        curvepoint.setCreationDate(creationDateCurve1);
        curvepoint.setCurveId(10);
        curvepoint.setTerm(10d);
        curvepoint.setValue(100d);
        allCurvepoints.add(curvepoint);

        curvepointTwo = new CurvePoint();
        curvepointTwo.setId(2);
        curvepointTwo.setCreationDate(creationDateCurve2);
        curvepointTwo.setCurveId(20);
        curvepointTwo.setTerm(20d);
        curvepointTwo.setValue(200d);
        allCurvepoints.add(curvepointTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save Curvepoint - OK - Positive curve id")
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
    @DisplayName("Save Curvepoint - OK - curve id 0")
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
    @DisplayName("Save Curvepoint - Ok - Empty Curve id ")
    public void givenOneCurvePoint_whenSaveWithEmptyCurveId_thenReturnNotSaved() {
        // GIVEN

        // WHEN
        result = curvePointService.saveCurvePoint(null, 15d, 150d);

        // THEN
        assertThat(result.getCreationDate()).isNotNull();
        assertThat(result.getCurveId()).isEqualTo(null);
        assertThat(result.getTerm()).isEqualTo(15d);
        assertThat(result.getValue()).isEqualTo(150d);
        verify(curvePointRepository, times(1)).save(result);
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
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(2);

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
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(curvePointRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing id")
    public void givenOneCurvePoints_whenUpdate_thenReturnUpdated() {
        // GIVEN
        when(curvePointRepository.save(curvepoint)).thenReturn(curvepoint);

        // 1= curvepoint id
        CurvePoint curvePoint = new CurvePoint(1, 99, 99d, 999d);

        when(curvePointRepository.findById(1))
                .thenReturn(Optional.of(curvepoint));

        // WHEN
        result = curvePointService.updateCurvePoint(curvePoint);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getAsOfDate()).isNotNull();// date for curve update
        assertThat(result.getCreationDate()).isEqualTo(creationDateCurve1);
        assertThat(result.getCurveId()).isEqualTo(99);
        assertThat(result.getTerm()).isEqualTo(99d);
        assertThat(result.getValue()).isEqualTo(999d);
        verify(curvePointRepository, times(1)).save(result);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Unknow id")
    public void givenOneCurvePoints_whenUpdateWithUnknowId_thenReturnNull() {
        // GIVEN
        when(curvePointRepository.save(curvepoint)).thenReturn(curvepoint);

        // 1 or 2 are valid
        CurvePoint curvePoint = new CurvePoint(999, 99, 99d, 999d);

        // WHEN
        result = curvePointService.updateCurvePoint(curvePoint);

        // THEN
        assertThat(result).isNull();
        verify(curvePointRepository, times(0)).save(result);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - OK")
    public void givenCurveInDb_whenDeleteWithCorrectCurveId_thenReturnTrue() {
        // GIVEN
        when(curvePointRepository.save(curvepoint)).thenReturn(curvepoint);
        when(curvePointRepository.findById(1))
                .thenReturn(Optional.of(curvepoint));

        // WHEN
        // 1 = valid
        boolean isDeleted = curvePointService.deleteCurvePoint(1);

        // THEN
        assertThat(isDeleted).isTrue();
        verify(curvePointRepository, times(1)).delete(curvepoint);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - Error - Unknow id")
    public void givenCurveInDb_whenDeleteWithIncorrectCurveId_thenReturnFalse() {
        // GIVEN
        when(curvePointRepository.save(curvepoint)).thenReturn(curvepoint);

        // WHEN
        // 1 = valid
        boolean isDeleted = curvePointService.deleteCurvePoint(99);

        // THEN
        assertThat(isDeleted).isFalse();
        verify(curvePointRepository, times(0)).delete(curvepoint);
    }

    @Test
    @Tag("GET_by_ID")
    @DisplayName("Get by id - Ok")
    public void givenOneCurvePoints_whenGetById_thenReturnOk() {
        // GIVEN
        CurvePoint curve = new CurvePoint(1, 10, 10d, 100d);
        when(curvePointRepository.findById(1)).thenReturn(Optional.of(curve));

        // WHEN
        result = curvePointService.getCurvePointById(1);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getCurveId()).isEqualTo(10);
        assertThat(result.getTerm()).isEqualTo(10d);
        assertThat(result.getValue()).isEqualTo(100d);
    }

    @Test
    @Tag("GET_by_ID")
    @DisplayName("Get by id - Error - Bad id")
    public void givenBadCurvePoint_whenGetById_thenReturnNull() {

        when(curvePointRepository.save(curvepoint)).thenReturn(curvepoint);

        assertThatNullPointerException().isThrownBy(() -> {
            curvePointService.getCurvePointById(99);
        });
    }

}
