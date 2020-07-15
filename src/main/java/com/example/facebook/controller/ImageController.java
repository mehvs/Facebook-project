package com.example.facebook.controller;

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
public class ImageController extends BaseController {

    private final ImageUploadService imageUploadService;

    @Autowired
    public ImageController(ImageUploadService imageUploadService) {
        this.imageUploadService = imageUploadService;
    }

    @GetMapping("/upload")
    public ModelAndView imageUpload() {
        return send("imageUpload");
    }


    @PostMapping("/upload")
    public ModelAndView imageUpload(@ModelAttribute ImageUploadDTO imageUploadDTO) throws IOException {
        imageUploadService.uploadImage(imageUploadDTO.getImage());
        return redirect("/");
    }
}
