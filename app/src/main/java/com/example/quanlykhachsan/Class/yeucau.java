package com.example.quanlykhachsan.Class;

import java.io.Serializable;
import java.util.Date;

public class yeucau implements Serializable {

    private String customer;
    private String room;
    private String service;
    private String date;
    private double price;

    public yeucau() {
    }

    public yeucau( String customer, String room, String service, String date, double price) {

        this.customer = customer;
        this.room = room;
        this.service = service;
        this.date = date;
        this.price = price;
    }



    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "yeucau{" +

                ", customer='" + customer + '\'' +
                ", room='" + room + '\'' +
                ", service='" + service + '\'' +
                ", date=" + date +
                ", price=" + price +
                '}';
    }
}
