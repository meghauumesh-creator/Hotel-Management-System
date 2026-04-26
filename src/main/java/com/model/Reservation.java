package com.model;

import java.util.Date;

public class Reservation {
    private int reservationID;
    private String customerName;
    private int age;
    private String phoneNo;
    private String roomNumber;
    private String roomType;
    private Date checkIn;
    private Date checkOut;
    private double totalAmount;
    private Date bookingDate;
    
    public Reservation() {}
    
    public Reservation(int reservationID, String customerName, int age, String phoneNo, 
                      String roomNumber, String roomType, Date checkIn, Date checkOut, 
                      double totalAmount, Date bookingDate) {
        this.reservationID = reservationID;
        this.customerName = customerName;
        this.age = age;
        this.phoneNo = phoneNo;
        this.roomNumber = roomNumber;
        this.roomType = roomType;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.totalAmount = totalAmount;
        this.bookingDate = bookingDate;
    }
    
    // Getters and Setters
    public int getReservationID() { return reservationID; }
    public void setReservationID(int reservationID) { this.reservationID = reservationID; }
    
    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }
    
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
    
    public String getPhoneNo() { return phoneNo; }
    public void setPhoneNo(String phoneNo) { this.phoneNo = phoneNo; }
    
    public String getRoomNumber() { return roomNumber; }
    public void setRoomNumber(String roomNumber) { this.roomNumber = roomNumber; }
    
    public String getRoomType() { return roomType; }
    public void setRoomType(String roomType) { this.roomType = roomType; }
    
    public Date getCheckIn() { return checkIn; }
    public void setCheckIn(Date checkIn) { this.checkIn = checkIn; }
    
    public Date getCheckOut() { return checkOut; }
    public void setCheckOut(Date checkOut) { this.checkOut = checkOut; }
    
    public double getTotalAmount() { return totalAmount; }
    public void setTotalAmount(double totalAmount) { this.totalAmount = totalAmount; }
    
    public Date getBookingDate() { return bookingDate; }
    public void setBookingDate(Date bookingDate) { this.bookingDate = bookingDate; }
}