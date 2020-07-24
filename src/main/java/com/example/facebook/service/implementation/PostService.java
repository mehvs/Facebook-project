package com.example.facebook.service.implementation;

import com.example.facebook.entity.Post;
import com.example.facebook.entity.User;
import com.example.facebook.repository.PostRepository;
import com.example.facebook.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class PostService {
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public PostService(PostRepository postRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }
    
   public Post findPostById(Long id) {
        Post post = postRepository.findFirstById(id).orElseThrow(() -> new IllegalArgumentException("Post id not found" + id));

        return post;
    }

   public void like() {
        //TODO: Need to set proper id here.
        Post post = findPostById(2L);
        User user = userRepository.findFirstByEmail(getCurrentLoggedUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not found" + getCurrentLoggedUsername()));
        ;



        post.getLikes().add(user);
        postRepository.save(post);
    }

    public String getCurrentLoggedUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username = "";

        if (principal instanceof UserDetails) {

            username = ((UserDetails) principal).getUsername();

        } else {

            username = principal.toString();

        }

        return username;
    }


}
