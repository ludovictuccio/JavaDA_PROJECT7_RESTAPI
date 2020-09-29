package com.nnk.springboot.services;

import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

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
     * Method used to check if the bidList entity entered is valid (to validate
     * javax constraints in model class)
     *
     * @param bid
     * @return bid
     */
    public BidList checkValidBid(final BidList bid) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<BidList>> constraintViolations = validator
                .validate(bid);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<BidList> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return bid;
    }

    /**
     * Method service used to find all bid lists.
     */
    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    /**
     * Method service used to save a valid bidList.
     * 
     * @param bidAccount
     * @param bidType
     * @param bidQuantity
     * @return bid or null
     */
    public BidList saveBid(final String bidAccount, final String bidType,
            final Double bidQuantity) {
        BidList bid = new BidList();
        bid.setAccount(bidAccount);
        bid.setType(bidType);
        bid.setBidQuantity(bidQuantity);

        if (checkValidBid(bid) == null) {
            return null;
        }
        bidListRepository.save(bid);
        return bid;
    }

    /**
     * Method service used to update a bidList by his id.
     * 
     * @param bidId
     * @param bid   list
     * @return bid saved or null
     */
    public BidList updateBid(final BidList bid, final String bidAccount,
            final String bidType) {

        BidList existingBid = bidListRepository.findById(bid.getBidListId())
                .orElse(null);

        if (existingBid == null) {
            LOGGER.error("Unknow id bidList");
            return null;
        } else if (checkValidBid(bid) == null) {
            LOGGER.error("Invalid bidlist. Please check the informations.");
            return null;
        } else if (!bidAccount.equals(existingBid.getAccount())
                || !bidType.equals(existingBid.getType())) {
            LOGGER.error(
                    "Invalid account or type. Please check the informations: the account and type must be the same as the bid to update.");
            return null;
        } else
            bidListRepository.delete(existingBid);
        return bidListRepository.save(bid);
    }

    /**
     * Method service used to delete a bidList by his id.
     *
     * @param bidId
     * @param bidAccount
     * @return isDeleted boolean
     */
    public boolean deleteBid(final Integer bidId, final String bidAccount) {
        boolean isDeleted = false;

        BidList existingBid = bidListRepository.findById(bidId).orElse(null);

        if (existingBid == null) {
            LOGGER.error("Unknow id bidList");
            return isDeleted;
        } else if (!bidAccount.equals(existingBid.getAccount())) {
            LOGGER.error(
                    "Invalid account entry. Please check the informations: the account must be the same as the bid to delete.");
            return isDeleted;
        } else
            bidListRepository.delete(existingBid);
        isDeleted = true;
        return isDeleted;
    }

}
