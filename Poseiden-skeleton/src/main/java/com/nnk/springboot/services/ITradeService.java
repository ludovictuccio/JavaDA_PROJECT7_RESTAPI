package com.nnk.springboot.services;

import com.nnk.springboot.domain.Trade;

/**
 * ITradeService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface ITradeService {

    Trade saveTrade(Trade trade);

}
