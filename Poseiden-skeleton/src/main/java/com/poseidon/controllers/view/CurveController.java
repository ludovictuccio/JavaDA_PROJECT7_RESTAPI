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

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.services.CurvePointService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/curvePoint")
public class CurveController {

    private static final Logger LOGGER = LogManager
            .getLogger("CurveController");

    @Autowired
    private CurvePointService curvePointService;

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Get HTML page used to display all curvePoint list.
     *
     * @param model
     * @return /curvePoint/list.html page
     */
    @ApiOperation(value = "CurvePoints LIST (get)", notes = "VIEW - Return all CurvePoints list.")
    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("curvePoint",
                curvePointService.findAllCurvePoints());
        LOGGER.info("GET request SUCCESS for: /curvePoint/list");
        return "curvePoint/list";
    }

    /**
     * Get HTML page used to add a new curvePoint.
     *
     * @param model
     * @return /curvePoint/add.html page
     */
    @ApiOperation(value = "ADD Curve Point (get)", notes = "VIEW - Add new Curve Point")
    @GetMapping("/add")
    public String addBidForm(final Model model) {
        model.addAttribute("curvePoint", new CurvePoint());
        LOGGER.info("GET request SUCCESS for: /curvePoint/add");
        return "curvePoint/add";
    }

    /**
     * Post HTML page used to validate a new curvePoint.
     *
     * @param curvePoint
     * @param result
     * @param model
     * @return /curvePoint/add.html page if bad request or else /curvePoint/list
     */
    @ApiOperation(value = "VALIDATE Curve Point (post)", notes = "VIEW - Validate / save the new Curve Point")
    @PostMapping("/validate")
    public String validate(@Valid final CurvePoint curvePoint,
            final BindingResult result, final Model model) {

        if (!result.hasErrors()) {
            CurvePoint curve = curvePointService.saveCurvePoint(
                    curvePoint.getCurveId(), curvePoint.getTerm(),
                    curvePoint.getValue());
            if (curve != null) {
                model.addAttribute("curvePoint", curve);
                LOGGER.info("POST request SUCCESS for: /curvePoint/validate");
                return "redirect:/curvePoint/list";
            }
        }
        LOGGER.info("POST request FAILED for: /curvePoint/validate");
        return "curvePoint/add";
    }

    /**
     * Get HTML page used to update a curve point.
     *
     * @param id
     * @param model
     * @return /curvePoint/update.html page
     */
    @ApiOperation(value = "UPDATE Curve Point (get)", notes = "VIEW - Get a Curve Point by id and retrieve to update it.")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Integer id,
            final Model model) {
        CurvePoint curvePoint = curvePointService.getCurvePointById(id);
        model.addAttribute("curvePoint", curvePoint);
        LOGGER.info("GET request SUCCESS for: /curvePoint/update/{id}");
        return "curvePoint/update";
    }

    /**
     * Post HTML page used to update a curvePoint.
     *
     * @param id
     * @param curvePoint
     * @param result
     * @param model
     * @return /curvePoint/update.html page if bad request or else
     *         /curvePoint/list
     */
    @ApiOperation(value = "UPDATE Curve Point (post)", notes = "VIEW - Update the Curve Point.")
    @PostMapping("/update/{id}")
    public String updateBid(@PathVariable("id") final Integer id,
            @Valid final CurvePoint curvePoint, final BindingResult result,
            final Model model) {

        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /curvePoint/update/{id}");
            return "curvePoint/update/" + id;
        }
        curvePoint.setId(id);
        curvePointRepository.save(curvePoint);
        model.addAttribute("curvePoint",
                curvePointService.findAllCurvePoints());
        LOGGER.info("POST request SUCCESS for: /curvePoint/update/{id}");
        return "redirect:/curvePoint/list";
    }

    /**
     * Get HTML page used to delete a curvePoint.
     *
     * @param id
     * @param model
     * @return /curvePoint/list.html page
     */
    @ApiOperation(value = "DELETE Curve Point (get)", notes = "VIEW - Get Curve Point with his id, and delete it.")
    @GetMapping("/delete/{id}")
    public String deleteBid(@PathVariable("id") final Integer id,
            final Model model) {
        boolean isDeleted = curvePointService.deleteCurvePoint(id);
        if (isDeleted) {
            model.addAttribute("curvePoint",
                    curvePointService.findAllCurvePoints());
            LOGGER.info("GET request SUCCESS for: /curvePoint/delete/{id}");
        } else {
            LOGGER.info("GET request FAILED for: /curvePoint/delete/{id}");
        }
        return "redirect:/curvePoint/list";
    }
}
