package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.model.ShelterUsers;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheltersUserService {

    private final SheltersUserRepository userRepository;

    public SheltersUserService(SheltersUserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public List<ShelterUsers> getAllUsers() {
        return userRepository.findAll();
    }

    public ShelterUsers createUser(ShelterUsers shelterUsers) {
        userRepository.save(shelterUsers);
        return shelterUsers;
    }

    public ShelterUsers editUser(ShelterUsers shelterUsers) {
        if (userRepository.findById(shelterUsers.getId()).isPresent()) {
            userRepository.save(shelterUsers);
            return shelterUsers;
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
