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
import ru.geekbrains.entity.Owner;
import ru.geekbrains.repository.OwnerRepository;
import ru.geekbrains.service.OwnerService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class OwnerRestControllerTest {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    OwnerService ownerService;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        ownerRepository.deleteAll();
    }

    @Test
    @DisplayName("RestController GET all owners and return status OK")
    void findAllOwners() {
        Owner firstOwner = new Owner("Kate", "79202020676", "kate@mail.ru");
        Owner secondOwner = new Owner("Tony", "79040913580", "tony@mail.ru");

        firstOwner = ownerRepository.save(firstOwner);
        secondOwner = ownerRepository.save(secondOwner);

        List<Owner> body = restClient.get()
                .uri("/owners")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Owner>>() {
                });

        assertNotNull(body);
        assertTrue(body.contains(firstOwner));
        assertTrue(body.contains(secondOwner));
        assertEquals(body.get(0), firstOwner);

    }

    @Test
    @DisplayName("RestController GET owner by id and return status OK")
    void findByIdIsOk() {
        Owner owner = new Owner("Kate", "79202020676", "kate@mail.ru");

        Owner expectedOwner = ownerRepository.save(owner);

        ResponseEntity<Owner> actual = restClient.get()
                .uri("/owners/" + expectedOwner.getId())
                .retrieve()
                .toEntity(Owner.class);

        Owner responseBody = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expectedOwner.getId(), responseBody.getId());
        assertEquals(expectedOwner.getEmail(), responseBody.getEmail());
        assertEquals(expectedOwner.getPhone(), responseBody.getPhone());
    }

    @Test
    @DisplayName("RestController POST owner and return status CREATED")
    void saveOwnerIsCreated() {
        Owner owner = new Owner("Kate", "79202020676", "kate@mail.ru");

        ResponseEntity<Owner> response = restClient.post()
                .uri("/owners")
                .body(owner)
                .retrieve()
                .toEntity(Owner.class);

        Owner responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseBody.getPhone(), owner.getPhone());
        assertTrue(ownerRepository.existsById(responseBody.getId()));
    }

    @Test
    @DisplayName("RestController DELETE owner by id and return status NO CONTENT")
    void deleteOwnerById() {
        Owner deletedOwner = new Owner();
        deletedOwner = ownerRepository.save(deletedOwner);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/owners/" + deletedOwner.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(ownerRepository.existsById(deletedOwner.getId()));


    }
}