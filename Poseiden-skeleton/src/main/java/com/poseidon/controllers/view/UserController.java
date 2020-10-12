package com.poseidon.controllers.view;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.services.UserService;

import io.swagger.annotations.ApiOperation;

@Controller
@RequestMapping("/user")
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger("UserController");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    /**
     * Get HTML page used to display all users list.
     *
     * @param model
     * @return /user/list.html page
     */
    @ApiOperation(value = "Users LIST (get)", notes = "VIEW - Return all Users list.")
    @GetMapping("/list")
    public String home(final Model model) {
        model.addAttribute("users", userService.findAllUsers());
        LOGGER.info("GET request SUCCESS for: /user/list");
        return "user/list";
    }

    /**
     * Get HTML page used to add a new user.
     *
     * @param model
     * @return /user/add.html page
     */
    @ApiOperation(value = "ADD User (get)", notes = "VIEW - Add new User")
    @GetMapping("/add")
    public String addUser(final Model model) {
        model.addAttribute("user", new User());
        LOGGER.info("GET request SUCCESS for: /user/add");
        return "user/add";
    }

    /**
     * Post HTML page used to validate a new user informations.
     *
     * @param user
     * @param result
     * @param model
     * @return /user/list.html page if good request, or /user/add
     */
    @ApiOperation(value = "VALIDATE User (post)", notes = "VIEW - Validate / save the new User")
    @PostMapping("/validate")
    public String validate(@Valid final User user, final BindingResult result,
            final Model model) {

        if (!result.hasErrors()) {
            User userToSave = userService.saveUser(user);
            model.addAttribute("user", userToSave);
            LOGGER.info("POST request SUCCESS for: /user/validate");
            return "redirect:/user/list";
        }
        LOGGER.info("POST request FAILED for: /user/validate");
        return "user/add";
    }

    /**
     * Get HTML page used to update an existing user.
     *
     * @param id
     * @param model
     * @return /user/list.html page if bad request, or /user/update
     */
    @ApiOperation(value = "UPDATE User (get)", notes = "VIEW - Get a User by id and retrieve to update it.")
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable("id") final Long id,
            final Model model) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            LOGGER.error("Invalid user Id: {}", id);
            LOGGER.info("GET request FAILED for: /user/update/{id}");
            return "redirect:/user/list";
        }
        model.addAttribute("user", user);
        LOGGER.info("GET request SUCCESS for: /user/update/{id}");
        return "user/update";
    }

    /**
     * Post HTML page used to update an user.
     *
     * @param id
     * @param user
     * @param result
     * @param model
     * @return /user/list.html page if good request, or /user/update
     */
    @ApiOperation(value = "UPDATE User (post)", notes = "VIEW - Update the User.")
    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") final Long id,
            @Valid final User user, final BindingResult result,
            final Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /user/update/{id}");
            return "user/update";
        }
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("user", user);
        LOGGER.info("POST request SUCCESS for: /user/update/{id}");
        return "redirect:/user/list";
    }

    /**
     * Get HTML page used to delete an user.
     *
     * @param id
     * @param model
     * @return /user/list.html page
     */
    @ApiOperation(value = "DELETE User (get)", notes = "VIEW - Get User with his id, and delete it.")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") final Long id,
            final Model model) {
        try {
            userRepository.deleteById(id);
            model.addAttribute("users", userService.findAllUsers());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Invalid user Id: {}", id);
            LOGGER.info("GET request FAILED for: /user/delete/{id}");
        }
        LOGGER.info("GET request SUCCESS for: /user/delete/{id}");
        return "redirect:/user/list";
    }

}
