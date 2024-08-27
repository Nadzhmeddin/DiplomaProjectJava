package ru.geekbrains.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "hotel_roles")
@NoArgsConstructor
@Data
public class HotelUserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @ManyToMany(mappedBy = "hotelUserRole", fetch = FetchType.LAZY)
    private List<HotelUser> hotelUserList;

    public HotelUserRole(String name) {
        this.name = name;
    }
}
