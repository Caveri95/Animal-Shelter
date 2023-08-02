package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.User;
import liquibase.pro.packaged.L;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
