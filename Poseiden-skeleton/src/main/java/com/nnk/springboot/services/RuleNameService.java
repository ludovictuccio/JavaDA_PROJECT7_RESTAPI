package com.nnk.springboot.services;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;

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
     * Method used to check if the RuleName entity entered is valid (to validate
     * javax constraints in model class).
     *
     * @param ruleName
     * @return ruleName or null
     */
    public RuleName checkValidRuleName(final RuleName ruleName) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RuleName>> constraintViolations = validator
                .validate(ruleName);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<RuleName> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return ruleName;
    }

    /**
     * Method service used to save a new ruleName.
     *
     * @param ruleName
     * @return ruleName
     */
    public RuleName saveRuleName(final RuleName ruleName) {

        if (checkValidRuleName(ruleName) == null) {
            return null;
        }
        ruleNameRepository.save(ruleName);
        return ruleName;
    }

    /**
     * Method service used to find all rulenames.
     */
    public List<RuleName> findAllRuleNames() {
        return ruleNameRepository.findAll();
    }

    /**
     * Method service used to update a ruleName.
     *
     * @param ruleName
     * @param ruleNameId the ruleName id
     * @param isUpdated  boolean
     */
    public boolean updateRuleName(final Integer ruleNameId,
            final RuleName ruleName) {
        boolean isUpdated = false;

        if (checkValidRuleName(ruleName) == null) {
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

}
