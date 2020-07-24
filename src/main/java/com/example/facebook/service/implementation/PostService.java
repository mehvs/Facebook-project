package com.example.facebook.service.implementation;

import com.example.facebook.entity.Post;
import com.example.facebook.entity.User;
import com.example.facebook.repository.PostRepository;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.UserService;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserRepository userRepository, UserService userService) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
        this.userService = userService;
    }


    public Post findPostById(Long id) {
        Post post = postRepository.findFirstById(id).orElseThrow(() -> new IllegalArgumentException("Post id not found" + id));
        ;

        return post;
    }

    public void like() {
        //TODO: Need to set proper id here.
        Post post = findPostById(2L);
        User user = userRepository.findFirstByEmail(userService.getCurrentLoggedUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found" + userService.getCurrentLoggedUsername()));
        ;

        post.getLikes().add(user);
        postRepository.save(post);
    }




}
