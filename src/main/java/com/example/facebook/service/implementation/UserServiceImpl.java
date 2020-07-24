package com.example.facebook.service.implementation;

import com.example.facebook.dto.ChangeProfileDetailsDTO;
import com.example.facebook.dto.RegisterDTO;
import com.example.facebook.entity.Role;
import com.example.facebook.entity.User;
import com.example.facebook.repository.UserRepository;
import com.example.facebook.service.contract.ImageService;
import com.example.facebook.service.contract.ProfileService;
import com.example.facebook.service.contract.UserService;
import com.mysql.cj.exceptions.PasswordExpiredException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Service
public class UserServiceImpl implements UserService, UserDetailsService {

    private static final String PROFILE_DEFAULT_PICTURE = "https://directory.illinois.edu/webservices/public/ds/profile.png";
    private final RoleServiceImpl roleService;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final ProfileService profileService;
    private final ImageService imageService;

    @Autowired
    public UserServiceImpl(RoleServiceImpl roleService, UserRepository userRepository, BCryptPasswordEncoder passwordEncoder, ProfileService profileService, ImageService imageService) {
        this.roleService = roleService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.profileService = profileService;
        this.imageService = imageService;
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


        user.setActive(true);
        user.setRegisterDate(new Date());
        user.setBirthday(formatBirthday(registerDTO));

        Set<Role> roles = new HashSet<>();
        roles.add(roleService.getUserRole());
        user.setAuthorities(roles);

        userRepository.save(user);

        setDefaultProfilePicture(user);

        userRepository.save(user);


        return user;
    }

    public void setDefaultProfilePicture(User user){

        user.setProfile(profileService.create());
        user.getProfile().setProfileImage(imageService.create(user));
        user.getProfile().getProfileImage().setUrl(PROFILE_DEFAULT_PICTURE);
        user.getProfile().setFullName(user.getFirstName() + " " + user.getLastName());

        userRepository.save(user);

    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = getUser(email);
        return user;
    }

    public User getUser(String email) {
        User user = userRepository.findFirstByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found; with username: " + email));
        return user;
    }

    @Override
    public User changeDetails(ChangeProfileDetailsDTO changeProfileDetailsDTO, String email) {
        User user = userRepository.findFirstByEmail(email).
                orElseThrow(() -> new IllegalArgumentException("User not found; with username: " + email));

        if (changeProfileDetailsDTO.getCurrentPassword() != null) {
            if (passwordEncoder.matches(changeProfileDetailsDTO.getCurrentPassword(), user.getPassword())) {
                throw new IllegalArgumentException("Password is incorrect!");
            } else {
                if (changeProfileDetailsDTO.getNewPassword() != null
                        && !changeProfileDetailsDTO.getNewPassword().equals("")
                        && changeProfileDetailsDTO.getNewPassword().equals(changeProfileDetailsDTO.getNewPasswordRepeat())) {
                    user.setPassword(passwordEncoder.encode(changeProfileDetailsDTO.getNewPassword()));
                }

            }
        }

        user.setFirstName(changeProfileDetailsDTO.getFirstName());
        user.setLastName(changeProfileDetailsDTO.getLastName());
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

    public UserDetails loadUserByPass(String password) throws PasswordExpiredException {
        User user = userRepository.findFirstByPassword(password).orElseThrow(() -> new IllegalArgumentException("Invalid password"));


        return user;
    }

    @Override
    public Optional findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Optional findUserByResetToken(String resetToken) {
        return userRepository.findByResetToken(resetToken);
    }

    @Override
    public void save(User user) {
        userRepository.save(user);
    }

}

