package com.example.facebook.service.implementation;

import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.UserService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getPasswordRepeat())) {
            throw new IllegalArgumentException("Passwords do not match");
        }

        User user = new User();
        user.setFirstName(registerDTO.getFirstName());
        user.setPassword(registerDTO.getPassword());

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return null;
    }
}

