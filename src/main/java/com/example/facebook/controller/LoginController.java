package com.example.facebook.controller;
import com.example.facebook.dto.LoginDTO;
import com.example.facebook.exception.InvalidPasswordException;
import com.example.facebook.exception.InvalidUserException;
import com.example.facebook.service.implementation.LoginService;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LoginController {

    private final LoginService loginService;
    private final UserServiceImpl userService;

    @Autowired
    public LoginController(LoginService loginService, UserServiceImpl userService) {
        this.loginService = loginService;
        this.userService = userService;
    }

    @GetMapping("/login")
    public String logon() {
        return "loginForm.html";
    }

    @PostMapping("/login")
    public ModelAndView login(@ModelAttribute LoginDTO loginDTO, ModelAndView modelAndView) throws InvalidPasswordException, InvalidUserException {
        modelAndView.setViewName("somePage.html");
//        userService.creator();
        String loggedUser = loginService.login(loginDTO.getUsername(), loginDTO.getPassword());
        UserDetails user = userService.loadUserByUsername(loginDTO.getUsername());
        userService.loadUserByPass(loginDTO.getPassword());
        modelAndView.addObject("user", loginDTO.getUsername());
        return modelAndView;
    }
}
