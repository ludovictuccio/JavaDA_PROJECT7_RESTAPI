package com.poseidon.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.domain.Trade;
import com.poseidon.repositories.TradeRepository;
import com.poseidon.util.ConstraintsValidation;

/**
 * TradeService class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class TradeService implements ITradeService {

    private static final Logger LOGGER = LogManager.getLogger("TradeService");

    @Autowired
    private TradeRepository tradeRepository;

    /**
     * Method service used to save a new trade.
     *
     * @param trade
     * @return trade
     */
    public Trade saveTrade(final Trade trade) {

        if (ConstraintsValidation.checkValidTrade(trade) == null
                || trade.getCreationName().isEmpty()
                || trade.getCreationName().isBlank()
                || trade.getCreationName() == null) {
            LOGGER.error(
                    "Failed to add a new trade. Please check that 'creation name' is not empty.");
            return null;
        } else if (!trade.getRevisionName().isBlank()) {
            LOGGER.error(
                    "The revision name can not be no-empty. It's use for trade update process.");
            return null;
        } else if (trade.getCreationName().isBlank()) {
            LOGGER.error("The creation name can not be empty.");
            return null;
        }
        trade.setCreationDate(LocalDateTime.now());
        tradeRepository.save(trade);
        return trade;
    }

    /**
     * Method service used to find all Trades.
     *
     * @return all trades
     */
    public List<Trade> findAllTrade() {
        return tradeRepository.findAll();
    }

    /**
     * Method service used to update a trade.
     *
     * @param tradeId the trade id
     * @param trade   a Trade object
     * @return isUpdated boolean
     */
    public boolean updateTrade(final Integer tradeId, final Trade trade) {
        boolean isUpdated = false;

        if (ConstraintsValidation.checkValidTrade(trade) == null) {
            return isUpdated;
        }
        Trade existingTrade = tradeRepository.findById(tradeId).orElse(null);

        if (existingTrade == null) {
            LOGGER.error("Unknow trade id for number: {}", tradeId);
            return isUpdated;
        } else if (trade.getRevisionName().isBlank()) {
            LOGGER.error("The revision name can't be empty.");
            return isUpdated;
        } else if (!trade.getCreationName()
                .equals(existingTrade.getCreationName())) {
            LOGGER.error("The creation name can't be changed.");
            return isUpdated;
        }
        trade.setRevisionDate(LocalDateTime.now());
        trade.setCreationDate(existingTrade.getCreationDate());
        tradeRepository.delete(existingTrade);
        tradeRepository.save(trade);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * Method service used to delete a trade with his id.
     *
     * @param tradeId the trade id
     * @return isDeleted boolean
     */
    public boolean deleteTrade(final Integer tradeId) {
        boolean isDeleted = false;

        Trade existingTrade = tradeRepository.findById(tradeId).orElse(null);

        if (existingTrade == null) {
            LOGGER.error("Unknow trade id for number: {}", tradeId);
            return isDeleted;
        }
        tradeRepository.delete(existingTrade);
        isDeleted = true;
        return isDeleted;
    }

    /**
     * Method service used to find a Trade by his id.
     *
     * @param id
     * @return existingTrade a Trade entity
     */
    public Trade getTradeById(final Integer id) {

        Trade existingTrade = tradeRepository.findById(id).orElse(null);

        if (existingTrade == null) {
            LOGGER.error("Trade not found for id: {}", id);
            return null;
        }
        return existingTrade;
    }

}
