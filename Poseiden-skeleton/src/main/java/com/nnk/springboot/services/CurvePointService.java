package com.nnk.springboot.services;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.repositories.CurvePointRepository;

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
     * Method used to check if the CurvePoint entity entered is valid (to
     * validate javax constraints in model class)
     *
     * @param curvepoint
     * @return curvepoint
     */
    public CurvePoint checkValidCurvePoint(final CurvePoint curvepoint) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<CurvePoint>> constraintViolations = validator
                .validate(curvepoint);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<CurvePoint> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        }
        return curvepoint;
    }

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

        if (checkValidCurvePoint(curvepoint) == null) {
            return null;
        }
        curvePointRepository.save(curvepoint);
        return curvepoint;
    }

    /**
     * Method service used to find all curve points.
     */
    public List<CurvePoint> findAllCurvePoints() {
        return curvePointRepository.findAll();
    }

    /**
     * Method service used to update a curve point by his curve id.
     * 
     * @param
     * @param
     * @return
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

}
