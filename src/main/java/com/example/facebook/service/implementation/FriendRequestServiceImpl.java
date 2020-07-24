package com.example.facebook.service.implementation;

import com.example.facebook.entity.FriendRequest;
import com.example.facebook.entity.User;
import com.example.facebook.exception.InvalidUserException;
import com.example.facebook.repository.FriendRequestRepository;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.FriendRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FriendRequestServiceImpl implements FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final UserRepository userRepository;

    @Autowired
    public FriendRequestServiceImpl(FriendRequestRepository friendRequestRepository, UserRepository userRepository) {
        this.friendRequestRepository = friendRequestRepository;
        this.userRepository = userRepository;
    }

    private User findUser(Long id) throws InvalidUserException {
        return userRepository.findById(id).orElseThrow(() -> new InvalidUserException("User not found"));
    }

    @Override
    public void acceptFriendRequest(Long requesterId, User receiver) throws InvalidUserException{
        User requester = findUser(requesterId);
        accept(requester,receiver);
    }

    private void accept(User requester, User requested) throws InvalidUserException{
        if (friendRequestRepository.findByRequestedAndRequester(requester, requested) == null) {
            throw new InvalidUserException("Something went wrong :/" + "Friend request is not found");
        }

        friendRequestRepository.deleteFriendRequestByRequesterAndRequested(requester, requested);
       /* requester.getUserFriends().add(requested);
        requested.getUserFriends().add(requester);*/
        userRepository.save(requester);
        userRepository.save(requested);
    }

    @Override
    public void sendFriendRequest(User requester, Long receiverId) throws InvalidUserException {
        FriendRequest friendRequest = new FriendRequest();
        User receiver = findUser(receiverId);
        friendRequest.setRequester(requester);
        friendRequest.setRequested(receiver);
        friendRequestRepository.save(friendRequest);
    }
}
