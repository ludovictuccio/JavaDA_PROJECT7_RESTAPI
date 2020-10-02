package com.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.poseidon.domain.CurvePoint;

@Repository
public interface CurvePointRepository
        extends JpaRepository<CurvePoint, Integer> {

    CurvePoint findByCurveId(Integer curveId);

}
