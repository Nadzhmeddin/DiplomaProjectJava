package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.exception.AnimalAgeException;
import ru.geekbrains.repository.AnimalRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnimalService {

    private final AnimalRepository animalRepository;

    public List<Animal> findAll() {
        return animalRepository.findAll();
    }

    public Optional<Animal> findById(Long id) {
        return animalRepository.findById(id);
    }

    public Animal save(Animal animal) throws AnimalAgeException {
        if(animal.getAge() <= 0) {
            throw new AnimalAgeException("Возраст животного указан некорректно: %s\n", animal.getAge());
        }
        return animalRepository.save(animal);
    }

    public void deleteById(Long id) {
        animalRepository.deleteById(id);
    }


    public List<Animal> findByOwnerId(Long id) {
        return animalRepository.findByOwnerId(id);
    }

    public List<Animal> findByAnimalTypeId(Long id) {
        return animalRepository.findByAnimalTypeId(id);
    }
}
