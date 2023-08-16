package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.exception.ShelterUserNotFoundException;
import com.skypro.animalshelter.model.SheltersUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ShelterUserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SheltersUserServiceImpl implements ShelterUserService {

    private final SheltersUserRepository userRepository;

    public SheltersUserServiceImpl(SheltersUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public List<SheltersUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public SheltersUser createUser(SheltersUser sheltersUser) {
        userRepository.save(sheltersUser);
        return sheltersUser;
    }

    @Override
    public SheltersUser findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(ShelterUserNotFoundException::new);
    }

    @Override
    public SheltersUser editUser(SheltersUser sheltersUser) {
        if (userRepository.findById(sheltersUser.getId()).isPresent()) {
            userRepository.save(sheltersUser);
            return sheltersUser;
        } else {
            throw new ShelterUserNotFoundException();
        }
    }

    @Override
    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            throw new ShelterUserNotFoundException();
        }
    }
}
