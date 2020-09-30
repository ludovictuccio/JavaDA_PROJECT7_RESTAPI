package com.nnk.springboot.controllers.api;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.services.IRatingService;

@RestController
@Validated
@RequestMapping("/api/rating")
public class RatingControllerApiRest {

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
            return new ResponseEntity<Rating>(HttpStatus.CREATED);
        }
        return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all rating.
     *
     * @return all rating
     */
    @GetMapping("/get")
    public List<Rating> getAllRating() {
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
            return new ResponseEntity<Rating>(HttpStatus.OK);
        }
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
            return new ResponseEntity<Rating>(HttpStatus.OK);
        }
        return new ResponseEntity<Rating>(HttpStatus.BAD_REQUEST);
    }

}
