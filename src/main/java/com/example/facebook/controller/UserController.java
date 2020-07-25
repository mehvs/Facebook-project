package com.example.facebook.controller;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.implementation.ImageUploadService;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class UserController extends BaseController {

    private final UserServiceImpl userService;
    private final UserRepository userRepository;
    private final ImageUploadService imageUploadService;

    @Autowired
    public UserController(UserServiceImpl userService, UserRepository userRepository, ImageUploadService imageUploadService) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.imageUploadService = imageUploadService;
    }

    @PreAuthorize("!isAuthenticated()")
    @GetMapping("/")
    public ModelAndView login() {
        return send("loginForm");
    }

//    @PreAuthorize("isAuthenticated()")
//    @GetMapping("/profile")
//    public ModelAndView profile(Principal principal) {
//        return send("profile", "username", principal.getName());
//    }

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

        return send("profile");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/profile")
    public ModelAndView profile() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("profile");
        User user = imageUploadService.findByEmail();
        user.getProfile().getProfileImage().getUrl();
        modelAndView.addObject("profilePicture", user.getProfile().getProfileImage().getUrl());
        modelAndView.addObject("firstName", user.getFirstName());
        modelAndView.addObject("lastName", user.getLastName());
        modelAndView.addObject("birthday", user.getBirthday());
        return modelAndView;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/change-profile-details")
    public ModelAndView changeProfileDetails(@ModelAttribute ChangeProfileDetailsDTO changeProfileDetailsDTO,@AuthenticationPrincipal User user) {

        try {
            userService.changeDetails(changeProfileDetailsDTO, user.getEmail());
        } catch (IllegalArgumentException exception) {
            return new ModelAndView("somePage");
        }

        return new ModelAndView("profile");
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/change-profile-details")
    public ModelAndView changeProfileDetails ( @AuthenticationPrincipal User user) {
        return send("change-profile-details", "user", user);
    }
}
