package com.nnk.springboot.controllers.api;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

@RestController
public class BidListControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("BidListControllerApiRest");

    @Autowired
    private IBidListService bidListService;

    @PostMapping("/bidList")
    // @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public BidList addBidList(@Valid @RequestParam final String bidAccount,
            @Valid @RequestParam final String bidType,
            @Valid @RequestParam final Double bidQuantity) {
        return bidListService.saveBid(bidAccount, bidType, bidQuantity);
    }

}
