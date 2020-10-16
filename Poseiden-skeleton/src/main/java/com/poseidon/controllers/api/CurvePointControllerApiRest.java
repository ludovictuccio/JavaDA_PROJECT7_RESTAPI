package com.poseidon.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.domain.CurvePoint;
import com.poseidon.services.ICurvePointService;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("/v1/curvePoint")
public class CurvePointControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("CurvePointControllerApiRest");

    @Autowired
    private ICurvePointService curvePointService;

    /**
     * Method controller used to add a new curve point.
     *
     * @param curveId
     * @param term
     * @param value
     * @return ResponseEntity (created or bad request)
     */
    @ApiOperation(value = "ADD Curve Point", notes = "API REST - Need Curve Point entity - Return ResponseEntity 201 created or 400 bad request.")
    @PostMapping
    public ResponseEntity<CurvePoint> addCurvePoint(
            @Valid @RequestParam final Integer curveId,
            @Valid @RequestParam final Double term,
            @Valid @RequestParam final Double value) {

        CurvePoint result = curvePointService.saveCurvePoint(curveId, term,
                value);

        if (result != null) {
            LOGGER.info("POST request SUCCESS for: /api/curvePoint/add");
            return new ResponseEntity<CurvePoint>(HttpStatus.CREATED);
        }
        LOGGER.info("POST request FAILED for: /api/curvePoint/add");
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all curve points.
     *
     * @return all curve points
     */
    @ApiOperation(value = "GET Curve Point", notes = "API REST - Returnall curve points list")
    @GetMapping
    public List<CurvePoint> getAllCurvePoints() {
        LOGGER.info("GET request SUCCESS for: /api/curvePoint/get");
        return curvePointService.findAllCurvePoints();
    }

    /**
     * Method controller used to update a curve point.
     *
     * @param curvePoint
     * @return ResponseEntity (ok or bad request)
     */
    @ApiOperation(value = "UPDATE Curve Point", notes = "API REST - Need Curve Point entity - Return ResponseEntity 200 OK or 400 bad request.")
    @PutMapping
    public ResponseEntity<CurvePoint> updateCurvePoint(
            @Valid @RequestBody final CurvePoint curvePoint) {

        CurvePoint existingCurvePoint = curvePointService
                .updateCurvePoint(curvePoint);

        if (existingCurvePoint != null) {
            LOGGER.info("PUT request SUCCESS for: /api/curvePoint/update");
            return new ResponseEntity<CurvePoint>(HttpStatus.OK);
        }
        LOGGER.info("PUT request FAILED for: /api/curvePoint/update");
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a curve point.
     *
     * @param id
     * @return ResponseEntity (ok or bad request)
     */
    @ApiOperation(value = "DELETE Curve Point", notes = "API REST - Need Curve Point id - Return ResponseEntity 200 OK or 400 bad request.")
    @DeleteMapping
    public ResponseEntity<CurvePoint> deleteBidList(
            @Valid @RequestParam final Integer id) {

        boolean result = curvePointService.deleteCurvePoint(id);

        if (result) {
            LOGGER.info("DELETE request SUCCESS for: /api/curvePoint/delete");
            return new ResponseEntity<CurvePoint>(HttpStatus.OK);
        }
        LOGGER.info("DELETE request FAILED for: /api/curvePoint/delete");
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

}
