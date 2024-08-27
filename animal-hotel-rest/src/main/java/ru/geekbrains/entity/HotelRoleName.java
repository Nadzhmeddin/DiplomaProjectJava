package ru.geekbrains.entity;

public enum HotelRoleName {
    ADMIN("admin"),
    USER("user"),
    ZOOKEEPER("zookeeper");

    private final String name;

    HotelRoleName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
