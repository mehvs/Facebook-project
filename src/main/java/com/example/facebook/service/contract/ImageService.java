package com.example.facebook.service.contract;

import com.example.facebook.entity.Image;
import com.example.facebook.entity.User;

public interface ImageService {
    Image create(User user);
}