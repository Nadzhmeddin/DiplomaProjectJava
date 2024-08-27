package ru.geekbrains.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.geekbrains.entity.AnimalClass;
import ru.geekbrains.entity.Booking;
import ru.geekbrains.entity.RoomClass;
import ru.geekbrains.exception.AnimalMatchesException;
import ru.geekbrains.exception.NightsException;
import ru.geekbrains.repository.BookingRepository;


import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookingRepository;


    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    public Booking save(Booking booking) throws NightsException, AnimalMatchesException {
        int nights = (int) ChronoUnit.DAYS.between(booking.getCheckInDate(), booking.getCheckOutDate());

        AnimalClass roomAnimalClass = booking.getRoom().getRoomType().getAnimalClass();
        AnimalClass animalClass = booking.getAnimal().getAnimalType().getAnimalClass();

        if(!roomAnimalClass.equals(animalClass)) {
            throw new AnimalMatchesException("Тип животного не совпадает с типом животных для номеров: %s & %s", animalClass, roomAnimalClass);
        }
        if(nights <= 0) {
            throw new NightsException("Количество ночей должно быть больше нуля! Ваше значение: %s", nights);
        }
        double priceForNights = nights * booking.getRoom().getRoomType().getPricePerNight();
        if(booking.getRoom().getRoomType().getRoomClass().equals(RoomClass.LUXURY)) {
            discountPrice(priceForNights, booking);
        }
        if(booking.getRoom().getRoomType().getRoomClass().equals(RoomClass.STANDARD)) {
            discountPrice(priceForNights, booking);
        }
        if(booking.getRoom().getRoomType().getRoomClass().equals(RoomClass.SUPERIOR)) {
            discountPrice(priceForNights, booking);
        }
        return bookingRepository.save(booking);
    }


    public void discountPrice(double price, Booking booking) {
        if(price >= 10000) {
            booking.setTotalPrice(price - price* 0.02);
        }
        else booking.setTotalPrice(price);
    }


    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    public List<Booking> findByRoomId(Long id) {
        return bookingRepository.findByRoomId(id);
    }

    public List<Booking> findByAnimalId(Long id) {
        return bookingRepository.findByAnimalId(id);
    }
}
