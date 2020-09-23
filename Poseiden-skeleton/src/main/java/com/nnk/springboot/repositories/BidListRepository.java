package com.nnk.springboot.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.nnk.springboot.domain.BidList;

@Transactional
@Repository
public interface BidListRepository extends JpaRepository<BidList, Integer> {

}
