package ru.geekbrains.exception;

public class PhoneException extends Exception {


    private final String phone;


    public String getPhone() {
        return phone;
    }

    public PhoneException(String message, String phone) {
        super(message);
        this.phone = phone;
    }
}
