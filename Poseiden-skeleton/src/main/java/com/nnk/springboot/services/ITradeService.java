package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.Trade;

/**
 * ITradeService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface ITradeService {

    Trade saveTrade(Trade trade);

    List<Trade> findAllTrade();

    boolean updateTrade(Integer tradeId, Trade trade);

}
