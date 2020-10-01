package com.nnk.springboot.controllers.api;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import com.nnk.springboot.services.IUserService;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class UserControllerApiRestIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private IUserService userService;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @BeforeEach
    public void setUpPerTest() {
        userRepository.deleteAll();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - User")
    public void givenValidInfos_whenCreate_thenReturnCreated()
            throws Exception {

        User userToCreate = new User("username", "Password1&", "fullname",
                "user");
        userToCreate.setId(1);
        String jsonContent = objectMapper.writeValueAsString(userToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - OK - Admin")
    public void givenValidInfosAdmin_whenCreate_thenReturnCreated()
            throws Exception {

        User userToCreate = new User("username", "Password1&", "fullname",
                "admin");
        userToCreate.setId(1);
        String jsonContent = objectMapper.writeValueAsString(userToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isCreated())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isCreated()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - Username already exists in DB")
    public void givenAlreadyExistingUsername_whenCreate_thenReturnBadRequest()
            throws Exception {
        userService.saveUser(
                new User("username", "Password1&99", "fullname99", "user"));
        userRepository.findAll().get(0).setId(9);

        User userToCreate = new User("username", "Password1&", "fullname",
                "user");
        userToCreate.setId(10);
        String jsonContent = objectMapper.writeValueAsString(userToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("CREATE")
    @DisplayName("Create - ERROR - no user or admin")
    public void givenUserRoleNotValid_whenCreate_thenReturnBadRequest()
            throws Exception {
        userService.saveUser(
                new User("username", "Password1&99", "fullname99", "user"));
        userRepository.findAll().get(0).setId(9);

        User userToCreate = new User("username", "Password1&", "fullname",
                "other");
        userToCreate.setId(10);
        String jsonContent = objectMapper.writeValueAsString(userToCreate);

        this.mockMvc
                .perform(MockMvcRequestBuilders.post("/api/user/add")
                        .contentType(APPLICATION_JSON).content(jsonContent))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isBadRequest()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 2 users in db")
    public void givenTwoUsersInDb_whenGet_thenReturnListWithTwoUsers()
            throws Exception {
        userService.saveUser(
                new User("username1", "Password1&99", "fullname99", "user"));
        userService.saveUser(
                new User("username2", "Password1&99", "fullname99", "user"));

        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

    @Test
    @Tag("GET")
    @DisplayName("Get - OK - 0 user in db")
    public void givenZeroUsersInDb_whenGet_thenReturnEmptyList()
            throws Exception {
        this.mockMvc
                .perform(MockMvcRequestBuilders.get("/api/user/get")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk()).andReturn();
    }

}
