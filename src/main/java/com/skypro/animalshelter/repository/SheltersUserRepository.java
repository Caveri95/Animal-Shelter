package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.SheltersUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SheltersUserRepository extends JpaRepository<SheltersUser, Long> {

    Optional<SheltersUser> findSheltersUserByChatId(Long chatId);

    List<SheltersUser> findSheltersUserByDataAdoptIsNotNull();
}
