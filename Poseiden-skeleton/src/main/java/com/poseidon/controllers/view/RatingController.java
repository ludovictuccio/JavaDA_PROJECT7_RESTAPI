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

import com.poseidon.domain.Rating;

@Controller
public class RatingController {
    // TODO: Inject Rating service

    private static final Logger LOGGER = LogManager
            .getLogger("RatingController");

    @GetMapping("/rating/list")
    public String home(Model model) {
        // TODO: find all Rating, add to model
        LOGGER.info("GET request SUCCESS for: /rating/list");
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Rating rating) {
        LOGGER.info("GET request SUCCESS for: /rating/add");
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid Rating rating, BindingResult result,
            Model model) {
        // TODO: check data valid and save to db, after saving return Rating
        // list
        LOGGER.info("POST request SUCCESS for: /rating/validate");
        return "rating/add";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get Rating by Id and to model then show to the form
        LOGGER.info("GET request SUCCESS for: /rating/update/{id}");
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id,
            @Valid Rating rating, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Rating
        // and return Rating list
        LOGGER.info("POST request SUCCESS for: /rating/update/{id}");
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Rating by Id and delete the Rating, return to Rating list
        LOGGER.info("GET request SUCCESS for: /rating/delete/{id}");
        return "redirect:/rating/list";
    }
}
