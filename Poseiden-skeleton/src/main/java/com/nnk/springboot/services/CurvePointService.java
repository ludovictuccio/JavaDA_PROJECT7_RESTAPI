package com.nnk.springboot.services;

import java.time.LocalDateTime;
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

        if (checkValidCurvePoint(curvepoint) == null) {
            return null;
        }
        curvePointRepository.save(curvepoint);
        return curvepoint;
    }

}
