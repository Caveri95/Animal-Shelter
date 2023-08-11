package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SheltersUserService {

    private final SheltersUserRepository userRepository;

    public SheltersUserService(SheltersUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<ShelterUser> getAllUsers() {
        return userRepository.findAll();
    }

    public ShelterUser createUser(ShelterUser shelterUser) {
        userRepository.save(shelterUser);
        return shelterUser;
    }

    public ShelterUser findUserById(Long id) {
        Optional<ShelterUser> user = userRepository.findById(id);
        return user.orElse(null);
    }

    public ShelterUser editUser(ShelterUser shelterUser) {
        if (userRepository.findById(shelterUser.getId()).isPresent()) {
            userRepository.save(shelterUser);
            return shelterUser;
        } else {
            return null;
        }
    }

    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
