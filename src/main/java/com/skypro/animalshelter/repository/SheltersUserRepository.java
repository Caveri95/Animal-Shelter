package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.SheltersUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SheltersUserRepository extends JpaRepository<SheltersUser, Long> {
}
