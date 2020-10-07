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

import com.poseidon.domain.CurvePoint;

@Controller
public class CurveController {
    // TODO: Inject Curve Point service

    private static final Logger LOGGER = LogManager
            .getLogger("CurveController");

    @GetMapping("/curvePoint/list")
    public String home(Model model) {
        // TODO: find all Curve Point, add to model
        LOGGER.info("GET request SUCCESS for: /curvePoint/list");
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addBidForm(CurvePoint bid) {
        LOGGER.info("GET request SUCCESS for: /curvePoint/add");
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result,
            Model model) {
        // TODO: check data valid and save to db, after saving return Curve list
        LOGGER.info("POST request SUCCESS for: /curvePoint/validate");
        return "curvePoint/add";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        // TODO: get CurvePoint by Id and to model then show to the form
        LOGGER.info("GET request SUCCESS for: /curvePoint/update/{id}");
        return "curvePoint/update";
    }

    @PostMapping("/curvePoint/update/{id}")
    public String updateBid(@PathVariable("id") Integer id,
            @Valid CurvePoint curvePoint, BindingResult result, Model model) {
        // TODO: check required fields, if valid call service to update Curve
        // and return Curve list
        LOGGER.info("POST request SUCCESS for: /curvePoint/update/{id}");
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteBid(@PathVariable("id") Integer id, Model model) {
        // TODO: Find Curve by Id and delete the Curve, return to Curve list
        LOGGER.info("GET request SUCCESS for: /curvePoint/delete/{id}");
        return "redirect:/curvePoint/list";
    }
}
