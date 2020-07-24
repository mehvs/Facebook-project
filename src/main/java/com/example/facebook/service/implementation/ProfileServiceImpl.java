package com.example.facebook.service.implementation;

import com.example.facebook.entity.Profile;
import com.example.facebook.repository.ProfileRepository;
import com.example.facebook.service.contract.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepository profileRepository;

    @Autowired
    public ProfileServiceImpl(ProfileRepository profileRepository) {
        this.profileRepository = profileRepository;
    }

    @Override
    public Profile create() {
        Profile profile = new Profile();

        return profileRepository.save(profile);
    }
}
