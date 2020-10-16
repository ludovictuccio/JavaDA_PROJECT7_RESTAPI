package com.poseidon.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.domain.Trade;
import com.poseidon.services.ITradeService;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
@RequestMapping("/v1/trade")
public class TradeControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("TradeControllerApiRest");

    @Autowired
    private ITradeService tradeService;

    /**
     * Method controller used to add a new trade.
     *
     * @param trade
     * @return ResponseEntity (created or bad request)
     */
    @ApiOperation(value = "ADD Trade", notes = "API REST - Need Trade entity - Return ResponseEntity 201 created or 400 bad request.")
    @PostMapping
    public ResponseEntity<Trade> addTrade(
            @Valid @RequestBody final Trade trade) {

        Trade result = tradeService.saveTrade(trade);

        if (result != null) {
            LOGGER.info("POST request SUCCESS for: /api/trade/add");
            return new ResponseEntity<Trade>(HttpStatus.CREATED);
        }
        LOGGER.info("POST request FAILED for: /api/trade/add");
        return new ResponseEntity<Trade>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all trades.
     *
     * @return all trades
     */
    @ApiOperation(value = "GET Trade", notes = "API REST - Return all trades list.")
    @GetMapping
    public List<Trade> getAllTrade() {
        LOGGER.info("GET request SUCCESS for: /api/trade/get");
        return tradeService.findAllTrade();
    }

    /**
     * Method controller used to update a trade.
     *
     * @param id
     * @param trade
     * @return ResponseEntity (ok or bad request)
     */
    @ApiOperation(value = "UPDATE Trade", notes = "API REST - Need Trade entity and param trade id - Return ResponseEntity 200 OK or 400 bad request.")
    @PutMapping
    public ResponseEntity<Trade> updateTrade(
            @Valid @RequestParam final Integer id,
            @Valid @RequestBody final Trade trade) {

        boolean result = tradeService.updateTrade(id, trade);

        if (result) {
            LOGGER.info("PUT request SUCCESS for: /api/trade/update");
            return new ResponseEntity<Trade>(HttpStatus.OK);
        }
        LOGGER.info("PUT request FAILED for: /api/trade/update");
        return new ResponseEntity<Trade>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a trade.
     *
     * @param id the trade id
     * @return ResponseEntity (ok or bad request)
     */
    @ApiOperation(value = "DELETE Trade", notes = "API REST - Need param trade id - Return ResponseEntity 200 OK or 400 bad request.")
    @DeleteMapping
    public ResponseEntity<Trade> deleteTrade(
            @Valid @RequestParam final Integer id) {
        boolean result = tradeService.deleteTrade(id);

        if (result) {
            LOGGER.info("DELETE request SUCCESS for: /api/trade/delete");
            return new ResponseEntity<Trade>(HttpStatus.OK);
        }
        LOGGER.info("DELETE request FAILED for: /api/trade/delete");
        return new ResponseEntity<Trade>(HttpStatus.BAD_REQUEST);
    }

}
