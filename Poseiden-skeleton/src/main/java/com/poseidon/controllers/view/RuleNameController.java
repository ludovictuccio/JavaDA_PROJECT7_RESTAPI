package com.poseidon.controllers.view;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidon.domain.RuleName;
import com.poseidon.services.RuleNameService;

@Controller
@RequestMapping("/ruleName")
public class RuleNameController {

    private static final Logger LOGGER = LogManager
            .getLogger("RuleNameController");

    @Autowired
    private RuleNameService ruleNameService;

    /**
     * Get HTML page used to display all ruleNames list.
     *
     * @param model
     * @return /ruleName/list.html page
     */
    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("ruleName", ruleNameService.findAllRuleNames());
        LOGGER.info("GET request SUCCESS for: /ruleName/list");
        return "ruleName/list";
    }

    /**
     * Get HTML page used to add a new ruleName.
     *
     * @param model
     * @return /ruleName/add.html page
     */
    @GetMapping("/add")
    public String addRuleForm(final Model model) {
        model.addAttribute("ruleName", new RuleName());
        LOGGER.info("GET request SUCCESS for: /ruleName/add");
        return "ruleName/add";
    }

    /**
     * Post HTML page used to validate a new ruleName.
     *
     * @param ruleName
     * @param result
     * @param model
     * @return /ruleName/add.html page if bad request or else /ruleName/list
     */
    @PostMapping("/validate")
    public String validate(@Valid final RuleName ruleName,
            final BindingResult result, final Model model) {

        if (!result.hasErrors()) {
            RuleName rule = ruleNameService.saveRuleName(ruleName);
            if (rule != null) {
                model.addAttribute("ruleName", rule);
                LOGGER.info("POST request SUCCESS for: /ruleName/validate");
                return "redirect:/ruleName/list";
            }
        }
        LOGGER.info("POST request FAILED for: /ruleName/validate");
        return "ruleName/add";
    }

    /**
     * Get HTML page used to update a ruleName.
     *
     * @param id
     * @param model
     * @return /ruleName/update.html page
     */
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {
        RuleName rulename = ruleNameService.getRuleNameById(id);
        model.addAttribute("ruleName", rulename);
        LOGGER.info("GET request SUCCESS for: /ruleName/update/{id}");
        return "ruleName/update";
    }

    /**
     * Post HTML page used to update a ruleName.
     *
     * @param id
     * @param ruleName
     * @param result
     * @param model
     * @return /ruleName/update.html page if bad request or else /ruleName/list
     */
    @PostMapping("/update/{id}")
    public String updateRuleName(@PathVariable("id") final Integer id,
            @Valid final RuleName ruleName, final BindingResult result,
            final Model model) {

        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /ruleName/update/{id}");
            return "ruleName/update/" + id;
        }
        ruleName.setId(id);
        ruleNameService.saveRuleName(ruleName);
        model.addAttribute("ruleName", ruleNameService.findAllRuleNames());
        LOGGER.info("POST request SUCCESS for: /ruleName/update/{id}");
        return "redirect:/ruleName/list";
    }

    /**
     * Get HTML page used to delete a ruleName.
     *
     * @param id
     * @param model
     * @return /ruleName/list.html page
     */
    @GetMapping("/delete/{id}")
    public String deleteRuleName(@PathVariable("id") final Integer id,
            final Model model) {
        boolean isDeleted = ruleNameService.deleteRuleName(id);
        if (isDeleted) {
            model.addAttribute("ruleName", ruleNameService.findAllRuleNames());
            LOGGER.info("GET request SUCCESS for: /ruleName/delete/{id}");
        } else {
            LOGGER.info("GET request FAILED for: /ruleName/delete/{id}");
        }
        return "redirect:/ruleName/list";
    }
}
