package ru.geekbrains.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.geekbrains.entity.Animal;
import ru.geekbrains.exception.AnimalAgeException;
import ru.geekbrains.responseAnnotation.AnimalAPI;
import ru.geekbrains.service.AnimalService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/animals")
@RequiredArgsConstructor
@Tag(name = "Animals", description = "API для работы с животными")
public class AnimalRestController {

    private final AnimalService animalService;

    @AnimalAPI.ErrorFromServerResponse
    @AnimalAPI.OKResponse
    @GetMapping
    @Operation(summary = "Get All Animals", description = "Получение списком всех животных")
    public ResponseEntity<List<Animal>> findAll() {
        return ResponseEntity.ok().body(animalService.findAll());
    }

    @AnimalAPI.NotFoundResponse
    @AnimalAPI.ErrorFromServerResponse
    @AnimalAPI.OKResponse
    @GetMapping("/{id}")
    @Operation(summary = "Get Animal by Id", description = "Получение животного по идентификатору")
    public ResponseEntity<Optional<Animal>> findById(@PathVariable @Parameter(description = "Идентификатор животного") Long id) {
        Optional<Animal> foundAnimal = animalService.findById(id);
        if(foundAnimal.isPresent()) {
            return ResponseEntity.ok().body(foundAnimal);
        }
        return ResponseEntity.notFound().build();
    }


    @AnimalAPI.CREATEDResponse
    @AnimalAPI.ErrorFromServerResponse
    @PostMapping
    @Operation(summary = "Post Animal", description = "Сохранение животного")
    public ResponseEntity<Animal> save(@RequestBody Animal animal) {
        ResponseEntity<Animal> savedAnimal;
        try {
            savedAnimal = ResponseEntity.status(HttpStatus.CREATED).body(animalService.save(animal));
        } catch (AnimalAgeException e) {
            System.out.printf("Возраст животного указан некорректно: %s", e.getAge());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return savedAnimal;
    }

    @AnimalAPI.NotFoundResponse
    @AnimalAPI.ErrorFromServerResponse
    @AnimalAPI.NoContentResponse
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Animal", description = "Удаление животного по идентификатору")
    public ResponseEntity<Void> deleteById(@PathVariable @Parameter(description = "Идентификатор животного") Long id) {
        animalService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @AnimalAPI.OKResponse
    @AnimalAPI.ErrorFromServerResponse
    @GetMapping("/owners/{id}")
    @Operation(summary = "Get Animal By Owner Id", description = "Получение животных по идентификатору хозяина")
    public ResponseEntity<List<Animal>> findByOwnerId(@PathVariable @Parameter(description = "Идентификатор хозяина животного") Long id) {
        return ResponseEntity.ok().body(animalService.findByOwnerId(id));
    }

    @AnimalAPI.OKResponse
    @AnimalAPI.ErrorFromServerResponse
    @GetMapping("/animal-type/{id}")
    @Operation(summary = "Get Animal By Animal Type Id", description = "Получение животных по идентификатору типа животного")
    public ResponseEntity<List<Animal>> findByAnimalTypeId(@PathVariable @Parameter(description = "Идентификатор типа животного") Long id) {
        return ResponseEntity.ok().body(animalService.findByAnimalTypeId(id));
    }
}
