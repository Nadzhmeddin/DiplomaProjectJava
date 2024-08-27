package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Hotel;
import ru.geekbrains.responseAnnotation.HotelAPI;
import ru.geekbrains.service.HotelService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/hotels")
@RequiredArgsConstructor
@Tag(name = "Hotels", description = "API для работы с отелями")
public class HotelRestController {

    private final HotelService hotelService;

    @HotelAPI.ErrorFromServerResponse
    @HotelAPI.OKResponse
    @GetMapping
    @Operation(summary = "Get All Hotels", description = "Получение списком всех отелей")
    public ResponseEntity<List<Hotel>> findAll() {
        return ResponseEntity.ok().body(hotelService.findAll());
    }

    @HotelAPI.NotFoundResponse
    @HotelAPI.ErrorFromServerResponse
    @HotelAPI.OKResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get Hotel by Id", description = "Получение отеля по идентификатору")
    public ResponseEntity<Optional<Hotel>> findById(@PathVariable @Parameter(description = "Идентификатор отеля") Long id) {
        Optional<Hotel> foundHotel = hotelService.findById(id);
        if(foundHotel.isPresent()) {
            return ResponseEntity.ok().body(foundHotel);
        }
        return ResponseEntity.notFound().build();
    }

    @HotelAPI.CREATEDResponse
    @HotelAPI.ErrorFromServerResponse
    @PostMapping
    @Operation(summary = "Post Hotel", description = "Сохранение отеля")
    public ResponseEntity<Hotel> save(@RequestBody Hotel hotel) {
        return ResponseEntity.status(HttpStatus.CREATED).body(hotelService.save(hotel));
    }


    @HotelAPI.NotFoundResponse
    @HotelAPI.ErrorFromServerResponse
    @HotelAPI.NoContentResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Hotel by Id", description = "Удаление отеля по идентификатору")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "Идентификатор отеля") Long id) {
        hotelService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
