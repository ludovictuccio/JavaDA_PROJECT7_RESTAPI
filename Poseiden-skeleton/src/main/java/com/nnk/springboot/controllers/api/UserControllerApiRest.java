package com.nnk.springboot.controllers.api;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.services.IUserService;

@RestController
@Validated
@RequestMapping("/api/user")
public class UserControllerApiRest {

    @Autowired
    private IUserService userService;

    /**
     * Method controller used to add a new user.
     *
     * @param user
     * @return ResponseEntity (created or bad request)
     */
    @PostMapping("/add")
    public ResponseEntity<User> addUser(@Valid @RequestBody final User user) {

        User result = userService.saveUser(user);

        if (result != null) {
            return new ResponseEntity<User>(HttpStatus.CREATED);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to find all Users.
     *
     * @return all Users
     */
    @GetMapping("/get")
    public List<User> getAllUsers() {
        return userService.findAllUsers();
    }

    /**
     * Method controller used to update an user.
     *
     * @param user
     * @return ResponseEntity (ok or bad request)
     */
    @PutMapping("/update")
    public ResponseEntity<User> updateUser(
            @Valid @RequestBody final User user) {

        boolean result = userService.updateUser(user);

        if (result) {
            return new ResponseEntity<User>(HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }

    /**
     * Method controller used to delete an user.
     *
     * @param username
     * @return ResponseEntity (ok or bad request)
     */
    @DeleteMapping("/delete")
    public ResponseEntity<User> deleteUser(
            @Valid @RequestParam final String username) {
        boolean result = userService.deleteUser(username);

        if (result) {
            return new ResponseEntity<User>(HttpStatus.OK);
        }
        return new ResponseEntity<User>(HttpStatus.BAD_REQUEST);
    }
}
