package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.repositories.TradeRepository;

/**
 * TradeService Service class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class TradeService implements ITradeService {

    private static final Logger LOGGER = LogManager.getLogger("TradeService");

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Method used to check if the Trade entity entered is valid (to validate
     * javax constraints in model class).
     *
     * @param trade
     * @return trade or null
     */
    public Trade checkValidTrade(final Trade trade) {
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
        } else if (trade.getTradeDate().isAfter(LocalDateTime.now())) {
            LOGGER.error(
                    "The trade date can not be after actual date. Please check the format: dd/MM/yyyy HH:mm");
            return null;
        }
        return trade;
    }

    /**
     * Method service used to save a new trade.
     *
     * @param trade
     * @return trade
     */
    public Trade saveTrade(final Trade trade) {

        if (checkValidTrade(trade) == null || trade.getCreationName().isEmpty()
                || trade.getCreationName().isBlank()
                || trade.getCreationName() == null) {
            LOGGER.error(
                    "Failed to add a new trade. Please check that 'creation name' is not empty.");
            return null;
        } else if (!trade.getRevisionName().isEmpty()) {
            LOGGER.error(
                    "The revision name can not be no-empty. Itis use for trade update process.");
            return null;
        }
        trade.setCreationDate(LocalDateTime.now());
        tradeRepository.save(trade);
        return trade;
    }
}
