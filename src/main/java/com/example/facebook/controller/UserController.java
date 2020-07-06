package com.example.facebook.controller;

import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class UserController extends BaseController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String registerUser() {
        return "register.html";
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute RegisterDTO registerDTO){

        try {
            userService.register(registerDTO);
        }
        catch (IllegalArgumentException exception) {
            return new ModelAndView("register-error.html");
        }

        return new ModelAndView("profile.html");
    }
}
