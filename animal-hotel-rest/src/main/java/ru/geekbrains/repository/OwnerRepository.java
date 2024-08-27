package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.Owner;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {
}
