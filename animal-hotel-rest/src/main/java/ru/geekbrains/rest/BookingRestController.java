package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Booking;
import ru.geekbrains.exception.AnimalMatchesException;
import ru.geekbrains.exception.NightsException;
import ru.geekbrains.responseAnnotation.BookingAPI;
import ru.geekbrains.service.BookingService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
@Tag(name = "Bookings", description = "API для работы с бронированием номеров")
public class BookingRestController {

    private final BookingService bookingService;

    @BookingAPI.ErrorFromServerResponse
    @BookingAPI.OKResponse
    @GetMapping
    @Operation(summary = "Get All Bookings", description = "Получение списком всех бронирований")
    public ResponseEntity<List<Booking>> findAll() {
        return ResponseEntity.ok().body(bookingService.findAll());
    }

    @BookingAPI.NotFoundResponse
    @BookingAPI.ErrorFromServerResponse
    @BookingAPI.OKResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get Booking by Id", description = "Получение бронирования по идентификатору")
    public ResponseEntity<Optional<Booking>> findById(@PathVariable @Parameter(description = "Идентификатор бронирования") Long id) {
        Optional<Booking> foundBooking = bookingService.findById(id);
        if(foundBooking.isPresent()) {
            return ResponseEntity.ok().body(foundBooking);
        }
        return ResponseEntity.notFound().build();
    }

    @BookingAPI.CREATEDResponse
    @BookingAPI.ErrorFromServerResponse
    @PostMapping
    @Operation(summary = "Post Booking", description = "Сохранения бронирования")
    public ResponseEntity<Booking> save(@RequestBody Booking booking) {
        ResponseEntity<Booking> savedBooking;
        try {
            savedBooking = ResponseEntity.status(HttpStatus.CREATED).body(bookingService.save(booking));
        } catch (NightsException e) {
            System.out.printf("Количество ночей должно быть больше 0! Ваш ввод: %s\n", e.getNights());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (AnimalMatchesException e) {
            System.out.printf("Тип животного и тип номера для животных не совпадают: %s & %s\n", e.getAnimalClass(), e.getAnimalRoomClass());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return savedBooking;
    }

    @BookingAPI.NotFoundResponse
    @BookingAPI.ErrorFromServerResponse
    @BookingAPI.NoContentResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Booking by Id", description = "Удаление бронирования по идентификатору")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "Идентификатор бронирования") Long id) {
        bookingService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @BookingAPI.OKResponse
    @BookingAPI.NotFoundResponse
    @BookingAPI.ErrorFromServerResponse
    @GetMapping("/rooms/{id}")
    public ResponseEntity<List<Booking>> findByRoomId(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.findByRoomId(id));
    }

    @BookingAPI.OKResponse
    @BookingAPI.NotFoundResponse
    @BookingAPI.ErrorFromServerResponse
    @GetMapping("/animals/{id}")
    public ResponseEntity<List<Booking>> findByAnimalId(@PathVariable Long id) {
        return ResponseEntity.ok().body(bookingService.findByAnimalId(id));
    }
}
