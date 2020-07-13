package com.example.facebook.controller;

import com.example.facebook.dto.PostDTO;
import com.example.facebook.service.contract.PostService;
import com.example.facebook.service.implementation.PostServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

public class PostController {

    private final PostServiceImpl postService;

    @Autowired
    public PostController(PostServiceImpl postService) {
        this.postService = postService;
    }

    @PostMapping("/createComment")
    public ModelAndView createPost(@ModelAttribute PostDTO postDTO){
        return new ModelAndView();
    }
}
