package com.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.domain.Rating;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Integer> {

    Rating findByOrderNumber(Integer orderNumber);

}
