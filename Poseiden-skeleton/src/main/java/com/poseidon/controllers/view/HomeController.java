package com.poseidon.controllers.view;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.swagger.annotations.ApiOperation;

@Controller
public class HomeController {

    private static final Logger LOGGER = LogManager.getLogger("HomeController");

    @ApiOperation(value = "App home page (used to login)", notes = "VIEW - Get request - Home page")
    @GetMapping("/")
    public String home(final Model model) {
        LOGGER.info("User disconnected");
        return "home";
    }

    @GetMapping("/admin/home")
    public String adminHome(final Model model) {
        LOGGER.info("GET request SUCCESS for: /admin/home");
        return "redirect:/bidList/list";
    }

}
