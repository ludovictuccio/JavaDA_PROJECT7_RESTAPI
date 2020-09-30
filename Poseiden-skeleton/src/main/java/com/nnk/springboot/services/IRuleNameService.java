package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.RuleName;

/**
 * IRuleNameService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface IRuleNameService {

    RuleName saveRuleName(RuleName ruleName);

    List<RuleName> findAllRuleNames();

    boolean updateRuleName(Integer ruleNameId, RuleName ruleName);

    boolean deleteRuleName(Integer ruleNameId);

}
