package com.example.facebook.entity;

import com.example.facebook.enumeration.FriendRequestStatus;

import javax.persistence.*;

@Entity
@Table(name = "friend_requests")
public class FriendRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated
    private FriendRequestStatus status;

    public FriendRequest() {
        this.id = id;
        this.status = status;
    }

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    private User requester;

    @ManyToOne(targetEntity = User.class, optional = false, fetch = FetchType.LAZY)
    private User requested;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getRequester() {
        return requester;
    }

    public void setRequester(User requester) {
        this.requester = requester;
    }

    public User getRequested() {
        return requested;
    }

    public void setRequested(User requested) {
        this.requested = requested;
    }

    public FriendRequestStatus getStatus() {
        return status;
    }

    public void setStatus(FriendRequestStatus status) {
        this.status = status;
    }
}
