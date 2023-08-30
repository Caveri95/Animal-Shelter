package com.skypro.animalshelter.service.impl;

import com.skypro.animalshelter.exception.AnimalNotFoundException;
import com.skypro.animalshelter.model.Animal;
import com.skypro.animalshelter.repository.AnimalRepository;
import com.skypro.animalshelter.service.AnimalService;
import org.springframework.stereotype.Service;

import java.util.List;

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
            throw new AnimalNotFoundException();
        }
    }

    @Override
    public Animal findAnimalById(Long id) {
        return animalRepository.findById(id).orElseThrow(AnimalNotFoundException::new);
    }

    @Override
    public boolean deleteAnimalById(Long id) {
        if (animalRepository.findById(id).isPresent()) {
            animalRepository.deleteById(id);
            return true;
        } else {
            throw new AnimalNotFoundException();
        }
    }
}
