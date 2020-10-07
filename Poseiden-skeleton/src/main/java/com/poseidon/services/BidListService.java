package com.poseidon.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.domain.BidList;
import com.poseidon.repositories.BidListRepository;
import com.poseidon.util.ConstraintsValidation;

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
     * Method service used to save a valid bidList (API).
     *
     * @param bidAccount
     * @param bidType
     * @param bidQuantity
     * @return bid or null
     */
    public BidList saveBid(final String bidAccount, final String bidType,
            final Double bidQuantity) {
        BidList bid = new BidList();
        bid.setCreationDate(LocalDateTime.now());
        bid.setAccount(bidAccount);
        bid.setType(bidType);
        bid.setBidQuantity(bidQuantity);

        if (ConstraintsValidation.checkValidBid(bid) == null) {
            return null;
        }
        bidListRepository.save(bid);
        return bid;
    }

    /**
     * Method service used to find all bid lists.
     *
     * @return all bidList
     */
    public List<BidList> findAllBids() {
        return bidListRepository.findAll();
    }

    /**
     * Method service used to update a bidList.
     *
     * @param bid
     * @param bidAccount
     * @param bidType
     * @return bid saved or null
     */
    public BidList updateBid(final BidList bid, final String bidAccount,
            final String bidType) {
        try {
            BidList existingBid = bidListRepository.findById(bid.getBidListId())
                    .orElse(null);

            if (existingBid == null) {
                LOGGER.error("Unknow id bidList");
                return null;
            } else if (ConstraintsValidation.checkValidBid(bid) == null) {
                LOGGER.error("Invalid bidlist. Please check the informations.");
                return null;
            } else if (!bidAccount.equals(existingBid.getAccount())
                    || !bidType.equals(existingBid.getType())) {
                LOGGER.error(
                        "Invalid account or type. Please check the informations: the account and type must be the same as the bid to update.");
                return null;
            } else if (bid.getRevisionName().isBlank()) {
                LOGGER.error(
                        "Failed to update bidList: the revision name can not be empty.");
                return null;
            } else if (bid.getBidListDate().isAfter(LocalDateTime.now())
                    || bid.getBidListDate() == null) {
                LOGGER.error(
                        "The trade date can not be after actual date or null. Please check the format: dd/MM/yyyy HH:mm");
                return null;
            }
            bid.setRevisionDate(LocalDateTime.now());
            bid.setCreationDate(existingBid.getCreationDate());
            bidListRepository.delete(existingBid);
        } catch (NullPointerException np) {
            LOGGER.error(
                    "Null pointer exception. Please check that bidlist date is entered.");
            return null;
        }
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
            LOGGER.error("Unknow id bidList for id: {}", bidId);
            return isDeleted;
        } else if (!bidAccount.equals(existingBid.getAccount())) {
            LOGGER.error(
                    "Invalid account entry. Please check the informations: the account must be the same as the bid to delete.");
            return isDeleted;
        } else {
            bidListRepository.delete(existingBid);
        }
        isDeleted = true;
        return isDeleted;
    }

    /**
     * Method service used to find a bidList by his id.
     *
     * @param id
     * @return bidList a bidList entity
     */
    public BidList getBidById(final Integer id) {

        BidList existingBid = bidListRepository.findById(id).orElse(null);

        if (existingBid == null) {
            LOGGER.error("BidList not found for id: {}", id);
            return null;
        }
        return existingBid;
    }

}
