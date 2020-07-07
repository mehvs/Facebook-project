package com.example.facebook.service.contract;

import com.example.facebook.exception.InvalidPasswordException;
import com.example.facebook.exception.InvalidUserException;

public interface LoginService {
    String login(String username, String password) throws InvalidUserException, InvalidPasswordException;
}
