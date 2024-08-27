package ru.geekbrains.exception;

public class AnimalAgeException extends Exception{

    private final Integer age;

    public AnimalAgeException(String message, Integer age) {
        super(message);
        this.age = age;
    }

    public Integer getAge() {
        return age;
    }
}
