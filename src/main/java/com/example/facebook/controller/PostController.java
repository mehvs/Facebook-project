package com.example.facebook.controller;

import com.example.facebook.dto.PostDTO;
import com.example.facebook.entity.User;
import com.example.facebook.service.implementation.PostServiceImpl;
import com.example.facebook.service.implementation.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class PostController {

    private final PostServiceImpl postService;
    private final UserServiceImpl userService;

    @Autowired
    public PostController(PostServiceImpl postService, UserServiceImpl userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-post")
    public ModelAndView createPost(@ModelAttribute PostDTO postDTO, Principal principal){
        User user = (User) userService.loadUserByUsername(principal.getName());
        postService.createPost(postDTO,user);
        return new ModelAndView();
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/add-comment")
    public ModelAndView writeComment (@ModelAttribute PostDTO postDTO, Principal principal) {
        User user = (User) userService.loadUserByUsername(principal.getName());
        postService.addComment(postDTO,user);
        return new ModelAndView();
    }
}
