package com.example.facebook.controller;

import com.example.facebook.dto.PostDTO;
import com.example.facebook.entity.User;
import com.example.facebook.service.implementation.PostServiceImpl;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

public class PostController {

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    @Autowired
    public PostController(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/createPost")
    public ModelAndView createPost(@ModelAttribute PostDTO postDTO, Principal principal){
        User user = userService.getUser(principal.getName());
        return new ModelAndView();
    }
}
