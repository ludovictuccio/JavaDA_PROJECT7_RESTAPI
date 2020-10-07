package com.poseidon.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.domain.RuleName;
import com.poseidon.services.IRuleNameService;

@RestController
@Validated
@RequestMapping("/api/rulename")
public class RuleNameControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("RuleNameControllerApiRest");

    @Autowired
    private IRuleNameService ruleNameService;

    /**
     * Method controller used to add a new ruleName.
     *
     * @param ruleName
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<RuleName> addRuleName(
            @Valid @RequestBody final RuleName ruleName) {

        RuleName result = ruleNameService.saveRuleName(ruleName);

        if (result != null) {
            LOGGER.info("POST request SUCCESS for: /api/rulename/add");
            return new ResponseEntity<RuleName>(HttpStatus.CREATED);
        }
        LOGGER.info("POST request FAILED for: /api/rulename/add");
        return new ResponseEntity<RuleName>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all rulenames.
     *
     * @return all rulename
     */
    @GetMapping("/get")
    public List<RuleName> getAllRuleName() {
        LOGGER.info("GET request SUCCESS for: /api/rulename/get");
        return ruleNameService.findAllRuleNames();
    }

    /**
     * Method controller used to update a rulename.
     *
     * @param id
     * @param ruleName
     * @return ResponseEntity (ok or bad request)
     */
    @PutMapping("/update")
    public ResponseEntity<RuleName> updateRuleName(
            @Valid @RequestParam final Integer id,
            @Valid @RequestBody final RuleName ruleName) {

        boolean result = ruleNameService.updateRuleName(id, ruleName);

        if (result) {
            LOGGER.info("PUT request SUCCESS for: /api/rulename/update");
            return new ResponseEntity<RuleName>(HttpStatus.OK);
        }
        LOGGER.info("PUT request FAILED for: /api/rulename/update");
        return new ResponseEntity<RuleName>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a rulename.
     *
     * @param id the rulename id
     * @return ResponseEntity (ok or bad request)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<RuleName> deleteRuleName(
            @Valid @RequestParam final Integer id) {
        boolean result = ruleNameService.deleteRuleName(id);

        if (result) {
            LOGGER.info("DELETE request SUCCESS for: /api/rulename/delete");
            return new ResponseEntity<RuleName>(HttpStatus.OK);
        }
        LOGGER.info("DELETE request FAILED for: /api/rulename/delete");
        return new ResponseEntity<RuleName>(HttpStatus.BAD_REQUEST);
    }
}
