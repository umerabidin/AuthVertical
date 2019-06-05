package com.example.authvertical.utils;

public class RoleEvent {
    int role;
    String name;

    public RoleEvent(int role, String name) {
        this.role = role;
        this.name = name;
    }

    public int getRole() {
        return role;
    }

    public String getName() {
        return name;
    }

}
