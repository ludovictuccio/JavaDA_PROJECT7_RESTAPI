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

import com.poseidon.domain.Rating;
import com.poseidon.services.IRatingService;

@RestController
@Validated
@RequestMapping("/api/rating")
public class RatingControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("RatingControllerApiRest");

    @Autowired
    private IRatingService ratingService;

    /**
     * Method controller used to add a new rating.
     *
     * @param rating
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<Rating> addRating(
            @Valid @RequestBody final Rating rating) {

        Rating result = ratingService.saveRating(rating);

        if (result != null) {
            LOGGER.info("POST request SUCCESS for: /api/rating/add");
            return new ResponseEntity<Rating>(HttpStatus.CREATED);
        }
        LOGGER.info("POST request FAILED for: /api/rating/add");
        return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all rating.
     *
     * @return all rating
     */
    @GetMapping("/get")
    public List<Rating> getAllRating() {
        LOGGER.info("GET request SUCCESS for: /api/rating/get");
        return ratingService.findAllRating();
    }

    /**
     * Method controller used to update a rating.
     *
     * @param id
     * @param rating
     * @return ResponseEntity (ok or bad request)
     */
    @PutMapping("/update")
    public ResponseEntity<Rating> updateRating(
            @Valid @RequestParam final Integer id,
            @Valid @RequestBody final Rating rating) {

        boolean result = ratingService.updateRating(id, rating);

        if (result) {
            LOGGER.info("PUT request SUCCESS for: /api/rating/update");
            return new ResponseEntity<Rating>(HttpStatus.OK);
        }
        LOGGER.info("PUT request FAILED for: /api/rating/update");
        return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a rating.
     *
     * @param id the rating id
     * @return ResponseEntity (ok or bad request)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<Rating> deleteBidList(
            @Valid @RequestParam final Integer id) {
        boolean result = ratingService.deleteRating(id);

        if (result) {
            LOGGER.info("DELETE request SUCCESS for: /api/rating/delete");
            return new ResponseEntity<Rating>(HttpStatus.OK);
        }
        LOGGER.info("DELETE request FAILED for: /api/rating/delete");
        return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
    }

}
