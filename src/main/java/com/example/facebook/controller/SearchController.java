package com.example.facebook.controller;

import com.example.facebook.entity.User;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class SearchController {
    private final UserServiceImpl userService;

    @Autowired
    public SearchController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/search")
    public String profile (){
        return "search.html";
    }

    @PostMapping("/search")
    public ModelAndView profile (@RequestParam("search") String search, ModelAndView modelAndView){
        List<User> users = userService.findUsersByName(search);
        modelAndView.setViewName("SearchedUsers.html");
        modelAndView.addObject("list", users);
        return modelAndView;
    }
}
