package ru.geekbrains.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekbrains.entity.HotelUserRole;

import java.util.List;

public interface HotelUserRoleRepository extends JpaRepository<HotelUserRole, Long> {

    List<HotelUserRole> findHotelUserRoleById(Long id);
}
