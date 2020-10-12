package com.poseidon.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.domain.Rating;
import com.poseidon.repositories.RatingRepository;
import com.poseidon.util.ConstraintsValidation;

/**
 * Rating Service class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class RatingService implements IRatingService {

    private static final Logger LOGGER = LogManager.getLogger("RatingService");

    @Autowired
    private RatingRepository ratingRepository;

    /**
     * Method service used to save a new rating.
     *
     * @param rating
     * @return rating
     */
    public Rating saveRating(final Rating rating) {

        if (ConstraintsValidation.checkValidRating(rating) == null) {
            return null;
        } else if (ratingRepository
                .findByOrderNumber(rating.getOrderNumber()) != null) {
            LOGGER.error(
                    "ERROR: this order number is already used. Please change.");
            return null;
        }
        ratingRepository.save(rating);
        return rating;
    }

    /**
     * Method service used to find all rating.
     *
     * @return all rating
     */
    public List<Rating> findAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * Method service used to update a rating.
     *
     * @param rating
     * @param ratingId the rating id
     * @return isUpdated boolean
     */
    public boolean updateRating(final Integer ratingId, final Rating rating) {
        boolean isUpdated = false;

        if (ConstraintsValidation.checkValidRating(rating) == null) {
            return isUpdated;
        }
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElse(null);

        if (existingRating == null) {
            LOGGER.error("Unknow rating id for number: {}", ratingId);
            return isUpdated;
        } else if (!rating.getOrderNumber()
                .equals(existingRating.getOrderNumber())) {
            LOGGER.error(
                    "Impossible to change order number. Please try again.");
            return isUpdated;
        }
        existingRating.setFitchRating(rating.getFitchRating());
        existingRating.setMoodysRating(rating.getMoodysRating());
        existingRating.setSandPRating(rating.getSandPRating());
        ratingRepository.save(existingRating);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * Method service used to delete a rating with his id.
     *
     * @param ratingId the rating id
     * @return isDeleted boolean
     */
    public boolean deleteRating(final Integer ratingId) {
        boolean isDeleted = false;

        Rating existingRating = ratingRepository.findById(ratingId)
                .orElse(null);

        if (existingRating == null) {
            LOGGER.error("Unknow rating id for number: {}", ratingId);
            return isDeleted;
        }
        ratingRepository.delete(existingRating);
        isDeleted = true;
        return isDeleted;
    }

    /**
     * Method service used to find a Rating by his id.
     *
     * @param id
     * @return existingRating a Rating entity
     */
    public Rating getRatingById(final Integer id) {

        Rating existingRating = ratingRepository.findById(id)
                .orElseThrow(() -> new NullPointerException(
                        "Rating not found for this id."));

        return existingRating;
    }
}
