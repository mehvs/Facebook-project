package com.example.facebook.service.implementation;

import com.example.facebook.dto.PostDTO;
import com.example.facebook.entity.Post;
import com.example.facebook.entity.User;
import com.example.facebook.repository.PostRepository;
import com.example.facebook.service.contract.PostService;
import org.springframework.beans.factory.annotation.Autowired;

public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public void createPost(PostDTO postDTO) {
        Post newPost = new Post();
        newPost.setPost(postDTO.getPost());
    }
}
