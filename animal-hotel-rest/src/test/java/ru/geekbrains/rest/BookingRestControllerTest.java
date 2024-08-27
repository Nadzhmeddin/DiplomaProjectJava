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
import ru.geekbrains.entity.*;
import ru.geekbrains.repository.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BookingRestControllerTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    AnimalTypeRepository animalTypeRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @LocalServerPort
    private int port;

    private RestClient restClient;

    @BeforeEach
    void beforeEach() {
        restClient = RestClient.create("http://localhost:" + port);
        bookingRepository.deleteAll();
    }

    @Test
    @DisplayName("RestController GET all bookings and return status OK")
    void findAllBookings() {
        Booking firstBooking = new Booking();
        firstBooking.setTotalPrice(1000.0);

        Booking secondBooking = new Booking();
        secondBooking.setTotalPrice(2000.0);

        firstBooking = bookingRepository.save(firstBooking);
        secondBooking = bookingRepository.save(secondBooking);

        List<Booking> body = restClient.get()
                .uri("/bookings")
                .retrieve()
                .body(new ParameterizedTypeReference<List<Booking>>() {
                });

        assertNotNull(body);
        assertTrue(body.contains(firstBooking));
        assertTrue(body.contains(secondBooking));
        assertEquals(body.get(0), firstBooking);
    }


    @Test
    @DisplayName("RestController GET booking by id and return status OK")
    void findByIdIsOk() {
        Booking booking = new Booking();
        booking.setTotalPrice(1000.0);
        Booking expectedBooking = bookingRepository.save(booking);

        ResponseEntity<Booking> actual = restClient.get()
                .uri("/bookings/" + expectedBooking.getId())
                .retrieve()
                .toEntity(Booking.class);

        Booking responseBody = actual.getBody();

        assertEquals(HttpStatus.OK, actual.getStatusCode());
        assertEquals(expectedBooking.getId(), responseBody.getId());
        assertEquals(expectedBooking.getTotalPrice(), responseBody.getTotalPrice());
    }

    @Test
    @DisplayName("RestController POST booking and return status CREATED")
    void saveBookingIsCreated() {
        Booking booking = new Booking();
        booking.setTotalPrice(1000.0);
        booking.setCheckInDate(LocalDate.of(2024, 9, 1));
        booking.setCheckOutDate(LocalDate.of(2024, 9, 10));

        RoomType roomType = new RoomType();
        roomType.setRoomClass(RoomClass.LUXURY);
        roomType.setAnimalClass(AnimalClass.DOG);
        roomType.setPricePerNight(1000);
        roomTypeRepository.save(roomType);

        Room room = new Room();
        room.setRoomType(roomType);
        roomRepository.save(room);
        booking.setRoom(room);

        AnimalType animalType = new AnimalType();
        animalType.setAnimalClass(AnimalClass.DOG);
        animalTypeRepository.save(animalType);

        Animal animal = new Animal("Sera", 3);
        animal.setAnimalType(animalType);
        animalRepository.save(animal);
        booking.setAnimal(animal);

        ResponseEntity<Booking> response = restClient.post()
                .uri("/bookings")
                .body(booking)
                .retrieve()
                .toEntity(Booking.class);

        Booking responseBody = response.getBody();

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(responseBody.getTotalPrice(), 9000);
        assertTrue(bookingRepository.existsById(responseBody.getId()));
    }

    @Test
    @DisplayName("RestController DELETE booking and return status NO CONTENT")
    void deleteBookingById() {
        Booking deleteedBooking = new Booking();
        deleteedBooking = bookingRepository.save(deleteedBooking);

        ResponseEntity<Void> response = restClient.delete()
                .uri("/bookings/" + deleteedBooking.getId())
                .retrieve()
                .toBodilessEntity();

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(animalRepository.existsById(deleteedBooking.getId()));
    }
}