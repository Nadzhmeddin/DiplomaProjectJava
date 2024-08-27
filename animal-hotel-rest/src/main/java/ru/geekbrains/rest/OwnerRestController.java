package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.entity.Owner;
import ru.geekbrains.exception.EmailException;
import ru.geekbrains.exception.PhoneException;
import ru.geekbrains.responseAnnotation.HotelAPI;
import ru.geekbrains.responseAnnotation.OwnerAPI;
import ru.geekbrains.service.OwnerService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/owners")
@RequiredArgsConstructor
@Tag(name = "Owners", description = "API для работы с владельцами животных")
public class OwnerRestController {

    private final OwnerService ownerService;

    @OwnerAPI.ErrorFromServerResponse
    @OwnerAPI.OKResponse
    @GetMapping
    @Operation(summary = "Get All Owners", description = "Получение списком всех владельцев животных")
    public ResponseEntity<List<Owner>> findAll() {
        return ResponseEntity.ok().body(ownerService.findAll());
    }


    @OwnerAPI.NotFoundResponse
    @OwnerAPI.ErrorFromServerResponse
    @OwnerAPI.OKResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get Owner by Id", description = "Получение владельца животного по идентификатору")
    public ResponseEntity<Optional<Owner>> findById(@PathVariable @Parameter(description = "Идентификатор владельца животного") Long id) {
        Optional<Owner> foundOwner = ownerService.findById(id);
        if(foundOwner.isPresent()) {
            return ResponseEntity.ok().body(foundOwner);
        }
        return ResponseEntity.notFound().build();
    }

    @OwnerAPI.CREATEDResponse
    @OwnerAPI.ErrorFromServerResponse
    @PostMapping
    @Operation(summary = "Post Owner", description = "Сохранение владельца животного")
    public ResponseEntity<Owner> save(@RequestBody Owner owner){
        ResponseEntity<Owner> savedOwner = null;
        try {
            savedOwner = ResponseEntity.status(HttpStatus.CREATED).body(ownerService.save(owner));
        } catch (PhoneException e) {
            System.out.printf("Ошибка ввода номера телефона. Размер %s не соответствует 11!\n", e.getPhone());
            return ResponseEntity.badRequest().build();
        }
        catch (EmailException e) {
            System.out.printf("Email некорректен: %s! Введите Email с '@'\n", e.getEmail());
            return ResponseEntity.badRequest().build();
        }
        return savedOwner;
    }


    @OwnerAPI.NotFoundResponse
    @OwnerAPI.ErrorFromServerResponse
    @OwnerAPI.NoContentResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Owner by Id", description = "Удаление владельца животного по идентификатору")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "Идентификатор владельца животного") Long id) {
        ownerService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
