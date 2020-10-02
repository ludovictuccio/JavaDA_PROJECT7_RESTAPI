package com.poseidon.services;

import java.util.List;

import com.poseidon.domain.User;

/**
 * IUserService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface IUserService {

    User saveUser(User user);

    List<User> findAllUsers();

    boolean updateUser(User user);

    boolean deleteUser(String username);

}
