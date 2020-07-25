package com.example.facebook.controller;

import com.example.facebook.dto.UserDTO;
import com.example.facebook.exception.InvalidUserException;
import com.example.facebook.service.contract.FriendRequestService;
import com.example.facebook.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
public class FriendRequestController extends BaseController{

    private final FriendRequestService friendRequestService;
    private final UserService userService;

    @Autowired
    public FriendRequestController(FriendRequestService friendRequestService, UserService userService) {
        this.friendRequestService = friendRequestService;
        this.userService = userService;
    }

    @PostMapping("/removeFriend")
    public ModelAndView removeFriend(@ModelAttribute UserDTO userDTO, Principal principal) throws InvalidUserException{
        friendRequestService.removeFriend(userService.getUser(principal.getName()),userDTO.getUserId());
        return redirect("/profile"+userDTO.getUserId());
    }

    @PostMapping("/cancelRequest")
    public ModelAndView cancelRequest(@ModelAttribute UserDTO userDTO, Principal principal) throws InvalidUserException{
        friendRequestService.cancelFriendRequest(userService.getUser(principal.getName()),userDTO.getUserId());
        return redirect("/profile" + userDTO.getUserId());
    }

    @PostMapping("/denyRequest")
    public ModelAndView declineFriendRequest(@ModelAttribute UserDTO userDTO, Principal principal) throws InvalidUserException{
        friendRequestService.declineFriendRequest(userDTO.getUserId(),userService.getUser(principal.getName()));
        return redirect("/profile"+userDTO);
    }


    @PostMapping("/sendFriendRequest")
    public ModelAndView sendFriendRequest(@ModelAttribute UserDTO userDTO, Principal principal) throws InvalidUserException {
        friendRequestService.sendFriendRequest(userService.getUser(principal.getName()),userDTO.getUserId());
        return redirect("/profile"+userDTO.getUserId());
    }

    @PostMapping("/acceptRequest")
    public ModelAndView acceptFriendRequest(@ModelAttribute UserDTO userIdDTO, Principal principal) throws InvalidUserException{
        friendRequestService.acceptFriendRequest(userIdDTO.getUserId(),userService.getUser(principal.getName()));
        return redirect("/profile" +userIdDTO.getUserId());
    }


}
