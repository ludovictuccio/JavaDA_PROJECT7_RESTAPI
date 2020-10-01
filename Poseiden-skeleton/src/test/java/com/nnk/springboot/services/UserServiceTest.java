package com.nnk.springboot.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.annotation.DirtiesContext;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;

@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
public class UserServiceTest {

    @Autowired
    public IUserService userService;

    @MockBean
    private UserRepository userRepository;

    private User user;
    private User userTwo;
    private User result;

    private List<User> allUsers;

    @BeforeEach
    public void setUpPerTest() {
        allUsers = new ArrayList<>();

        user = new User("username1", "validPassword1&", "fullname1", "role1");
        user.setId(1);
        allUsers.add(user);

        userTwo = new User("username2", "validPassword2&", "fullname2",
                "role2");
        userTwo.setId(2);
        allUsers.add(userTwo);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - OK")
    public void givenValidUser_whenSave_thenReturnSaved() {
        // GIVEN

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result.getUsername()).isEqualTo("username1");
        assertThat(result.getPassword()).isEqualTo("validPassword1&");
        assertThat(result.getFullname()).isEqualTo("fullname1");
        assertThat(result.getRole()).isEqualTo("role1");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty username")
    public void givenEmptyUsername_whenSave_thenReturnNull() {
        // GIVEN
        user.setUsername("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Username already used in DB")
    public void givenAlreadyExistingUsername_whenSave_thenReturnNull() {
        // GIVEN
        when(userRepository.save(user)).thenReturn(user);
        when(userRepository.findAll()).thenReturn(allUsers);
        when(userRepository.findUserByUsername(user.getUsername()))
                .thenReturn(user);

        User alreadyExistingUsername = new User("username1", "validPassword1&",
                "fullname1", "role1");
        alreadyExistingUsername.setId(80);

        // WHEN
        result = userService.saveUser(alreadyExistingUsername);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Attribute size > max allowed")
    public void givenSizeMaxThanAllowes_whenSave_thenReturnNull() {
        // GIVEN
        user.setUsername(
                "123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 123456789 12345 9856");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty password")
    public void givenEmptyPassword_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty fullname")
    public void givenEmptyFullname_whenSave_thenReturnNull() {
        // GIVEN
        user.setFullname("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Empty role")
    public void givenEmptyRole_whenSave_thenReturnNull() {
        // GIVEN
        user.setRole("");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without uppercase letter")
    public void givenPasswordWithoutUppercaseLetter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("password1&");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without lowercase letter")
    public void givenPasswordWithoutLowercaseLetter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("PASSWORD1&");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without special character")
    public void givenPasswordWithoutSpecialCharacter_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Password1");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password size < 8")
    public void givenPasswordWithSizeLessThanMini_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Pass1*");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }

    @Test
    @Tag("SAVE")
    @DisplayName("Save User - ERROR - Password without digit")
    public void givenPasswordWithoutDigits_whenSave_thenReturnNull() {
        // GIVEN
        user.setPassword("Password*");

        // WHEN
        result = userService.saveUser(user);

        // THEN
        assertThat(result).isNull();
        verify(userRepository, times(0)).save(user);
    }
}
