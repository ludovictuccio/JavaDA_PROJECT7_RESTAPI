package com.nnk.springboot.controllers.api;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.Trade;
import com.nnk.springboot.services.ITradeService;

@RestController
@Validated
@RequestMapping("/api/trade")
public class TradeControllerApiRest {

    @Autowired
    private ITradeService tradeService;

    /**
     * Method controller used to add a new trade.
     *
     * @param trade
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<Trade> addTrade(
            @Valid @RequestBody final Trade trade) {

        Trade result = tradeService.saveTrade(trade);

        if (result != null) {
            return new ResponseEntity<Trade>(HttpStatus.CREATED);
        }
        return new ResponseEntity<Trade>(HttpStatus.BAD_REQUEST);
    }

}
