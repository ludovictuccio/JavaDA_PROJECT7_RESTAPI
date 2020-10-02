package com.poseidon.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.domain.CurvePoint;
import com.poseidon.services.ICurvePointService;

@RestController
@Validated
@RequestMapping("/api/curvePoint")
public class CurvePointControllerApiRest {

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

    /**
     * Method controller used to find all curve points.
     *
     * @return all curve points
     */
    @GetMapping("/get")
    public List<CurvePoint> getAllCurvePoints() {
        return curvePointService.findAllCurvePoints();
    }

    /**
     * Method controller used to update a curve point.
     *
     * @param curveId
     * @param term
     * @param value
     * @return ResponseEntity (ok or bad request)
     */
    @PutMapping("/update")
    public ResponseEntity<CurvePoint> updateCurvePoint(
            @Valid @RequestParam final Integer curveId,
            @Valid @RequestParam final Double term,
            @Valid @RequestParam final Double value) {

        CurvePoint result = curvePointService.updateCurvePoint(curveId, term,
                value);

        if (result != null) {
            return new ResponseEntity<CurvePoint>(HttpStatus.OK);
        }
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a curve point.
     *
     * @param curveId
     * @return ResponseEntity (ok or bad request)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<CurvePoint> deleteBidList(
            @Valid @RequestParam final Integer curveId) {

        boolean result = curvePointService.deleteCurvePoint(curveId);

        if (result) {
            return new ResponseEntity<CurvePoint>(HttpStatus.OK);
        }
        return new ResponseEntity<CurvePoint>(HttpStatus.BAD_REQUEST);
    }

}
