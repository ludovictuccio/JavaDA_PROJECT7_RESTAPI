package com.poseidon.services;

import java.time.LocalDateTime;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.poseidon.domain.CurvePoint;
import com.poseidon.repositories.CurvePointRepository;
import com.poseidon.util.ConstraintsValidation;

/**
 * CurvePoint Service class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class CurvePointService implements ICurvePointService {

    private static final Logger LOGGER = LogManager
            .getLogger("CurvePointService");

    @Autowired
    private CurvePointRepository curvePointRepository;

    /**
     * Method service used to save a new curvepoint. Curve id must not be empty
     * or null.
     *
     * @param curveId
     * @param term
     * @param value
     * @return curvepoint saved in db
     */
    public CurvePoint saveCurvePoint(final Integer curveId, final Double term,
            final Double value) {
        CurvePoint curvepoint = new CurvePoint();
        curvepoint.setCreationDate(LocalDateTime.now());
        curvepoint.setCurveId(curveId);
        curvepoint.setTerm(term);
        curvepoint.setValue(value);

        // check that the curve id is not already existing
        for (CurvePoint existingCurvePoint : curvePointRepository.findAll()) {
            if (existingCurvePoint.getCurveId().equals(curveId)) {
                LOGGER.error(
                        "Failed to create a new curve point: the id {} already exists.",
                        curveId);
                return null;
            }
        }

        if (ConstraintsValidation.checkValidCurvePoint(curvepoint) == null) {
            return null;
        }
        curvePointRepository.save(curvepoint);
        return curvepoint;
    }

    /**
     * Method service used to find all curve points.
     *
     * @return all curve points
     */
    public List<CurvePoint> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Method service used to update a curve point by his curve id.
     *
     * @param curveId
     * @param term
     * @param value
     * @return curve point saved
     */
    public CurvePoint updateCurvePoint(final Integer curveId, final Double term,
            final Double value) {

        CurvePoint existingCurvePoint = curvePointRepository
                .findByCurveId(curveId);

        if (existingCurvePoint == null) {
            LOGGER.error("Unknow id curve point for: {}", curveId);
            return null;
        }
        existingCurvePoint.setAsOfDate(LocalDateTime.now());
        existingCurvePoint.setTerm(term);
        existingCurvePoint.setValue(value);
        return curvePointRepository.save(existingCurvePoint);
    }

    /**
     * Method service used to delete a curve point by his curve id.
     *
     * @param curveId
     * @return isDeleted boolean
     */
    public boolean deleteCurvePoint(final Integer curveId) {
        boolean isDeleted = false;

        CurvePoint existingCurvePoint = curvePointRepository
                .findByCurveId(curveId);

        if (existingCurvePoint == null) {
            LOGGER.error("Unknow curve id for number: {}", curveId);
            return isDeleted;
        }
        curvePointRepository.delete(existingCurvePoint);
        isDeleted = true;
        return isDeleted;
    }

}
