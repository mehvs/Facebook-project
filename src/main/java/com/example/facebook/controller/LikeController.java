package com.example.facebook.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class LikeController extends BaseController{

    @PostMapping("/like")
    public ModelAndView likePost(){
        return send("profile");
    }

}
