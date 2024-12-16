package com.example.quanlykhachsan.Class;

import java.io.Serializable;

public class Customers implements Serializable {
    private int id;
    private String name;
    private String email;
    private String phone;
    private String username;
    private String password;

    public Customers() {
    }

    public Customers(int id, String name, String phone, String email, String username, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.username = username;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return
                "id=" + id +
                "\nname=" + name +
                "\nemail=" + email +
                "\nphone=" + phone +
                "\nusername=" + username +
                "\npassword=" + password ;
    }
}
