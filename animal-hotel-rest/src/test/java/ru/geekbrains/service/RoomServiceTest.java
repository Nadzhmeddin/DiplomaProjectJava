package ru.geekbrains.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import ru.geekbrains.entity.Hotel;
import ru.geekbrains.entity.Room;
import ru.geekbrains.entity.RoomType;
import ru.geekbrains.repository.HotelRepository;
import ru.geekbrains.repository.RoomRepository;
import ru.geekbrains.repository.RoomTypeRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("test")
@SpringBootTest
class RoomServiceTest {

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    RoomTypeRepository roomTypeRepository;

    @Autowired
    HotelRepository hotelRepository;

    @Autowired
    RoomService roomService;

    @BeforeEach
    void clearData(){
        roomRepository.deleteAll();
    }

    @Test
    @DisplayName("find all rooms method test")
    void findAllRoomsTest() {
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        roomRepository.save(firstRoom);
        roomRepository.save(secondRoom);

        List<Room> roomsList = roomService.findAll();

        assertThat(roomsList)
                .isNotNull()
                .contains(firstRoom, secondRoom)
                .hasSize(2);
    }

    @Test
    @DisplayName("find room by id method test")
    void findRoomByIdTest() {
        Room room = new Room();
        room = roomRepository.save(room);

        Room foundRoom = roomService.findById(room.getId()).get();

        assertThat(foundRoom)
                .isNotNull()
                .isEqualTo(room);
    }

    @Test
    @DisplayName("save room method test")
    void saveRoomTest() {
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        roomService.save(firstRoom);
        roomService.save(secondRoom);

        List<Room> roomsList = roomService.findAll();

        assertThat(roomsList)
                .hasSize(2)
                .isNotNull()
                .contains(firstRoom, secondRoom);
    }

    @Test
    @DisplayName("delete room by id method test")
    void deleteRoomByIdTest() {
        Room firstRoom = new Room();
        Room secondRoom = new Room();
        roomService.save(firstRoom);
        roomService.save(secondRoom);



        roomService.deleteById(firstRoom.getId());

        List<Room> roomList = roomService.findAll();

        assertThat(roomList)
                .contains(secondRoom)
                .isNotNull()
                .hasSize(1);
    }

    @Test
    @DisplayName("find room by hotel id method test")
    void findRoomByHotelIdTest() {
        Hotel hotel = new Hotel();
        hotel = hotelRepository.save(hotel);
        Room room = new Room();
        room.setHotel(hotel);
        roomService.save(room);

        List<Room> foundRooms = roomService.findByHotelId(hotel.getId());

        assertThat(foundRooms)
                .hasSize(1)
                .contains(room)
                .isNotNull();
    }

    @Test
    @DisplayName("find room by room type id method test")
    void findByRoomTypeIdTest() {
        RoomType roomType = new RoomType();
        roomType = roomTypeRepository.save(roomType);
        Room room = new Room();
        room.setRoomType(roomType);
        room = roomService.save(room);

        List<Room> foundRooms = roomService.findByRoomTypeId(roomType.getId());

        assertThat(foundRooms)
                .isNotNull()
                .contains(room)
                .hasSize(1);

    }
}