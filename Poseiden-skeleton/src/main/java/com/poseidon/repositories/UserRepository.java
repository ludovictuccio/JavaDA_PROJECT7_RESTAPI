package com.poseidon.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.poseidon.domain.User;

@Repository
public interface UserRepository
        extends JpaRepository<User, Integer>, JpaSpecificationExecutor<User> {

    User findUserByUsername(String username);
}
