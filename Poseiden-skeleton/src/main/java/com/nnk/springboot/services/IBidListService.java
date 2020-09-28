/**
 * 
 */
package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.BidList;

/**
 * IBidListService interface class.
 *
 * @author Ludovic Tuccio
 *
 */
public interface IBidListService {

    List<BidList> findAllBids();

    BidList saveBid(String bidAccount, String bidType, Double bidQuantity);

}
