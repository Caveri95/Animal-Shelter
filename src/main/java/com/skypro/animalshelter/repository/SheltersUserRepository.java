package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.ShelterUsers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SheltersUserRepository extends JpaRepository<ShelterUsers, Long> {

    Optional<ShelterUsers> findSheltersUserByChatId(Long chatId);
}
