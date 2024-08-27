package ru.geekbrains.rest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.embedded.NettyWebServerFactoryCustomizer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.web.client.RestClient;
import ru.geekbrains.entity.Room;
import ru.geekbrains.repository.RoomRepository;
import ru.geekbrains.service.RoomService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class RoomRestControllerTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomService roomService;

    @LocalServerPort
    private int port;

    private RestClient restClient;
    @Autowired
    private NettyWebServerFactoryCustomizer nettyWebServerFactoryCustomizer;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        roomRepository.deleteAll();
    }

    @Test
    @DisplayName("RestController GET all rooms and return status OK")
    void findAllRooms() {
        Room firstRoom = new Room();
        Room secondRoom = new Room();

        firstRoom = roomRepository.save(firstRoom);
        secondRoom = roomRepository.save(secondRoom);

        List<Room> body = restClient.get()
                .uri("/rooms")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Room>>() {
                });

        assertNotNull(body);
        assertTrue(body.contains(firstRoom));
        assertTrue(body.contains(secondRoom));
        assertEquals(body.get(0), firstRoom);
    }

    @Test
    @DisplayName("RestController GET room by id and return status OK")
    void findByIdIsOk() {

        Room room = new Room();
        Room expectedRoom = roomRepository.save(room);

        ResponseEntity<Room> actual = restClient.get()
                .uri("/rooms/" + expectedRoom.getId())
                .retrieve()
                .toEntity(Room.class);

        Room responseBody = actual.getBody();
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expectedRoom.getId(), responseBody.getId());
    }

    @Test
    @DisplayName("RestController POST room and return status CREATED")
    void saveRoomIsCreated() {
        Room room = new Room();

        ResponseEntity<Room> response = restClient.post()
                .uri("/rooms")
                .body(room)
                .retrieve()
                .toEntity(Room.class);

        Room responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(roomRepository.existsById(responseBody.getId()));
    }

    @Test
    @DisplayName("RestController DELETE room and return status NO CONTENT")
    void deleteById() {

        Room deletedRoom = new Room();
        deletedRoom = roomRepository.save(deletedRoom);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/rooms/" + deletedRoom.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(roomRepository.existsById(deletedRoom.getId()));
    }
}