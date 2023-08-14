package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.ShelterUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SheltersUserRepository extends JpaRepository<ShelterUser, Long> {

    Optional<ShelterUser> findSheltersUserByChatId(Long chatId);
}
