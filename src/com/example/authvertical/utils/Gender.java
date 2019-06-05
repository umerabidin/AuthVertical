package com.example.authvertical.utils;

public class Gender {
    String type;
    String name;

    public Gender(String type , String name) {
        this.type = type;
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "type='" + type + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
