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

import com.poseidon.domain.BidList;
import com.poseidon.services.IBidListService;

@RestController
@Validated
@RequestMapping("/api/bidList")
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
    @PostMapping("/add")
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
    @GetMapping("/get")
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
    @PutMapping("/update")
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
    @DeleteMapping("/delete")
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
