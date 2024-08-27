package ru.geekbrains.entity;

import lombok.Getter;

@Getter
public enum RoomClass {
    SUPERIOR("superior"),
    LUXURY("luxury"),
    STANDARD("standard");

    private final String name;

    RoomClass(String name) {
        this.name = name;
    }
}
