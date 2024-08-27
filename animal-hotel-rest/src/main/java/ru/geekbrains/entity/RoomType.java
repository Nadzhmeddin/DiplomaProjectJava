package ru.geekbrains.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@Data
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private RoomClass roomClass;

    @Enumerated(EnumType.STRING)
    private AnimalClass animalClass;

    @Column(name = "price_per_Night")
    private Integer pricePerNight;
}
