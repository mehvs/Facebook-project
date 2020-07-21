package com.example.facebook.dto;

import com.example.facebook.entity.Post;
import com.example.facebook.entity.User;

import java.util.Date;

public class PostDTO {
    private String post;
    private Post parent;

    public PostDTO() {
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Post getParent() {
        return parent;
    }

    public void setParent(Post parent) {
        this.parent = parent;
    }
}
