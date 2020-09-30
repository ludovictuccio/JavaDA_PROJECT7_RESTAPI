package com.nnk.springboot.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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

}
