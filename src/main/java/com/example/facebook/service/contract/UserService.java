package com.example.facebook.service.contract;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;
import com.example.facebook.exception.InvalidUserException;

public interface UserService {

    User register(RegisterDTO registerDTO);

    User changeDetails(ChangeProfileDetailsDTO changeProfileDetailsDTO, String email);

    User getUser(String username) throws InvalidUserException;

}
