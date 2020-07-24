package com.example.facebook.service.contract;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;

import java.util.Optional;

public interface UserService {

    User register(RegisterDTO registerDTO);

    User changeDetails(ChangeProfileDetailsDTO changeProfileDetailsDTO, String email);

    public Optional<User> findUserByEmail(String email);
    public Optional<User> findUserByResetToken(String resetToken);
    public void save(User user);
    String getCurrentLoggedUsername();
}
