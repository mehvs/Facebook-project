package com.example.facebook.controller;

import com.dropbox.core.DbxException;
import com.example.facebook.dto.ImageUploadDTO;
import com.example.facebook.service.implementation.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class ProfileController extends BaseController{

    private final ImageUploadService imageUploadService;

    @Autowired
    public ProfileController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/profile")
    public ModelAndView setProfilePage() {
        return send("profile");
    }
/*
    @PostMapping("/profile")
    public ModelAndView setProfilePicture(@ModelAttribute ImageUploadDTO imageUploadDTO) throws IOException, DbxException {
       /imageUploadService.uploadImage(imageUploadDTO.getImage());
        imageUploadService.setProfilePicture(imageUploadDTO);
        return redirect("profile");

    }*/

}
