package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekbrains.entity.HotelUser;

import java.util.Optional;

@Repository
public interface HotelUserRepository extends JpaRepository<HotelUser, Long> {

    Optional<HotelUser> findByLogin(String login);
}
