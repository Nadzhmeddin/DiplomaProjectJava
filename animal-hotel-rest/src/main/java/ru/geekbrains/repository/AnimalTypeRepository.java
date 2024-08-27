package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.entity.AnimalType;

public interface AnimalTypeRepository extends JpaRepository<AnimalType, Long> {

}
