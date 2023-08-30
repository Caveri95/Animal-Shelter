package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.Report;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ReportRepository extends JpaRepository<Report, Long> {

    Optional<Report> findByLocalDateEquals(LocalDate date);

    List<Report> findBySheltersUserId(Long id);

}
