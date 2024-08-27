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
import ru.geekbrains.entity.Hotel;
import ru.geekbrains.repository.HotelRepository;
import ru.geekbrains.service.HotelService;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class HotelRestControllerTest {

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        hotelRepository.deleteAll();
    }


    @Test
    @DisplayName("RestController GET all hotels and return status OK")
    void findAllHotels() {
        Hotel firstHotel = new Hotel();
        firstHotel.setName("Marriott");
        Hotel secondHotel = new Hotel();
        secondHotel.setName("Radisson");

        firstHotel = hotelRepository.save(firstHotel);
        secondHotel = hotelRepository.save(secondHotel);

        List<Hotel> body = restClient.get()
                .uri("/hotels")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Hotel>>() {
                });

        assertNotNull(body);
        assertTrue(body.contains(firstHotel));
        assertTrue(body.contains(secondHotel));
    }

    @Test
    @DisplayName("RestController GET hotel by id and return status OK")
    void findHotelByIdIsOk() {
        Hotel hotel = new Hotel();
        hotel.setName("Marriott");
        hotel.setStars(5);
        Hotel savedHotel = hotelRepository.save(hotel);

        ResponseEntity<Hotel> actual = restClient.get()
                .uri("/hotels/" + savedHotel.getId())
                .retrieve()
                .toEntity(Hotel.class);

        Hotel responseBody = actual.getBody();
        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(savedHotel.getId(), responseBody.getId());
        assertEquals(savedHotel.getName(), responseBody.getName());
        assertEquals(savedHotel.getStars(), responseBody.getStars());
    }

    @Test
    @DisplayName("RestController POST hotel and return status CREATED")
    void saveHotelIsCreated() {
        Hotel hotel = new Hotel("Marriot", "Belgorod", 5);

        ResponseEntity<Hotel> response = restClient.post()
                .uri("/hotels")
                .body(hotel)
                .retrieve()
                .toEntity(Hotel.class);

        Hotel responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(hotelRepository.existsById(responseBody.getId()));
        assertEquals(responseBody.getName(), hotel.getName());
        assertEquals(responseBody.getStars(), hotel.getStars());
    }

    @Test
    @DisplayName("RestController DELETE booking by id and return status NO CONTENT")
    void deleteByIdIsNoContent() {
        Hotel deletedHotel = new Hotel();
        deletedHotel = hotelRepository.save(deletedHotel);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/hotels/" + deletedHotel.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(hotelRepository.existsById(deletedHotel.getId()));
    }
}