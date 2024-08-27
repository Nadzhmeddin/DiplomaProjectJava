package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Room;

import java.util.List;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    @Query("select r from Room r where r.hotel.id = :hotelId order by r.id desc")
    List<Room> findByHotelId(Long hotelId);

    @Query("select r from Room r where r.roomType.id = :roomTypeId order by r.id")
    List<Room> findByRoomTypeId(Long roomTypeId);
}
