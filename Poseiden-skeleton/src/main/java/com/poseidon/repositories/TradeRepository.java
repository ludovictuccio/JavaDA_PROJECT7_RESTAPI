package com.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.domain.Trade;

@Repository
public interface TradeRepository extends JpaRepository<Trade, Integer> {
}
