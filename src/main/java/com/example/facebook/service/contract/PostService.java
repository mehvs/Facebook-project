package com.example.facebook.service.contract;

import com.example.facebook.dto.PostDTO;
import com.example.facebook.entity.User;

public interface PostService {
    void createPost(PostDTO postDTO, User user);
}
