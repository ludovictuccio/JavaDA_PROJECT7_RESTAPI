package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Rating;

/**
 * IRatingService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface IRatingService {

    Rating saveRating(Rating rating);

    List<Rating> findAllRating();

    boolean updateRating(Integer ratingId, Rating rating);

    boolean deleteRating(Integer ratingId);

}
