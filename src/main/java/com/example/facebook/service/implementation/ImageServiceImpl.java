package com.example.facebook.service.implementation;

import com.example.facebook.entity.Image;
import com.example.facebook.entity.User;
import com.example.facebook.repository.ImageRepository;
import com.example.facebook.service.contract.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image create(User user) {
        Image image = new Image();
        image.setUser(user);
        return imageRepository.save(image);
    }
}
