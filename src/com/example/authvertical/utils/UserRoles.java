package com.example.authvertical.utils;

public class UserRoles {
    String roleId;
    String name;

    public UserRoles(String roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    public String getRoleId() {
        return roleId;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "{" +
                "roleId='" + roleId + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
