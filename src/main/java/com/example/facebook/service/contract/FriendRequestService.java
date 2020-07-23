package com.example.facebook.service.contract;

import com.example.facebook.entity.User;
import com.example.facebook.exception.InvalidUserException;

public interface FriendRequestService {
    void sendFriendRequest(User requester, Long receiverId) throws InvalidUserException;
}
