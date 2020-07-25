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
    public void declineFriendRequest(Long requesterId, User requested) throws InvalidUserException{
        User requester = findUser(requesterId);
        friendRequestRepository.deleteFriendRequestByRequesterAndRequested(requester, requested);
    }

    @Override
    public void removeFriend(User user, Long userId) throws InvalidUserException{
        User removedUser = findUser(userId);
        removedUser.getFriends().remove(user);
        user.getFriends().remove(removedUser);
        userRepository.save(user);
        userRepository.save(removedUser);
    }

    @Override
    public void cancelFriendRequest(User requester, Long requestedId) throws InvalidUserException{
        User requested = findUser(requestedId);
        friendRequestRepository.deleteFriendRequestByRequesterAndRequested(requester, requested);
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
        requester.getFriends().add(requested);
        requested.getFriends().add(requester);
        userRepository.save(requester);
        userRepository.save(requested);
    }

    @Override
    public void sendFriendRequest(User requester, Long requestedId) throws InvalidUserException {
        FriendRequest friendRequest = new FriendRequest();
        User receiver = findUser(requestedId);
        friendRequest.setRequester(requester);
        friendRequest.setRequested(receiver);
        friendRequestRepository.save(friendRequest);
    }
}
