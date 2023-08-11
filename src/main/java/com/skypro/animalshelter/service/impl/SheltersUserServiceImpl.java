package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.model.ShelterUser;
import com.skypro.animalshelter.repository.SheltersUserRepository;
import com.skypro.animalshelter.service.ShelterUserService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SheltersUserServiceImpl implements ShelterUserService {

    private final SheltersUserRepository userRepository;

    public SheltersUserServiceImpl(SheltersUserRepository userRepository) {
        this.userRepository = userRepository;
    }


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
        Optional<ShelterUser> user = userRepository.findById(id);
        return user.orElse(null);
    }
    @Override

    public ShelterUser editUser(ShelterUser shelterUser) {
        if (userRepository.findById(shelterUser.getId()).isPresent()) {
            userRepository.save(shelterUser);
            return shelterUser;
        } else {
            return null;
        }
    }
@Override

    public boolean deleteUserById(Long id) {
        if (userRepository.findById(id).isPresent()) {
            userRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }


}
