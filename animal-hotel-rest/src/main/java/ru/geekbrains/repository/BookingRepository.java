package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Booking;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("select b from Booking b where  b.room.id = :roomId order by b.totalPrice desc")
    List<Booking> findByRoomId(Long roomId);


    @Query("select b from Booking b where b.animal.id = :animalId order by b.totalPrice desc")
    List<Booking> findByAnimalId(Long animalId);

}
