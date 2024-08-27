package ru.geekbrains.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.repository.AnimalRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AnimalRestControllerTest {

    @Autowired
    AnimalRepository animalRepository;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        animalRepository.deleteAll();
    }

    @Test
    @DisplayName("RestController GET animal by id and return status OK")
    void findByIdIsOk() {
        Animal animal = new Animal("Sera", 3);
        Animal expectedAnimal = animalRepository.save(animal);

        ResponseEntity<Animal> actual = restClient.get()
                .uri("/animals/" + expectedAnimal.getId())
                .retrieve()
                .toEntity(Animal.class);
        Animal responseBody = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expectedAnimal.getId(), responseBody.getId());
        assertEquals(expectedAnimal.getName(), responseBody.getName());
        assertEquals(expectedAnimal.getAge(), responseBody.getAge());
    }

    @Test
    @DisplayName("RestController POST animal and return status CREATED")
    void saveAnimalIsCreated() {
        Animal createdAnimal  = new Animal("Sera", 3);

        ResponseEntity<Animal> response = restClient.post()
                .uri("/animals")
                .body(createdAnimal)
                .retrieve()
                .toEntity(Animal.class);

        Animal responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(responseBody);
        assertEquals(responseBody.getAge(), createdAnimal.getAge());
        assertEquals(responseBody.getName(), createdAnimal.getName());
        assertTrue(animalRepository.existsById(responseBody.getId()));
    }

    @Test
    @DisplayName("RestController DELETE animal and return status NO CONTENT")
    void deleteAnimalByIdTest() {
        Animal deletedAnimal = new Animal("Sera", 3);
        deletedAnimal = animalRepository.save(deletedAnimal);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/animals/" + deletedAnimal.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(animalRepository.existsById(deletedAnimal.getId()));
    }

    @Test
    @DisplayName("RestController GET all animas and return status OK")
    void findAllAnimalsTest() {
        Animal firstAnimal = new Animal("Sera", 3);
        Animal secondAnimal = new Animal("Donny", 5);

        firstAnimal = animalRepository.save(firstAnimal);
        secondAnimal = animalRepository.save(secondAnimal);

        List<Animal> body = restClient.get()
                .uri("/animals")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Animal>>() {
                });

        assertNotNull(body);
        assertTrue(body.contains(firstAnimal));
        assertTrue(body.contains(secondAnimal));
        assertEquals(body.get(0), firstAnimal);
    }
}