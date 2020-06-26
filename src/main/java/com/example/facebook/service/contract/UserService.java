package com.example.facebook.service.contract;

import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;

public interface UserService {

    User register(RegisterDTO registerDTO);

}
