package ru.geekbrains.exception;

public class NightsException extends Exception{

    private final int nights;

    public NightsException(String message, int nights) {
        super(message);
        this.nights = nights;
    }

    public int getNights() {
        return nights;
    }
}
