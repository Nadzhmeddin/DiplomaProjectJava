package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.entity.AnimalClass;
import ru.geekbrains.entity.AnimalType;
import ru.geekbrains.entity.Owner;
import ru.geekbrains.repository.AnimalRepository;
import ru.geekbrains.repository.AnimalTypeRepository;
import ru.geekbrains.repository.OwnerRepository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
class AnimalServiceTest {

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    AnimalTypeRepository animalTypeRepository;

    @Autowired
    AnimalService animalService;

    @BeforeEach
    void clearData(){
        animalRepository.deleteAll();
    }

    @Test
    @DisplayName("search for non-existent animal by id test")
    void testFindByIdIsEmptyTest() {
        assertFalse(animalRepository.existsById(1L));

        assertTrue(animalService.findById(1L).isEmpty());
    }

    @Test
    @DisplayName("search existing animal by id method test")
    void testFindByIdIsPresentTest() {
        Animal animal = new Animal();
        animal.setName("animalName");
        animal = animalRepository.save(animal);

        Optional<Animal> actual = animalService.findById(animal.getId());

        assertTrue(actual.isPresent());
        assertEquals(animal.getId(), actual.get().getId());
        assertEquals(animal.getName(), actual.get().getName());
    }

    @Test
    @DisplayName("find all animals in data method test")
    void findAllTest() {
        Animal firstAnimal = new Animal();
        firstAnimal.setName("Sera");
        Animal secondAnimal = new Animal();
        secondAnimal.setName("Lu");
        animalRepository.save(firstAnimal);
        animalRepository.save(secondAnimal);

        List<Animal> savedAnimals = animalService.findAll();

        assertTrue(savedAnimals.contains(firstAnimal));
        assertTrue(savedAnimals.contains(secondAnimal));
        assertEquals(firstAnimal.getName(), savedAnimals.get(0).getName());
        assertEquals(2, savedAnimals.size());
    }

    @Test
    @DisplayName("save animal method test")
    void saveTest() {
        Animal animal = new Animal();
        animal.setName("Donny");
        animal.setAge(11);
        animalRepository.save(animal);

        Animal foundAnimal = animalService.findById(animal.getId()).get();

        assertEquals(11, foundAnimal.getAge());
    }


    @Test
    @DisplayName("delete by id animal method test")
    void deleteByIdTest() {
        Animal deletedAnimal = new Animal();
        deletedAnimal.setName("Kevin");
        deletedAnimal.setAge(11);
        animalRepository.save(deletedAnimal);

        animalService.deleteById(deletedAnimal.getId());

        assertTrue(animalRepository.findAll().isEmpty());
        assertEquals(0, animalRepository.findAll().size());
    }

    @Test
    @DisplayName("find animal by owner id method test")
    void findByOwnerIdTest() {

        Animal animal = new Animal();
        Owner kate = new Owner();
        kate.setName("Kate");
        ownerRepository.save(kate);
        animal.setOwner(kate);
        animalRepository.save(animal);

        List<Animal> animalsList = animalService.findByOwnerId(kate.getId());

        assertThat(animalsList)
                .contains(animal)
                .hasSize(1)
                .contains(animal)
                .isNotNull();
    }

    @Test
    @DisplayName("find animal by animal type id method test")
    void findByAnimalTypeIdTest() {
        Animal animal = new Animal();
        AnimalType animalType = new AnimalType();
        animalType.setAnimalClass(AnimalClass.DOG);
        animalTypeRepository.save(animalType);
        animal.setAnimalType(animalType);
        animalRepository.save(animal);

        List<Animal> animalsList = animalService.findByAnimalTypeId(animalType.getId());

        assertThat(animalsList)
                .hasSize(1)
                .isNotNull()
                .contains(animal)
                .isNotNull();
    }
}