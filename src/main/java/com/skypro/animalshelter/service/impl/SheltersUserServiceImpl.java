package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.exception.ShelterUserNotFoundException;
import com.skypro.animalshelter.model.ShelterUser;
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
    public List<ShelterUser> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public ShelterUser createUser(ShelterUser shelterUser) {
        userRepository.save(shelterUser);
        return shelterUser;
    }

    @Override
    public ShelterUser findUserById(Long id) {
        return userRepository.findById(id).orElseThrow(ShelterUserNotFoundException::new);
    }

    @Override
    public ShelterUser editUser(ShelterUser shelterUser) {
        if (userRepository.findById(shelterUser.getId()).isPresent()) {
            userRepository.save(shelterUser);
            return shelterUser;
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
