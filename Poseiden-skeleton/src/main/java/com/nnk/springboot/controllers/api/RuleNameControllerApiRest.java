package com.nnk.springboot.controllers.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.services.IRuleNameService;

@RestController
@Validated
@RequestMapping("/api/rulename")
public class RuleNameControllerApiRest {

    @Autowired
    private IRuleNameService ruleNameService;

    /**
     * Method controller used to add a new ruleName.
     *
     * @param ruleName
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<RuleName> addRating(
            @Valid @RequestBody final RuleName ruleName) {

        RuleName result = ruleNameService.saveRuleName(ruleName);

        if (result != null) {
            return new ResponseEntity<RuleName>(HttpStatus.CREATED);
        }
        return new ResponseEntity<RuleName>(HttpStatus.BAD_REQUEST);
    }
}
