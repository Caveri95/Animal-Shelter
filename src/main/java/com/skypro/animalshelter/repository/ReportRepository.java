package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.Reports;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Reports, Long> {

    Optional<Reports> findByShelterUsersId(Long userId);

    Optional<Reports> findByLocalDateEquals(LocalDate date);

}
