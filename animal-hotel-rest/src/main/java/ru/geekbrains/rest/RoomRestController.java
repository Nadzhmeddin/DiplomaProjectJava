package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Room;
import ru.geekbrains.responseAnnotation.OwnerAPI;
import ru.geekbrains.responseAnnotation.RoomAPI;
import ru.geekbrains.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
@RequiredArgsConstructor
@Tag(name = "Rooms", description = "API для работы с номерами отеля")
public class RoomRestController {

    private final RoomService roomService;

    @RoomAPI.ErrorFromServerResponse
    @RoomAPI.OKResponse
    @GetMapping
    @Operation(summary = "Get All Rooms", description = "Получение списком всех номеров отеля")
    public ResponseEntity<List<Room>> findAll() {
        return ResponseEntity.ok().body(roomService.findAll());
    }


    @RoomAPI.NotFoundResponse
    @RoomAPI.ErrorFromServerResponse
    @RoomAPI.OKResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get Room by Id", description = "Получение номера отеля по идентификатору")
    public ResponseEntity<Optional<Room>> findById(@PathVariable @Parameter(description = "Идентификатор номера отеля") Long id) {
        Optional<Room> foundRoom = roomService.findById(id);
        if(foundRoom.isPresent()) {
            return ResponseEntity.ok().body(foundRoom);
        }
        return ResponseEntity.notFound().build();
    }

    @RoomAPI.CREATEDResponse
    @RoomAPI.ErrorFromServerResponse
    @PostMapping
    @Operation(summary = "Post Room", description = "Сохранение номера отеля")
    public ResponseEntity<Room> save(@RequestBody Room room) {
        return ResponseEntity.status(HttpStatus.CREATED).body(roomService.save(room));
    }

    @RoomAPI.NotFoundResponse
    @RoomAPI.ErrorFromServerResponse
    @RoomAPI.NoContentResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Room by Id", description = "Удаление номера отеля по идентификатору")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "Идентификатор номера отеля") Long id) {
        roomService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @RoomAPI.OKResponse
    @RoomAPI.NotFoundResponse
    @GetMapping("/hotels/{id}")
    public ResponseEntity<List<Room>> findByHotelId(@PathVariable Long id) {
        return ResponseEntity.ok().body(roomService.findByHotelId(id));
    }

    @RoomAPI.OKResponse
    @RoomAPI.NotFoundResponse
    @GetMapping("/rooms-type/{id}")
    public ResponseEntity<List<Room>> findByRoomTypeId(@PathVariable Long id) {
        return ResponseEntity.ok().body(roomService.findByRoomTypeId(id));
    }


}
