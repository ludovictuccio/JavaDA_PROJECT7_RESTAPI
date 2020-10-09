package com.poseidon.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

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

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class RuleNameServiceTest {

    @Autowired
    public IRuleNameService ruleNameService;

    @MockBean
    private RuleNameRepository ruleNameRepository;

    private RuleName ruleName;
    private RuleName ruleNameTwo;
    private RuleName result;

    private List<RuleName> allRuleName;

    @BeforeEach
    public void setUpPerTest() {
        allRuleName = new ArrayList<>();

        ruleName = new RuleName();
        ruleName.setId(1);
        ruleName.setName("name 1");
        ruleName.setDescription("description 1");
        ruleName.setJson("json 1");
        ruleName.setTemplate("template 1");
        ruleName.setSqlStr("sql str 1");
        ruleName.setSqlPart("sql part 1");
        allRuleName.add(ruleName);

        ruleNameTwo = new RuleName();
        ruleNameTwo.setId(2);
        ruleNameTwo.setName("name 2");
        ruleNameTwo.setDescription("description 2");
        ruleNameTwo.setJson("json 2");
        ruleNameTwo.setTemplate("template 2");
        ruleNameTwo.setSqlStr("sql str 2");
        ruleNameTwo.setSqlPart("sql part 2");
        allRuleName.add(ruleNameTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - OK")
    public void givenValidRuleName_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = ruleNameService.saveRuleName(ruleName);

        // THEN
        assertThat(result.getId()).isEqualTo(1);
        assertThat(result.getDescription()).isEqualTo("description 1");
        assertThat(result.getSqlPart()).isEqualTo("sql part 1");
        verify(ruleNameRepository, times(1)).save(ruleName);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save RuleName - ERROR - Attribute size > max allowed")
    public void givenInvalidRuleName_whenSave_thenReturnNull() {
        // GIVEN
        ruleName.setDescription(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456 95");
        // WHEN
        result = ruleNameService.saveRuleName(ruleName);

        // THEN
        assertThat(result).isNull();
        verify(ruleNameRepository, times(0)).save(ruleName);
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - OK - 2 rating")
    public void givenTwoRulename_whenFindAll_thenReturnTwoSizeList() {
        // GIVEN
        when(ruleNameService.findAllRuleNames()).thenReturn(allRuleName);

        // WHEN
        List<RuleName> resultList = ruleNameService.findAllRuleNames();

        // THEN
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(2);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(2);
        assertThat(ruleNameRepository.findAll().get(0).getId()).isNotNull();
        assertThat(ruleNameRepository.findAll().get(0).getDescription())
                .isEqualTo("description 1");
        assertThat(ruleNameRepository.findAll().get(1).getId()).isNotNull();
        assertThat(ruleNameRepository.findAll().get(1).getDescription())
                .isEqualTo("description 2");
    }

    @Test
    @Tag("FIND")
    @DisplayName("Find all - Ok - Empty list / size = 0")
    public void givenZeroRulename_whenFindAll_thenReturnEmptyList() {
        // GIVEN
        allRuleName.clear();
        when(ruleNameService.findAllRuleNames()).thenReturn(allRuleName);

        // WHEN
        List<RuleName> resultList = ruleNameService.findAllRuleNames();

        // THEN
        assertThat(resultList).isNotNull();
        assertThat(resultList.size()).isEqualTo(0);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(0);
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - OK - Existing id")
    public void givenValidId_whenUpdate_thenReturnTrue() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleNameTwo)).thenReturn(ruleNameTwo);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameService.findAllRuleNames()).thenReturn(allRuleName);

        RuleName rulenameForUpdate = new RuleName("name", "description updated",
                "json", "template", "sql str", "sql part");

        // WHEN
        boolean result = ruleNameService.updateRuleName(1, rulenameForUpdate);

        // THEN
        assertThat(result).isTrue();
        verify(ruleNameRepository, times(1)).save(ruleName);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ruleNameRepository.findAll().get(0).getId()).isEqualTo(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription())
                .isEqualTo("description updated");
        assertThat(ruleNameRepository.findAll().get(1).getId()).isEqualTo(2);
        assertThat(ruleNameRepository.findAll().get(1).getDescription())
                .isEqualTo("description 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Bad id")
    public void givenInvalidId_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleNameTwo)).thenReturn(ruleNameTwo);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));
        when(ruleNameService.findAllRuleNames()).thenReturn(allRuleName);

        RuleName rulenameForUpdate = new RuleName("name", "description updated",
                "json", "template", "sql str", "sql part");

        // WHEN
        boolean result = ruleNameService.updateRuleName(99, rulenameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(ruleNameRepository, times(0)).save(ruleName);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ruleNameRepository.findAll().get(0).getId()).isEqualTo(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription())
                .isEqualTo("description 1");
        assertThat(ruleNameRepository.findAll().get(1).getId()).isEqualTo(2);
        assertThat(ruleNameRepository.findAll().get(1).getDescription())
                .isEqualTo("description 2");
    }

    @Test
    @Tag("UPDATE")
    @DisplayName("Update - ERROR - Invalid attributes > max size (125)")
    public void givenInvalidAttributes_whenUpdate_thenReturnFalse() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleNameTwo)).thenReturn(ruleNameTwo);
        when(ruleNameService.findAllRuleNames()).thenReturn(allRuleName);

        RuleName rulenameForUpdate = new RuleName("name",
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456 95gf",
                "json", "template", "sql str", "sql part");

        // WHEN
        boolean result = ruleNameService.updateRuleName(1, rulenameForUpdate);

        // THEN
        assertThat(result).isFalse();
        verify(ruleNameRepository, times(0)).save(ruleName);
        assertThat(ruleNameRepository.findAll().size()).isEqualTo(2); // unchanged
        assertThat(ruleNameRepository.findAll().get(0).getId()).isEqualTo(1);
        assertThat(ruleNameRepository.findAll().get(0).getDescription())
                .isEqualTo("description 1");
        assertThat(ruleNameRepository.findAll().get(1).getId()).isEqualTo(2);
        assertThat(ruleNameRepository.findAll().get(1).getDescription())
                .isEqualTo("description 2");
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - OK - Valid id")
    public void givenRulenameInDb_whenDeleteWithValidId_thenReturnTrue() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleNameTwo)).thenReturn(ruleNameTwo);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        // WHEN
        // 1 = ruleName id
        boolean isDeleted = ruleNameService.deleteRuleName(1);

        // THEN
        assertThat(isDeleted).isTrue();
        verify(ruleNameRepository, times(1)).delete(ruleName);
    }

    @Test
    @Tag("DELETE")
    @DisplayName("Delete - ERROR - Unknow  id")
    public void givenRulenameInDb_whenDeleteWithInvalidId_thenReturnFalse() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.save(ruleNameTwo)).thenReturn(ruleNameTwo);

        // WHEN
        // 1 or 2 are valid
        boolean isDeleted = ruleNameService.deleteRuleName(99);

        // THEN
        assertThat(isDeleted).isFalse();
        verify(ruleNameRepository, times(0)).delete(ruleName);
    }

    @Test
    @Tag("GET_by_ID")
    @DisplayName("Get by id - Ok")
    public void givenOneRulenamewhenGetById_thenReturnOk() {
        // GIVEN
        when(ruleNameRepository.save(ruleName)).thenReturn(ruleName);
        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(ruleName));

        // WHEN
        result = ruleNameService.getRuleNameById(1);

        // THEN
        assertThat(result).isNotNull();
        assertThat(result.getName()).isEqualTo("name 1");
        assertThat(result.getDescription()).isEqualTo("description 1");
        assertThat(result.getJson()).isEqualTo("json 1");
        assertThat(result.getTemplate()).isEqualTo("template 1");
        assertThat(result.getSqlStr()).isEqualTo("sql str 1");
        assertThat(result.getSqlPart()).isEqualTo("sql part 1");
    }
}
