package ru.geekbrains.exception;

import ru.geekbrains.entity.AnimalClass;

public class AnimalMatchesException extends Exception{

    private final AnimalClass animalClass;

    private final AnimalClass animalRoomClass;


    public AnimalMatchesException(String message, AnimalClass animalClass, AnimalClass animalRoomClass) {
        super(message);
        this.animalClass = animalClass;
        this.animalRoomClass = animalRoomClass;
    }

    public AnimalClass getAnimalRoomClass() {
        return animalRoomClass;
    }

    public AnimalClass getAnimalClass() {
        return animalClass;
    }
}
