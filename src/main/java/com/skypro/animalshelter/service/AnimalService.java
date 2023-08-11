package com.skypro.animalshelter.service;

import com.skypro.animalshelter.model.Animal;

import java.util.List;

public interface AnimalService {
    List<Animal> getAllAnimals();

    Animal createAnimal(Animal animal);

    Animal editAnimal(Animal animal);

    boolean deleteAnimalById(Long id);
}
