package com.skypro.animalshelter.repository;

import com.skypro.animalshelter.model.Animal;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AnimalRepository extends JpaRepository<Animal, Long> {

    List<Animal> findAnimalByTypeAnimal(String type);
}
