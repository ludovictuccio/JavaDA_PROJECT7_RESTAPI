package com.nnk.springboot.controllers.api;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.services.ICurvePointService;

@RestController
@Validated
@RequestMapping("/api/curvePoint")
public class CurvePointControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("CurvePointControllerApiRest");

    @Autowired
    private ICurvePointService curvePointService;

    /**
     * Method controller used to add a new curve point.
     *
     * @param id
     * @param term
     * @param value
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<CurvePoint> addBidList(
            @Valid @RequestParam final Integer id,
            @Valid @RequestParam final Double term,
            @Valid @RequestParam final Double value) {

        CurvePoint result = curvePointService.saveCurvePoint(id, term, value);

        if (result != null) {
            return new ResponseEntity<CurvePoint>(HttpStatus.CREATED);
        }
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

}
