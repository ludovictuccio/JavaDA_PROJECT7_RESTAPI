package com.poseidon.services;

import java.util.List;

import com.poseidon.domain.CurvePoint;

/**
 * ICurvePointService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface ICurvePointService {

    CurvePoint saveCurvePoint(Integer curveId, Double term, Double value);

    List<CurvePoint> findAllCurvePoints();

    CurvePoint updateCurvePoint(Integer curveId, Double term, Double value);

    boolean deleteCurvePoint(Integer curveId);

}
