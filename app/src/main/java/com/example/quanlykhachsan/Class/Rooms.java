package com.example.quanlykhachsan.Class;

import java.io.Serializable;

public class Rooms implements Serializable {
    private int roomid;

    private String roomtype;
    private String status;
    private float price;
    private String image;
    public Rooms() {
    }

    public Rooms(int roomid, String roomtype, String status, float price, String image) {
        this.roomid = roomid;
        this.roomtype = roomtype;
        this.status = status;
        this.price = price;
        this.image = image;
    }

    public int getRoomid() {
        return roomid;
    }

    public void setRoomid(int roomid) {
        this.roomid = roomid;
    }

    public String getRoomtype() {
        return roomtype;
    }

    public void setRoomtype(String roomtype) {
        this.roomtype = roomtype;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return
                " Phòng = " + roomid +
                "\n Loại Phòng = " + roomtype +
                "\n Trạng Thái = " + status +
                "\n Giá = " + price ;
    }
}
