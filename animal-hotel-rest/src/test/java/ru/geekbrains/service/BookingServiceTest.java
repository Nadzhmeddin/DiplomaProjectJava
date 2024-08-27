package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.entity.Booking;
import ru.geekbrains.entity.Room;
import ru.geekbrains.exception.AnimalMatchesException;
import ru.geekbrains.exception.NightsException;
import ru.geekbrains.repository.AnimalRepository;
import ru.geekbrains.repository.BookingRepository;
import ru.geekbrains.repository.RoomRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class BookingServiceTest {

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    AnimalRepository animalRepository;

    @Autowired
    BookingService bookingService;

    @BeforeEach
    void clearData(){
        bookingRepository.deleteAll();
    }

    @Test
    @DisplayName("find all bookings method test")
    void findAllTest() {
        Booking firstBooking = new Booking();
        firstBooking.setTotalPrice(1000.0);
        Booking secondBooking = new Booking();
        secondBooking.setTotalPrice(2000.0);
        bookingRepository.save(firstBooking);
        bookingRepository.save(secondBooking);

        List<Booking> all = bookingService.findAll();

        assertThat(all)
                .hasSize(2)
                .isNotNull()
                .contains(firstBooking, secondBooking);
    }


    @Test
    @DisplayName("find by id booking method test")
    void findByIdTest() {
        Booking booking = new Booking();
        booking.setTotalPrice(2000.0);
        booking = bookingRepository.save(booking);

        Booking findedBooking = bookingService.findById(booking.getId()).get();

        assertThat(booking)
                .isEqualTo(findedBooking)
                .isNotNull();

    }

    @Test
    @DisplayName("save booking method test")
    void saveBookingTest() throws NightsException, AnimalMatchesException {
        Booking firstBooking = new Booking();
        firstBooking.setTotalPrice(2000.0);
        Booking secondBooking = new Booking();
        secondBooking.setTotalPrice(1000.0);
        bookingRepository.save(firstBooking);
        bookingRepository.save(secondBooking);

        List<Booking> bookingList = bookingService.findAll();

        assertThat(bookingList)
                .isNotNull()
                .contains(firstBooking, secondBooking)
                .hasSize(2);
    }

    @Test
    @DisplayName("delete by id booking method test")
    void deleteByIdTest() {
        Booking firstBooking = new Booking();
        firstBooking.setTotalPrice(2000.0);
        Booking secondBooking = new Booking();
        secondBooking.setTotalPrice(1000.0);
        bookingRepository.save(firstBooking);
        bookingRepository.save(secondBooking);

        bookingService.deleteById(1L);
        bookingService.deleteById(2L);
        List<Booking> bookingList = bookingService.findAll();

        assertThat(bookingList)
                .hasSize(0)
                .isEmpty();
    }

    @Test
    @DisplayName("find bookings by room id method test")
    void findByRoomIdTest() {
        Booking booking = new Booking();
        booking.setTotalPrice(1000.0);
        Room room = new Room();
        roomRepository.save(room);
        booking.setRoom(room);
        bookingRepository.save(booking);


        List<Booking> bookingList = bookingService.findByRoomId(room.getId());

        assertThat(bookingList)
                .hasSize(1)
                .isNotNull()
                .contains(booking);
    }

    @Test
    @DisplayName("find bookings by animal id method test")
    void findByAnimalIdTest() {
        Booking booking = new Booking();
        booking.setTotalPrice(1000.0);
        Animal animal = new Animal("Donny", 22);
        animalRepository.save(animal);
        booking.setAnimal(animal);
        bookingRepository.save(booking);


        List<Booking> bookingList = bookingService.findByAnimalId(animal.getId());

        assertThat(bookingList)
                .hasSize(1)
                .isNotNull()
                .contains(booking);
    }
}