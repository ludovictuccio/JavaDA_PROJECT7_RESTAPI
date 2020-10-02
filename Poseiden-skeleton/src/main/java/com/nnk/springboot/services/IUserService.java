package com.nnk.springboot.services;

import java.util.List;

import com.nnk.springboot.domain.User;

/**
 * IUserService interface class.
 *
 * @author Ludovic Tuccio
 */
public interface IUserService {

    User saveUser(User user);

    List<User> findAllUsers();

    boolean updateUser(final User user);

}
