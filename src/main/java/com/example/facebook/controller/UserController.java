package com.example.facebook.controller;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.service.contract.UserService;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController extends BaseController {

    private final UserServiceImpl userService;

    @Autowired
    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/")
    public ModelAndView login() {
        return send("loginForm");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView profile(Principal principal, ModelAndView modelAndView) {
        modelAndView.setViewName("profile.html");
        modelAndView.addObject("user", userService.getUser(principal.getName()));
        return modelAndView;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/register")
    public ModelAndView register(@ModelAttribute("user") RegisterDTO registerDTO) {
        return send("register");
    }

    @PreAuthorize("!isAuthenticated()")
    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") RegisterDTO registerDTO) {

        try {
            userService.register(registerDTO);
        } catch (IllegalArgumentException exception) {
            return new ModelAndView("errors/register-error");
        }

        return new ModelAndView("profile");
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-profile-details")
    public ModelAndView changeProfileDetails(@ModelAttribute ChangeProfileDetailsDTO changeProfileDetailsDTO, Principal principal) {

        try {
            userService.changeDetails(changeProfileDetailsDTO, principal.getName());
        } catch (IllegalArgumentException exception) {
            return new ModelAndView("");
        }

        return new ModelAndView("profile");
    }
}
