package com.poseidon.controllers.view;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.services.UserService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUpPerTest() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
        userRepository.deleteAll();
    }

    @Test
    @Tag("/user/list")
    @DisplayName("Get - list")
    public void givenZeroUser_whenGetList_thenreturnOk() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user/list")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/add")
    @DisplayName("Get - add")
    public void givenUserPage_whenGetAdd_thenReturnOk() throws Exception {

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user/add")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("user"))
                .andReturn();
    }

    @Test
    @Tag("/user/validate")
    @DisplayName("Post - validate - OK")
    public void givenValidUser_whenValidate_thenReturnSaved() throws Exception {

        User user = new User(1l, "username", "Password1&", "fullname", "USER");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/user/validate")
                        .contentType(APPLICATION_JSON)
                        .param("id", user.getId().toString())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"))
                .andReturn();
    }

    @Test
    @Tag("/user/validate")
    @DisplayName("Post - validate - ERROR - Invalid password")
    public void givenInvalidPassword_whenValidate_thenReturnModelErrors()
            throws Exception {

        User user = new User(1l, "username", "passwordinvalid", "fullname",
                "USER");
        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/user/validate")
                        .contentType(APPLICATION_JSON)
                        .param("id", user.getId().toString())
                        .param("username", user.getUsername())
                        .param("password", user.getPassword())
                        .param("fullname", user.getFullname())
                        .param("role", user.getRole()))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Get - Update - OK")
    public void givenOneUser_whenUpdate_thenReturnUpdated() throws Exception {

        User user = new User(1l, "username", "Password1&", "fullname", "USER");
        userService.saveUser(user);

        this.mockMvc.perform(MockMvcRequestBuilders.get("/user/update/1"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(
                        MockMvcResultMatchers.model().attributeExists("user"))
                .andExpect(MockMvcResultMatchers.view().name("user/update"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Post - Update - OK")
    public void givenOneUser_whenUpdate_thenReturnUpdate() throws Exception {

        User user = new User(1l, "username", "Password1&", "fullname", "USER");
        userService.saveUser(user);
        assertThat(userRepository.findAll().get(0).getId()).isEqualTo(1l);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/user/update/1")
                        .contentType(MediaType.ALL)
                        .param("username", user.getUsername())
                        .param("password", "Password2&changed")
                        .param("fullname", "other").param("role", "ADMIN"))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.model().hasNoErrors())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"))
                .andReturn();
    }

    @Test
    @Tag("/user/update")
    @DisplayName("Post - Update - ERROR - Bad id")
    public void givenBadId_whenUpdate_thenReturnErrors() throws Exception {

        User user = new User(1l, "username", "Password1&", "fullname", "USER");
        userService.saveUser(user);
        assertThat(userRepository.findAll().get(0).getId()).isEqualTo(1l);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/user/update/99"))
                .andExpect(MockMvcResultMatchers.model().hasErrors())
                .andReturn();
    }

    @Test
    @Tag("/user/delete")
    @DisplayName("Delete - OK")
    public void givenUser_whenDelete_thenReturnDeleted() throws Exception {

        User user = new User(1l, "username", "Password1&", "fullname", "USER");
        userService.saveUser(user);
        assertThat(userRepository.findAll().get(0).getId()).isEqualTo(1l);

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/user/delete/1")
                        .contentType(MediaType.ALL))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(MockMvcResultMatchers.redirectedUrl("/user/list"))
                .andReturn();

        assertThat(userRepository.findAll().size()).isEqualTo(0);
    }

}
