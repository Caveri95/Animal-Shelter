package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.service.AnimalService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimalServiceImpl implements AnimalService {


    private final AnimalRepository animalRepository;

    public AnimalServiceImpl(AnimalRepository animalRepository) {
        this.animalRepository = animalRepository;
    }

    @Override
    public List<Animal> getAllAnimals() {
        return animalRepository.findAll();
    }

    @Override
    public Animal createAnimal(Animal animal) {
        animalRepository.save(animal);
        return animal;
    }

    @Override
    public Animal editAnimal(Animal animal) {
        if (animalRepository.findById(animal.getId()).isPresent()) {
            animalRepository.save(animal);
            return animal;
        } else {
            return null;
        }
    }
@Override
    public Animal findAnimalById(Long id) {
        Optional<Animal> animal = animalRepository.findById(id);
        return animal.orElse(null);
    }

    @Override
    public boolean deleteAnimalById(Long id) {
        if (animalRepository.findById(id).isPresent()) {
            animalRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }
}
