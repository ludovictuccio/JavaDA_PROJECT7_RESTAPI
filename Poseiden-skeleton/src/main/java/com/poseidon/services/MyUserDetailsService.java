package com.poseidon.services;

import java.util.Arrays;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;

@Service
public class MyUserDetailsService implements UserDetailsService {

    private static final Logger LOGGER = LogManager
            .getLogger("MyUserDetailsService");

    @Autowired
    private UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username)
            throws UsernameNotFoundException {

        User user = userRepository.findUserByUsername(username);

        GrantedAuthority authority = new SimpleGrantedAuthority(user.getRole());

        UserDetails userDetails = (UserDetails) new org.springframework.security.core.userdetails.User(
                user.getUsername(), user.getPassword(),
                Arrays.asList(authority));

        LOGGER.info("User: {} connected with role: {}", username, authority);

        return userDetails;
    }

}
