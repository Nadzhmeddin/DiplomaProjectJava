package ru.geekbrains.entity;

import lombok.Getter;

@Getter
public enum AnimalClass {
    DOG("dog"),
    CAT("cat"),
    HAMSTER("hamster");

    private final String type;

    AnimalClass(String type) {
        this.type = type;
    }
}
