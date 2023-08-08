package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.Animals;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animals, Long> {

    List<Animals> findAnimalByTypeAnimal(String type);
}
