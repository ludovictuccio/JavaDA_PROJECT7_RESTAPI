package com.nnk.springboot.services;

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

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

/**
 * User Service class.
 *
 * @author Ludovic Tuccio
 */
@Service
public class UserService implements IUserService {

    private static final Logger LOGGER = LogManager.getLogger("UserService");

    @Autowired
    private UserRepository userRepository;

    /**
     * Method used to check if the User entity entered is valid (to validate
     * javax constraints in model class).
     *
     * @param user
     * @return user or null
     */
    public User checkValidUser(final User user) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<User>> constraintViolations = validator
                .validate(user);
        if (constraintViolations.size() > 0) {
            LOGGER.error(
                    "ERROR: a constraint was violated. Please check the informations entered.");
            for (ConstraintViolation<User> contraintes : constraintViolations) {
                LOGGER.error(contraintes.getRootBeanClass().getSimpleName()
                        + "." + contraintes.getPropertyPath() + " "
                        + contraintes.getMessage());
            }
            return null;
        } else if (!user.getRole().equalsIgnoreCase("USER")
                && !user.getRole().equalsIgnoreCase("ADMIN")) {
            LOGGER.error("ERROR: the role must be 'admin' or 'user'");
            return null;
        }
        return user;
    }

    /**
     * Method service used to save a new user. Username is unique attribute and
     * role must be ADMIN or USER.
     *
     * @param user
     * @return user
     */
    public User saveUser(final User user) {

        if (checkValidUser(user) == null) {
            LOGGER.error("Save user process exit.");
            return null;
        } else if (userRepository
                .findUserByUsername(user.getUsername()) != null) {
            LOGGER.error(
                    "ERROR: this username is already used. Please change.");
            return null;
        }
        user.setRole(user.getRole().toUpperCase());
        userRepository.save(user);
        return user;
    }

    /**
     * Method service used to find all User.
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Method service used to update an user.
     *
     * @param user
     * @param isUpdated boolean
     */
    public boolean updateUser(final User user) {
        boolean isUpdated = false;

        if (checkValidUser(user) == null) {
            return isUpdated;
        }
        User existingUser = userRepository
                .findUserByUsername(user.getUsername());

        if (existingUser == null) {
            LOGGER.error("Unknow username: {}", user.getUsername());
            return isUpdated;
        }
        existingUser.setPassword(user.getPassword());
        existingUser.setFullname(user.getFullname());
        existingUser.setRole(user.getRole().toUpperCase());
        userRepository.save(existingUser);
        isUpdated = true;
        return isUpdated;
    }
}
