package com.poseidon.services;

import java.util.List;

import com.poseidon.domain.Rating;

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

    Rating getRatingById(Integer id);

}
