package com.poseidon.services;

import java.util.List;

import com.poseidon.domain.Trade;

/**
 * ITradeService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface ITradeService {

    Trade saveTrade(Trade trade);

    List<Trade> findAllTrade();

    boolean updateTrade(Integer tradeId, Trade trade);

    boolean deleteTrade(Integer tradeId);

    Trade getTradeById(Integer id);

}
