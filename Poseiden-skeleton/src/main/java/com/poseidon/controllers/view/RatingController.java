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

import com.poseidon.domain.Rating;
import com.poseidon.services.RatingService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/rating")
public class RatingController {

    private static final Logger LOGGER = LogManager
            .getLogger("RatingController");

    @Autowired
    private RatingService ratingService;

    /**
     * Get HTML page used to display all ratings list.
     *
     * @param model
     * @return /rating/list.html page
     */
    @ApiOperation(value = "Ratings LIST (get)", notes = "VIEW - Return all Ratings list.")
    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("rating", ratingService.findAllRating());
        LOGGER.info("GET request SUCCESS for: /rating/list");
        return "rating/list";
    }

    /**
     * Get HTML page used to add a new rating.
     *
     * @param model
     * @return /rating/add.html page
     */
    @ApiOperation(value = "ADD Rating (get)", notes = "VIEW - Add new Rating")
    @GetMapping("/add")
    public String addRatingForm(final Model model) {
        model.addAttribute("rating", new Rating());
        LOGGER.info("GET request SUCCESS for: /rating/add");
        return "rating/add";
    }

    /**
     * Post HTML page used to validate a new rating.
     *
     * @param rating
     * @param result
     * @param model
     * @return /rating/add.html page if bad request or else /rating/list
     */
    @ApiOperation(value = "VALIDATE Rating (post)", notes = "VIEW - Validate / save the new Rating")
    @PostMapping("/validate")
    public String validate(@Valid final Rating rating,
            final BindingResult result, final Model model) {

        if (!result.hasErrors()) {
            Rating ratingResult = ratingService.saveRating(rating);
            if (ratingResult != null) {
                model.addAttribute("rating", ratingResult);
                LOGGER.info("POST request SUCCESS for: /rating/validate");
                return "redirect:/rating/list";
            }
        }
        LOGGER.info("POST request FAILED for: /rating/validate");
        return "rating/add";
    }

    /**
     * Get HTML page used to update a rating.
     *
     * @param id
     * @param model
     * @return /rating/update.html page
     */
    @ApiOperation(value = "UPDATE Rating (get)", notes = "VIEW - Get a Rating by id and retrieve to update it.")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {
        Rating rating = ratingService.getRatingById(id);
        model.addAttribute("rating", rating);
        LOGGER.info("GET request SUCCESS for: /rating/update/{id}");
        return "rating/update";
    }

    /**
     * Post HTML page used to update a rating.
     *
     * @param id
     * @param rating
     * @param result
     * @param model
     * @return /rating/update.html page if bad request or else /rating/list
     */
    @ApiOperation(value = "UPDATE Rating (post)", notes = "VIEW - Update the Rating.")
    @PostMapping("/update/{id}")
    public String updateRating(@PathVariable("id") final Integer id,
            @Valid final Rating rating, final BindingResult result,
            final Model model) {

        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /rating/update/{id}");
            return "rating/update/" + id;
        }
        rating.setId(id);
        ratingService.saveRating(rating);
        model.addAttribute("rating", ratingService.findAllRating());
        LOGGER.info("POST request SUCCESS for: /rating/update/{id}");
        return "redirect:/rating/list";
    }

    /**
     * Get HTML page used to delete a rating.
     *
     * @param id
     * @param model
     * @return /rating/list.html page
     */
    @ApiOperation(value = "DELETE Rating (get)", notes = "VIEW - Get Rating with his id, and delete it.")
    @GetMapping("/delete/{id}")
    public String deleteRating(@PathVariable("id") final Integer id,
            final Model model) {
        boolean isDeleted = ratingService.deleteRating(id);
        if (isDeleted) {
            model.addAttribute("rating", ratingService.findAllRating());
            LOGGER.info("GET request SUCCESS for: /rating/delete/{id}");
        } else {
            LOGGER.info("GET request FAILED for: /rating/delete/{id}");
        }
        return "redirect:/rating/list";
    }
}
