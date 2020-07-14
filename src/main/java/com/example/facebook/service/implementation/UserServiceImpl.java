package com.example.facebook.service.implementation;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import sun.plugin.dom.html.HTMLBodyElement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public User register(RegisterDTO registerDTO) {
        if (!registerDTO.getPassword().equals(registerDTO.getPasswordRepeat())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }

        User user = new User();
        if (registerDTO.getAge() < 14) {
            throw new IllegalArgumentException("You are too young!");
        }
        user.setFirstName(registerDTO.getFirstName());
        user.setLastName(registerDTO.getLastName());
        user.setAge(registerDTO.getAge());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
        user.setEmail(registerDTO.getEmail());
        user.setActive(false);
        user.setRegisterDate(new Date());
        user.setBirthday(formatBirthday(registerDTO));

        userRepository.save(user);
        return user;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findFirstByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found; with username: " + email));

        return user;
    }

    @Override
    public User changeDetails(ChangeProfileDetailsDTO changeProfileDetailsDTO, String email) {
        User user = userRepository.findFirstByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found; with username: " + email));


        if (!changeProfileDetailsDTO.getCurrentPassword().equals(user.getPassword() )) {
            throw new IllegalArgumentException("Password is incorrect!");
        }else{
            if (changeProfileDetailsDTO.getNewPassword() != null
                    && !changeProfileDetailsDTO.getNewPassword().equals("")
                    && changeProfileDetailsDTO.getNewPassword().equals(changeProfileDetailsDTO.getNewPasswordRepeat())) {
                user.setPassword(passwordEncoder.encode(changeProfileDetailsDTO.getNewPassword()));
            }

        }

        user.setFirstName(changeProfileDetailsDTO.getFirstName());
        user.setLastName(changeProfileDetailsDTO.getLastName());
        user.setAge(changeProfileDetailsDTO.getAge());
        user.setPassword(passwordEncoder.encode(changeProfileDetailsDTO.getNewPassword()));
        user.setEmail(changeProfileDetailsDTO.getEmail());

        userRepository.save(user);
        return user;
    }

    public Date formatBirthday(RegisterDTO registerDTO) {
        SimpleDateFormat format = new SimpleDateFormat("dd.MM.yyyy");
        String birthday = registerDTO.getBirthday();
        try {
            return format.parse(birthday);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

}

