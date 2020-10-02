package com.poseidon.services;

import java.util.List;

import com.poseidon.domain.BidList;

/**
 * IBidListService interface class.
 *
 * @author Ludovic Tuccio
 *
 */
public interface IBidListService {

    List<BidList> findAllBids();

    BidList saveBid(String bidAccount, String bidType, Double bidQuantity);

    BidList updateBid(BidList bid, String bidAccount, String bidType);

    boolean deleteBid(Integer bidId, String bidAccount);

}
