package com.poseidon.services;

import static org.assertj.core.api.Assertions.assertThatNullPointerException;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class MyUserDetailsServiceTest {

    @Autowired
    private MyUserDetailsService myUserDetailsService;

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
    public void givenUnknowUsername_whenlogin_thenReturnNullPointer()
            throws Exception {
        assertThatNullPointerException().isThrownBy(() -> {
            myUserDetailsService.loadUserByUsername("425254");
        });
    }

    @Test
    @Tag("Login")
    @DisplayName("Login - Error - Empty username")
    public void givenEmptyUsername_whenlogin_thenReturnNullPointer()
            throws Exception {
        assertThatNullPointerException().isThrownBy(() -> {
            myUserDetailsService.loadUserByUsername("");
        });
    }

}
