package com.example.facebook.service.contract;

import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User register(RegisterDTO registerDTO);

}
