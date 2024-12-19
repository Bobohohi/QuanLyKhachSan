package com.example.quanlykhachsan.Class;

public class Bill {
    private int bookingDetailId;  // Mới thêm
    private int bookingId;
    private int roomId;
    private double price;
    private int numberOfNight;
    private double totalPrice;

    @Override
    public String toString() {
        return "Bill{" +
                "bookingDetailId=" + bookingDetailId +
                ", bookingId=" + bookingId +
                ", roomId=" + roomId +
                ", price=" + price +
                ", numberOfNight=" + numberOfNight +
                ", totalPrice=" + totalPrice +
                '}';
    }

    public Bill(int bookingDetailId, int bookingId, int roomId, double price, int numberOfNight, double totalPrice) {
        this.bookingDetailId = bookingDetailId;
        this.bookingId = bookingId;
        this.roomId = roomId;
        this.price = price;
        this.numberOfNight = numberOfNight;
        this.totalPrice = totalPrice;
    }

    // Getter và Setter cho tất cả các thuộc tính
    public int getBookingDetailId() {
        return bookingDetailId;
    }

    public void setBookingDetailId(int bookingDetailId) {
        this.bookingDetailId = bookingDetailId;
    }

    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getNumberOfNight() {
        return numberOfNight;
    }

    public void setNumberOfNight(int numberOfNight) {
        this.numberOfNight = numberOfNight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
