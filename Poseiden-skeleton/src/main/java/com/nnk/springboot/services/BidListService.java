package com.nnk.springboot.services;

import java.util.List;

import javax.validation.Validator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.BidList;
import com.nnk.springboot.repositories.BidListRepository;

/**
 * BidListService class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class BidListService implements IBidListService {

    private static final Logger LOGGER = LogManager.getLogger("BidListService");

    @Autowired
    private BidListRepository bidListRepository;

    /**
     * Validator used to validate javax constraints in model classes.
     */
    private Validator validator;

    /**
     * Method used to check if the bidList entity entered is valid
     *
     * @param bid
     * @return isValid boolean
     */
//    public BidList checkValidBid(final BidList bid) {
//        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
//        validator = factory.getValidator();
//        Set<ConstraintViolation<BidList>> constraintViolations = validator
//                .validate(bid);
//        if (constraintViolations.size() > 0) {
//            LOGGER.error(
//                    "ERROR: a constraint was violated. Please check the informations entered.");
//            return null;
//        }
//        return bid;
//    }

    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    public BidList saveBid(final String bidAccount, final String bidType,
            final Double bidQuantity) {
        BidList bid = new BidList();
        bid.setAccount(bidAccount);
        bid.setType(bidType);
        bid.setBidQuantity(bidQuantity);

        // checkValidBid(bid);

        bidListRepository.save(bid);
        return bid;
    }

    public BidList saveBidList(final BidList bid) {
        // checkValidBid(bid);
        return bidListRepository.save(bid);
    }

}
