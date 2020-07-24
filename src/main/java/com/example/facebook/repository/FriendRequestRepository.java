package com.example.facebook.repository;

import com.example.facebook.entity.FriendRequest;
import com.example.facebook.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface FriendRequestRepository extends JpaRepository<FriendRequest, Long> {
    FriendRequest findByRequestedAndRequester(User requester, User requested);
    void deleteFriendRequestByRequesterAndRequested(User requester, User requested);
}
