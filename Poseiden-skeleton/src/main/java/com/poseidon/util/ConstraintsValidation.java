package com.poseidon.util;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.poseidon.domain.BidList;
import com.poseidon.domain.CurvePoint;
import com.poseidon.domain.Rating;
import com.poseidon.domain.RuleName;
import com.poseidon.domain.Trade;
import com.poseidon.domain.User;

/**
 * This class is used to valid constraints entities.
 *
 * @author Ludovic Tuccio
 */
public class ConstraintsValidation {

    private static final Logger LOGGER = LogManager.getLogger("Validation");

    public static User checkValidUser(final User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator
                .validate(user);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<User> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        } else if (!user.getRole().equalsIgnoreCase("USER")
                && !user.getRole().equalsIgnoreCase("ADMIN")) {
            LOGGER.error("ERROR: the role must be 'admin' or 'user'");
            return null;
        }
        return user;
    }

    public static BidList checkValidBid(final BidList bid) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BidList>> constraintViolations = validator
                .validate(bid);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<BidList> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        } else if (bid.getBidListDate() != null
                && bid.getBidListDate().isAfter(LocalDateTime.now())) {
            LOGGER.error(
                    "The trade date can not be after actual date. Please check the format: dd/MM/yyyy HH:mm");
            return null;
        }
        return bid;
    }

    public static CurvePoint checkValidCurvePoint(final CurvePoint curvepoint) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CurvePoint>> constraintViolations = validator
                .validate(curvepoint);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<CurvePoint> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return curvepoint;
    }

    public static Rating checkValidRating(final Rating rating) {
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

    public static RuleName checkValidRuleName(final RuleName ruleName) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<RuleName>> constraintViolations = validator
                .validate(ruleName);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<RuleName> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return ruleName;
    }

    public static Trade checkValidTrade(final Trade trade) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<Trade>> constraintViolations = validator
                .validate(trade);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<Trade> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        } else if (trade.getTradeDate() != null
                && trade.getTradeDate().isAfter(LocalDateTime.now())) {
            LOGGER.error(
                    "The trade date can not be after actual date. Please check the format: dd/MM/yyyy HH:mm");
            return null;
        }
        return trade;
    }

}
