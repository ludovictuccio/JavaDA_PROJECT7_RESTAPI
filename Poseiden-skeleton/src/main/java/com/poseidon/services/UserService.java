package com.poseidon.services;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.util.ConstraintsValidation;

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

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Method service used to save a new user. Username is unique attribute and
     * role must be ADMIN or USER.
     *
     * @param user
     * @return user
     */
    public User saveUser(final User user) {

        if (ConstraintsValidation.checkValidUser(user) == null) {
            LOGGER.error("Save user process exit.");
            return null;
        } else if (userRepository
                .findUserByUsername(user.getUsername()) != null) {
            LOGGER.error(
                    "ERROR: this username is already used. Please change.");
            return null;
        }

        // Set id with last id added in db +1
        List<User> allUsers = userRepository.findAll();
        if (allUsers.size() > 0) {
            User lastUserAdded = allUsers.get(allUsers.size() - 1);
            user.setId(lastUserAdded.getId() + 1);
        } else {
            user.setId(1l);
        }

        user.setRole(user.getRole().toUpperCase());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user;
    }

    /**
     * Method service used to find all User.
     *
     * @return all users
     */
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    /**
     * Method service used to update an user.
     *
     * @param user
     * @return isUpdated boolean
     */
    public boolean updateUser(final User user) {
        boolean isUpdated = false;

        if (ConstraintsValidation.checkValidUser(user) == null) {
            return isUpdated;
        }
        User existingUser = userRepository
                .findUserByUsername(user.getUsername());

        if (existingUser == null) {
            LOGGER.error("Unknow user for username: {}", user.getUsername());
            return isUpdated;
        }
        existingUser
                .setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        existingUser.setFullname(user.getFullname());
        existingUser.setRole(user.getRole().toUpperCase());
        userRepository.save(existingUser);
        isUpdated = true;
        return isUpdated;
    }

    /**
     * Method service used to delete an user with his username (for api rest).
     *
     * @param username
     * @return isDeleted boolean
     */
    public boolean deleteUser(final String username) {
        boolean isDeleted = false;

        User existingUser = userRepository.findUserByUsername(username);

        if (existingUser == null) {
            LOGGER.error("Unknow user for username: {}", username);
            return isDeleted;
        }
        userRepository.delete(existingUser);
        isDeleted = true;
        return isDeleted;
    }
}
