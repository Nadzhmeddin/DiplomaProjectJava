package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Animal;

import java.util.List;

@Repository
public interface AnimalRepository extends JpaRepository<Animal, Long> {

    @Query("select a from Animal a where a.owner.id = :ownerId order by a.age desc")
    List<Animal> findByOwnerId(Long ownerId);


    @Query("select a from Animal a where a.animalType.id = :animalTypeId order by a.age desc")
    List<Animal> findByAnimalTypeId(Long animalTypeId);
}
