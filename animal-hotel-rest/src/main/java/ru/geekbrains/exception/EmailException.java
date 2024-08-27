package ru.geekbrains.exception;

public class EmailException extends Exception{


    private final String email;


    public String getEmail() {
        return email;
    }

    public EmailException(String message, String email) {
        super(message);
        this.email = email;
    }
}
