package com.nnk.springboot.services;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;

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
     * Method used to check if the Rating entity entered is valid (to validate
     * javax constraints in model class).
     *
     * @param rating
     * @return rating or null
     */
    public Rating checkValidRating(final Rating rating) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Rating>> constraintViolations = validator
                .validate(rating);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<Rating> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return rating;
    }

    /**
     * Method service used to save a new rating.
     *
     * @param rating
     * @return rating
     */
    public Rating saveRating(final Rating rating) {

        if (checkValidRating(rating) == null) {
            return null;
        }
        ratingRepository.save(rating);
        return rating;
    }

    /**
     * Method service used to find all rating.
     */
    public List<Rating> findAllRating() {
        return ratingRepository.findAll();
    }

    /**
     * Method service used to update a rating.
     *
     * @param rating
     * @param ratingId  the rating id
     * @param isUpdated boolean
     */
    public boolean updateRating(final Integer ratingId, final Rating rating) {
        boolean isUpdated = false;

        if (checkValidRating(rating) == null) {
            return isUpdated;
        }
        Rating existingRating = ratingRepository.findById(ratingId)
                .orElse(null);

        if (existingRating == null) {
            LOGGER.error("Unknow rating id for number: {}", ratingId);
            return isUpdated;
        }
        existingRating.setFitchRating(rating.getFitchRating());
        existingRating.setMoodysRating(rating.getMoodysRating());
        existingRating.setSandPRating(rating.getSandPRating());
        existingRating.setOrderNumber(rating.getOrderNumber());
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
}
