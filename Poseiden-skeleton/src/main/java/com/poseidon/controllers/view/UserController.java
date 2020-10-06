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
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("user", new User());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid User user, BindingResult result,
            Model model) {

        if (!result.hasErrors()) {
            User userToSave = userService.saveUser(user);
            model.addAttribute("user", userToSave);
            return "redirect:/user/list";
        }
        return "user/add";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {

        User user = userRepository.findById(id).orElse(null);

        if (user == null) {
            LOGGER.error("Invalid user Id: {}", id);
            return "redirect:/user/list";
        }
        model.addAttribute("user", user);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Long id, @Valid User user,
            BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "user/update";
        }
        user.setId(id);
        userService.updateUser(user);
        model.addAttribute("user", user);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Long id, Model model) {
        try {
            userRepository.deleteById(id);
            model.addAttribute("users", userService.findAllUsers());
        } catch (IllegalArgumentException ex) {
            LOGGER.error("Invalid user Id: {}", id);
        }
        return "redirect:/user/list";
    }

}
