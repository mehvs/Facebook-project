package com.example.facebook.service.contract;

import com.example.facebook.entity.User;
import com.example.facebook.exception.InvalidUserException;

public interface FriendRequestService {
    void sendFriendRequest(User requester, Long receiverId) throws InvalidUserException;
    void acceptFriendRequest(Long requesterId, User receiver) throws InvalidUserException;
    void declineFriendRequest(Long requesterId, User requested) throws InvalidUserException;
    void cancelFriendRequest(User requester, Long receiverId) throws InvalidUserException;
    void removeFriend(User authUser, Long userId) throws InvalidUserException;
}
