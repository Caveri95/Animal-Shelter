package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.ShelterUser;

public interface ShelterUserService {
    ShelterUser createUser(ShelterUser shelterUser);

    ShelterUser findUserById(Long id);

    ShelterUser editUser(ShelterUser shelterUser);

    boolean deleteUserById(Long id);
}
