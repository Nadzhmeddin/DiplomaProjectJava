package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.entity.Hotel;
import ru.geekbrains.repository.HotelRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class HotelServiceTest {

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    HotelService hotelService;

    @BeforeEach
    void clearData(){
        hotelRepository.deleteAll();
    }

    @Test
    @DisplayName("find all hotels method test")
    void findAllHotelsTest() {

        Hotel firstHotel = new Hotel();
        Hotel secondHotel = new Hotel();
        hotelRepository.save(firstHotel);
        hotelRepository.save(secondHotel);

        List<Hotel> hotels = hotelService.findAll();

        assertThat(hotels)
                .hasSize(2)
                .contains(firstHotel, secondHotel)
                .isNotNull();
    }

    @Test
    @DisplayName("find by id hotel method test")
    void findByIdTest() {
        Hotel hotel = new Hotel();
        hotel.setName("Marriott");

        hotel = hotelRepository.save(hotel);

        Hotel foundHotel = hotelService.findById(hotel.getId()).get();

        assertThat(foundHotel)
                .isEqualTo(hotel)
                .isNotNull();
    }

    @Test
    @DisplayName("save hotel method test")
    void saveHotelTest() {
        Hotel hotel = new Hotel();
        hotel.setName("Radisson");
        hotel = hotelService.save(hotel);

        Hotel savedHotel = hotelService.findById(hotel.getId()).get();

        assertThat(savedHotel)
                .isEqualTo(hotel)
                .isNotNull();
    }

    @Test
    @DisplayName("delete hotel method test")
    void deleteByIdTest() {
        Hotel firstHotel = new Hotel();
        Hotel secondHotel = new Hotel();
        hotelRepository.save(firstHotel);
        hotelRepository.save(secondHotel);

        hotelService.deleteById(firstHotel.getId());

        List<Hotel> hotels = hotelService.findAll();

        assertThat(hotels)
                .hasSize(1)
                .contains(secondHotel)
                .isNotNull();
    }
}