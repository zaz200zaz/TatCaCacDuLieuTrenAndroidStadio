package com.example.rescview;

public class User {
    private int resutId;
    private String name;
    private String address;

    public User(int resutId, String name, String address) {
        this.resutId = resutId;
        this.name = name;
        this.address = address;
    }

    public int getResutId() {
        return resutId;
    }

    public void setResutId(int resutId) {
        this.resutId = resutId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
