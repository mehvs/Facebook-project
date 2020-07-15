package com.example.facebook.service.implementation;

import com.example.facebook.exception.InvalidPasswordException;
import com.example.facebook.exception.InvalidUserException;
import org.springframework.stereotype.Service;

@Service
public class LoginService {
    public String login(String username, String password) throws InvalidUserException, InvalidPasswordException {

        if (username == null || username.isEmpty()) {
            throw new InvalidUserException("Username not provided");
        }

        if (password == null || password.isEmpty()) {
            throw new InvalidPasswordException("Password not provided");
        }

        return username;
    }
}
