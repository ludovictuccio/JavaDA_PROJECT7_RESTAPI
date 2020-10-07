package com.poseidon.controllers.view;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.poseidon.domain.User;
import com.poseidon.repositories.UserRepository;
import com.poseidon.services.UserService;

@Controller
public class UserController {

    private static final Logger LOGGER = LogManager.getLogger("UserController");

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    @GetMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.findAllUsers());
        LOGGER.info("GET request SUCCESS for: /user/list");
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        LOGGER.info("GET request SUCCESS for: /user/add");
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result,
            Model model) {

        if (!result.hasErrors()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));
            User userToSave = userService.saveUser(user);
            model.addAttribute("user", userToSave);
            LOGGER.info("POST request SUCCESS for: /user/validate");
            return "redirect:/user/list";
        }
        LOGGER.info("POST request FAILED for: /user/validate");
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

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

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            LOGGER.info("POST request FAILED for: /user/update/{id}");
            return "user/update";
        }
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        user.setPassword(encoder.encode(user.getPassword()));
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("user", user);
        LOGGER.info("POST request SUCCESS for: /user/update/{id}");
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
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
