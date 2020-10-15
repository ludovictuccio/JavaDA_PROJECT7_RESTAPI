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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.poseidon.domain.BidList;
import com.poseidon.services.IBidListService;

import io.swagger.annotations.ApiOperation;

@RestController
@Validated
public class BidListControllerApiRest {

    private static final Logger LOGGER = LogManager
            .getLogger("BidListControllerApiRest");

    @Autowired
    private IBidListService bidListService;

    /**
     * Method controller used to add a new bidList.
     *
     * @param bidAccount
     * @param bidType
     * @param bidQuantity
     * @return ResponseEntity (created or bad request)
     */
    @ApiOperation(value = "ADD Bid List", notes = "API REST - Need param bid account, type and quantity - Return ResponseEntity 201 created or 400 bad request.")
    @PostMapping("/api/bidList")
    public ResponseEntity<BidList> addBidList(
            @Valid @RequestParam final String bidAccount,
            @Valid @RequestParam final String bidType,
            @Valid @RequestParam final Double bidQuantity) {

        BidList result = bidListService.saveBid(bidAccount, bidType,
                bidQuantity);

        if (result != null) {
            LOGGER.info("POST request SUCCESS for: /api/bidList/add");
            return new ResponseEntity<BidList>(HttpStatus.CREATED);
        }
        LOGGER.info("POST request FAILED for: /api/bidList/add");
        return new ResponseEntity<BidList>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all bidList.
     *
     * @return all bid list
     */
    @GetMapping("/api/bidList")
    @ApiOperation(value = "GET Bid List", notes = "API REST - Return all bid list.")
    public List<BidList> getBidList() {
        LOGGER.info("GET request SUCCESS for: /api/bidList/get");
        return bidListService.findAllBids();
    }

    /**
     * Method controller used to update a bidList.
     *
     * @param bid
     * @param bidAccount
     * @param bidType
     * @return ResponseEntity (ok or bad request)
     */
    @ApiOperation(value = "UPDATE Bid List", notes = "API REST - Need param bid account, bid type and a Bid entity - Return ResponseEntity 200 OK or 400 bad request.")
    @PutMapping("/api/bidList")
    public ResponseEntity<BidList> updateBidList(
            @Valid @RequestBody final BidList bid,
            @Valid @RequestParam final String bidAccount,
            @Valid @RequestParam final String bidType) {

        BidList result = bidListService.updateBid(bid, bidAccount, bidType);

        if (result != null) {
            LOGGER.info("PUT request SUCCESS for: /api/bidList/update");
            return new ResponseEntity<BidList>(HttpStatus.OK);
        }
        LOGGER.info("PUT request FAILED for: /api/bidList/update");
        return new ResponseEntity<BidList>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete a bidList.
     *
     * @param bidId
     * @param bidAccount
     * @return ResponseEntity (created or bad request)
     */
    @ApiOperation(value = "DELETE Bid List", notes = "API REST - Need param bid id and bid account (to confirm that good bid list deleting process) - Return ResponseEntity 200 OK or 400 bad request.")
    @DeleteMapping("/api/bidList")
    public ResponseEntity<BidList> deleteBidList(
            @Valid @RequestParam final Integer bidId,
            @Valid @RequestParam final String bidAccount) {

        boolean result = bidListService.deleteBid(bidId, bidAccount);

        if (result) {
            LOGGER.info("DELETE request SUCCESS for: /api/bidList/delete");
            return new ResponseEntity<BidList>(HttpStatus.OK);
        }
        LOGGER.info("DELETE request FAILED for: /api/bidList/delete");
        return new ResponseEntity<BidList>(HttpStatus.BAD_REQUEST);
    }

}
