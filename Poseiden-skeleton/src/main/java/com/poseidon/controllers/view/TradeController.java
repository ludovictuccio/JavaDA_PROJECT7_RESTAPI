package com.poseidon.controllers.view;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidon.domain.Trade;

@Controller
public class TradeController {
    // TODO: Inject Trade service

    private static final Logger LOGGER = LogManager
            .getLogger("TradeController");

    @GetMapping("/trade/list")
    public String home(Model model) {
        // TODO: find all Trade, add to model
        LOGGER.info("GET request SUCCESS for: /trade/list");
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        LOGGER.info("GET request SUCCESS for: /trade/add");
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result,
            Model model) {
        // TODO: check data valid and save to db, after saving return Trade list
        LOGGER.info("POST request SUCCESS for: /trade/validate");
        return "trade/add";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Trade by Id and to model then show to the form
        LOGGER.info("GET request SUCCESS for: /trade/update/{id}");
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id,
            @Valid Trade trade, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Trade
        // and return Trade list
        LOGGER.info("POST request SUCCESS for: /trade/update/{id}");
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Trade by Id and delete the Trade, return to Trade list
        LOGGER.info("GET request SUCCESS for: /trade/delete/{id}");
        return "redirect:/trade/list";
    }
}
