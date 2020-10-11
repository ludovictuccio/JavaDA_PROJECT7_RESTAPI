package com.poseidon.controllers.view;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.formLogin;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.unauthenticated;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class LogincontrollerIT {

    private MockMvc mvc;

    @Autowired
    private WebApplicationContext wac;

    @BeforeEach
    public void setUpPerTest() {
        mvc = MockMvcBuilders.webAppContextSetup(wac).apply(springSecurity())
                .build();
    }

    @Test
    public void requiresAuthentication() throws Exception {
        mvc.perform(get("/")).andExpect(status().isOk());
    }

//    @Test
//    public void httpBasicAuthenticationSuccess() throws Exception {
//        mvc.perform(get("/").with(httpBasic("ludo45", "poseidon")))
//                .andExpect(status().isOk())
//                .andExpect(authenticated().withUsername("ludo45"));
//    }

    @Test
    @Tag("Form")
    @DisplayName("Form Authentication - ERROR")
    public void authenticationFailed() throws Exception {
        mvc.perform(formLogin().user("ludo45").password("invalid"))
                .andExpect(redirectedUrl("/login?error"))
                .andExpect(unauthenticated());
    }

    @Test
    @Tag("Authentication")
    @DisplayName("Authentication - ERROR - Invalid password")
    public void givenInvalidInfos_whenAuthentication_thenReturnUnhautorized()
            throws Exception {
        this.mvc.perform(get("/").with(httpBasic("ludo45", "invalid"))
                .contentType(APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

}
