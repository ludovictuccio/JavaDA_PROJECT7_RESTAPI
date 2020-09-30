package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

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

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

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
        ruleName.setDescription("description 1");
        ruleName.setJson("json 1");
        ruleName.setTemplate("template 1");
        ruleName.setSqlStr("sql str 1");
        ruleName.setSqlPart("sql part 1");
        allRuleName.add(ruleName);

        ruleNameTwo = new RuleName();
        ruleNameTwo.setId(2);
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

}
