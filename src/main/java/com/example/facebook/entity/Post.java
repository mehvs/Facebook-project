package com.example.facebook.entity;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne (targetEntity = User.class,optional = false)
    private User poster;

    @Column(name = "post",nullable = false)
    private String post;

    @Column(name = "post_date", nullable = false)
    private Date postDate;

    @ManyToOne(targetEntity = Post.class, optional = false)
    private Post parent;

    @ManyToMany
    @JoinTable(name = "post_users",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> likes;

    @ManyToMany
    @JoinTable(name = "post_users",
            joinColumns = { @JoinColumn(name = "post_id") },
            inverseJoinColumns = { @JoinColumn(name = "user_id") })
    private Set<User> shares;

    @OneToMany(mappedBy = "parent", fetch = FetchType.LAZY)
    private Set<Post> comments;

    public Post() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getPoster() {
        return poster;
    }

    public void setPoster(User poster) {
        this.poster = poster;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public Post getParent() {
        return parent;
    }

    public void setParent(Post parent) {
        this.parent = parent;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public Set<User> getShares() {
        return shares;
    }

    public void setShares(Set<User> shares) {
        this.shares = shares;
    }

    public Set<Post> getComments() {
        return comments;
    }

    public void setComments(Set<Post> comments) {
        this.comments = comments;
    }
}
