package com.nnk.springboot.services;

import com.nnk.springboot.domain.CurvePoint;

/**
 * ICurvePointService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface ICurvePointService {

    CurvePoint saveCurvePoint(Integer curveId, Double term, Double value);

}
