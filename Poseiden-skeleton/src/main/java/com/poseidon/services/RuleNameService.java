package com.poseidon.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.domain.RuleName;
import com.poseidon.repositories.RuleNameRepository;
import com.poseidon.util.ConstraintsValidation;

/**
 * RuleName Service class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class RuleNameService implements IRuleNameService {

    private static final Logger LOGGER = LogManager
            .getLogger("RuleNameService");

    @Autowired
    private RuleNameRepository ruleNameRepository;

    /**
     * Method service used to save a new ruleName.
     *
     * @param ruleName
     * @return ruleName
     */
    public RuleName saveRuleName(final RuleName ruleName) {

        if (ConstraintsValidation.checkValidRuleName(ruleName) == null) {
            return null;
        }
        ruleNameRepository.save(ruleName);
        return ruleName;
    }

    /**
     * Method service used to find all rulenames.
     *
     * @return all rulenames
     */
    public List<RuleName> findAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Method service used to update a ruleName.
     *
     * @param ruleName
     * @param ruleNameId the ruleName id
     * @return isUpdated boolean
     */
    public boolean updateRuleName(final Integer ruleNameId,
            final RuleName ruleName) {
        boolean isUpdated = false;

        if (ConstraintsValidation.checkValidRuleName(ruleName) == null) {
            return isUpdated;
        }
        RuleName existingRuleName = ruleNameRepository.findById(ruleNameId)
                .orElse(null);

        if (existingRuleName == null) {
            LOGGER.error("Unknow rule name id for number: {}", ruleNameId);
            return isUpdated;
        }
        existingRuleName.setName(ruleName.getName());
        existingRuleName.setDescription(ruleName.getDescription());
        existingRuleName.setJson(ruleName.getJson());
        existingRuleName.setTemplate(ruleName.getTemplate());
        existingRuleName.setSqlStr(ruleName.getSqlStr());
        existingRuleName.setSqlPart(ruleName.getSqlPart());
        ruleNameRepository.save(existingRuleName);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * Method service used to delete a rulename with his id.
     *
     * @param ruleNameId the ruleName id
     * @return isDeleted boolean
     */
    public boolean deleteRuleName(final Integer ruleNameId) {
        boolean isDeleted = false;

        RuleName existingRuleName = ruleNameRepository.findById(ruleNameId)
                .orElse(null);

        if (existingRuleName == null) {
            LOGGER.error("Unknow rule name id for number: {}", ruleNameId);
            return isDeleted;
        }
        ruleNameRepository.delete(existingRuleName);
        isDeleted = true;
        return isDeleted;
    }

    /**
     * Method service used to find a RuleName by his id.
     *
     * @param id
     * @return existingRuleName a RuleName entity
     */
    public RuleName getRuleNameById(final Integer id) {

        RuleName existingRuleName = ruleNameRepository.findById(id)
                .orElse(null);

        if (existingRuleName == null) {
            LOGGER.error("RuleName not found for id: {}", id);
            return null;
        }
        return existingRuleName;
    }

}
