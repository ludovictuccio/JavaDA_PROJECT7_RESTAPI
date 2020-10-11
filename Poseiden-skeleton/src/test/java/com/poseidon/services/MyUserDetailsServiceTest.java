package com.poseidon.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.annotation.DirtiesContext;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MyUserDetailsServiceTest {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

    @MockBean
    private UserRepository userRepository;

    private String username = "username";
    private User user;

    @BeforeEach
    public void setUpPerTest() {
        user = new User("username1", "validPassword1&", "fullname1", "user");
        user.setId(1l);
    }

    @Test
    @Tag("Login")
    @DisplayName("Login - Ok")
    public void givenUsername_whenlogin_thenReturnOk() throws Exception {
        // GIVEN
        when(userRepository.findUserByUsername(username)).thenReturn(user);

        // WHEN
        UserDetails userDetails = myUserDetailsService
                .loadUserByUsername(username);

        // THEN
        assertThat(userDetails).isNotNull();
        assertThat(userDetails.getAuthorities().toString()).isEqualTo("[user]");
        assertThat(userDetails.getUsername()).isEqualTo("username1");
    }

//  @Test
//  @Tag("Login")
//  @DisplayName("Login - OK")
//  public void givenValidUsername_whenlogin_thenReturnOk() throws Exception {
//      UserDetails user = myUserDetailsService.loadUserByUsername("ludo45");
//      assertThat(user).isNotNull();
//  }

    @Test
    @Tag("Login")
    @DisplayName("Login - Error - Unknow username")
    public void givenUnknowUsername_whenlogin_thenReturnException()
            throws Exception {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> {
                    myUserDetailsService.loadUserByUsername("425254");
                });
    }

    @Test
    @Tag("Login")
    @DisplayName("Login - Error - Empty username")
    public void givenEmptyUsername_whenlogin_thenReturnException()
            throws Exception {
        assertThatExceptionOfType(UsernameNotFoundException.class)
                .isThrownBy(() -> {
                    myUserDetailsService.loadUserByUsername("");
                });
    }

}
