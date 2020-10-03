package com.example.webApp.controller;

import com.example.webApp.RegisterUserForm;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

//    RestTemplate restTemplate = new RestTemplate(); // used for communicate between services

    @GetMapping("/")
    public String homePage() {
        return "index";
    }

    @GetMapping("/userRegistration")
    public String userRegistration(Model model) {
        model.addAttribute("registerUser", new RegisterUserForm());
        return "userRegistration";
    }

    @GetMapping("/userLogin")
    public String userLogin(Model model) {
        model.addAttribute("loginUser", new RegisterUserForm());
        return "userLogin";
    }

    @GetMapping("/administrateUsers")
    public String administrateUsers() {
        return "welcome";
    }
}
