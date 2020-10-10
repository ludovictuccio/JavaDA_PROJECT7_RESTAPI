package com.poseidon.controllers.view;

import org.springframework.stereotype.Controller;

@Controller
//@RequestMapping("app")
public class LoginController {

//    private static final Logger LOGGER = LogManager.getLogger("HomeController");
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @GetMapping("/login")
//    public String login(final Model model) {
//        LOGGER.info("GET request SUCCESS for: login");
//        // model.addAttribute("login", new AuthenticationRequest());
//        return "login";
//    }
//    @GetMapping("/login")
//    public String login(Model model, String error, String logout) {
//        if (error != null)
//            model.addAttribute("error",
//                    "Your username and password is invalid.");
//
//        if (logout != null)
//            model.addAttribute("message",
//                    "You have been logged out successfully.");
//
//        return "login";
//    }
//
//    @GetMapping("/app-logout")
//    public String appLogout() {
//        LOGGER.info("GET request SUCCESS for: logout");
//        return "redirect:/";
//    }

//    @GetMapping("secure/article-details")
//    public ModelAndView getAllUserArticles() {
//        ModelAndView mav = new ModelAndView();
//        mav.addObject("users", userRepository.findAll());
//        mav.setViewName("user/list");
//        LOGGER.info("GET request SUCCESS for: secure/article-details");
//        return mav;
//    }
//
//    @GetMapping("error")
//    public ModelAndView error() {
//        ModelAndView mav = new ModelAndView();
//        String errorMessage = "You are not authorized for the requested data.";
//        mav.addObject("errorMsg", errorMessage);
//        mav.setViewName("403");
//        LOGGER.info("GET request for: error");
//        return mav;
//    }
}
