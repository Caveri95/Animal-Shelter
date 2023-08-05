package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheltersUserService {

    private final SheltersUserRepository userRepository;

    public SheltersUserService(SheltersUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<SheltersUser> getAllUsers() {
        return userRepository.findAll();
    }

    public SheltersUser createUser(SheltersUser user) {
        userRepository.save(user);
        return user;
    }
}
