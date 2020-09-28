package com.nnk.springboot.controllers.api;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.services.IBidListService;

@RestController
@Validated
@RequestMapping("/bidList")
public class BidListControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("BidListControllerApiRest");

    @Autowired
    private IBidListService bidListService;

    @PostMapping
    public ResponseEntity<BidList> addBidList(
            @Valid @RequestParam final String bidAccount,
            @Valid @RequestParam final String bidType,
            @Valid @RequestParam final Double bidQuantity) {

        BidList result = bidListService.saveBid(bidAccount, bidType,
                bidQuantity);

        if (result != null) {
            return new ResponseEntity<BidList>(HttpStatus.CREATED);
        }
        return new ResponseEntity<BidList>(HttpStatus.BAD_REQUEST);
    }

}
