package com.dao;

import com.model.Reservation;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Date;
import java.text.SimpleDateFormat;

public class ReservationDAO {
    
    private String jdbcURL = "jdbc:mysql://localhost:3306/HotelDB";
    private String jdbcUsername = "root";
    private String jdbcPassword = "meri1234"; // Change this to your MySQL password
    
    private static final String INSERT_RESERVATION = "INSERT INTO Reservations (CustomerName, Age, PhoneNo, RoomNumber, RoomType, CheckIn, CheckOut, TotalAmount) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
    private static final String SELECT_RESERVATION_BY_ID = "SELECT * FROM Reservations WHERE ReservationID = ?";
    private static final String SELECT_ALL_RESERVATIONS = "SELECT * FROM Reservations ORDER BY BookingDate DESC";
    private static final String DELETE_RESERVATION = "DELETE FROM Reservations WHERE ReservationID = ?";
    private static final String UPDATE_RESERVATION = "UPDATE Reservations SET CustomerName=?, Age=?, PhoneNo=?, RoomNumber=?, RoomType=?, CheckIn=?, CheckOut=?, TotalAmount=? WHERE ReservationID=?";
    private static final String CHECK_ROOM_AVAILABILITY = "SELECT * FROM Reservations WHERE RoomNumber = ? AND ((CheckIn <= ? AND CheckOut >= ?) OR (CheckIn BETWEEN ? AND ?))";
    private static final String SELECT_RESERVATIONS_BY_DATE_RANGE = "SELECT * FROM Reservations WHERE DATE(CheckIn) BETWEEN ? AND ? ORDER BY CheckIn";
    private static final String GET_ROOMS_BOOKED_FREQUENTLY = "SELECT RoomNumber, RoomType, COUNT(*) as BookingCount FROM Reservations GROUP BY RoomNumber, RoomType ORDER BY BookingCount DESC LIMIT 5";
    private static final String GET_TOTAL_REVENUE_BY_DATE_RANGE = "SELECT SUM(TotalAmount) as TotalRevenue FROM Reservations WHERE DATE(CheckIn) BETWEEN ? AND ?";
    
    public ReservationDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword);
    }
    
    public boolean isRoomAvailable(String roomNumber, Date checkIn, Date checkOut) throws SQLException {
        boolean available = true;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(CHECK_ROOM_AVAILABILITY)) {
            preparedStatement.setString(1, roomNumber);
            preparedStatement.setTimestamp(2, new Timestamp(checkIn.getTime()));
            preparedStatement.setTimestamp(3, new Timestamp(checkOut.getTime()));
            preparedStatement.setTimestamp(4, new Timestamp(checkIn.getTime()));
            preparedStatement.setTimestamp(5, new Timestamp(checkOut.getTime()));
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                available = false;
            }
        }
        return available;
    }
    
    public int insertReservation(Reservation reservation) throws SQLException {
        int generatedId = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_RESERVATION, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, reservation.getCustomerName());
            preparedStatement.setInt(2, reservation.getAge());
            preparedStatement.setString(3, reservation.getPhoneNo());
            preparedStatement.setString(4, reservation.getRoomNumber());
            preparedStatement.setString(5, reservation.getRoomType());
            preparedStatement.setTimestamp(6, new Timestamp(reservation.getCheckIn().getTime()));
            preparedStatement.setTimestamp(7, new Timestamp(reservation.getCheckOut().getTime()));
            preparedStatement.setDouble(8, reservation.getTotalAmount());
            preparedStatement.executeUpdate();
            
            ResultSet rs = preparedStatement.getGeneratedKeys();
            if (rs.next()) {
                generatedId = rs.getInt(1);
            }
        }
        return generatedId;
    }
    
    public Reservation selectReservation(int id) throws SQLException {
        Reservation reservation = null;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVATION_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerName(rs.getString("CustomerName"));
                reservation.setAge(rs.getInt("Age"));
                reservation.setPhoneNo(rs.getString("PhoneNo"));
                reservation.setRoomNumber(rs.getString("RoomNumber"));
                reservation.setRoomType(rs.getString("RoomType"));
                reservation.setCheckIn(rs.getTimestamp("CheckIn"));
                reservation.setCheckOut(rs.getTimestamp("CheckOut"));
                reservation.setTotalAmount(rs.getDouble("TotalAmount"));
                reservation.setBookingDate(rs.getTimestamp("BookingDate"));
            }
        }
        return reservation;
    }
    
    public List<Reservation> selectAllReservations() throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_ALL_RESERVATIONS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerName(rs.getString("CustomerName"));
                reservation.setAge(rs.getInt("Age"));
                reservation.setPhoneNo(rs.getString("PhoneNo"));
                reservation.setRoomNumber(rs.getString("RoomNumber"));
                reservation.setRoomType(rs.getString("RoomType"));
                reservation.setCheckIn(rs.getTimestamp("CheckIn"));
                reservation.setCheckOut(rs.getTimestamp("CheckOut"));
                reservation.setTotalAmount(rs.getDouble("TotalAmount"));
                reservation.setBookingDate(rs.getTimestamp("BookingDate"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }
    
    public boolean deleteReservation(int id) throws SQLException {
        boolean rowDeleted;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(DELETE_RESERVATION)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        }
        return rowDeleted;
    }
    
    public boolean updateReservation(Reservation reservation) throws SQLException {
        boolean rowUpdated;
        try (Connection connection = getConnection();
             PreparedStatement statement = connection.prepareStatement(UPDATE_RESERVATION)) {
            statement.setString(1, reservation.getCustomerName());
            statement.setInt(2, reservation.getAge());
            statement.setString(3, reservation.getPhoneNo());
            statement.setString(4, reservation.getRoomNumber());
            statement.setString(5, reservation.getRoomType());
            statement.setTimestamp(6, new Timestamp(reservation.getCheckIn().getTime()));
            statement.setTimestamp(7, new Timestamp(reservation.getCheckOut().getTime()));
            statement.setDouble(8, reservation.getTotalAmount());
            statement.setInt(9, reservation.getReservationID());
            rowUpdated = statement.executeUpdate() > 0;
        }
        return rowUpdated;
    }
    
    public List<Reservation> getReservationsByDateRange(String startDate, String endDate) throws SQLException {
        List<Reservation> reservations = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_RESERVATIONS_BY_DATE_RANGE)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Reservation reservation = new Reservation();
                reservation.setReservationID(rs.getInt("ReservationID"));
                reservation.setCustomerName(rs.getString("CustomerName"));
                reservation.setAge(rs.getInt("Age"));
                reservation.setPhoneNo(rs.getString("PhoneNo"));
                reservation.setRoomNumber(rs.getString("RoomNumber"));
                reservation.setRoomType(rs.getString("RoomType"));
                reservation.setCheckIn(rs.getTimestamp("CheckIn"));
                reservation.setCheckOut(rs.getTimestamp("CheckOut"));
                reservation.setTotalAmount(rs.getDouble("TotalAmount"));
                reservations.add(reservation);
            }
        }
        return reservations;
    }
    
    public List<Object[]> getRoomsBookedMostFrequently() throws SQLException {
        List<Object[]> rooms = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_ROOMS_BOOKED_FREQUENTLY)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                Object[] room = new Object[3];
                room[0] = rs.getString("RoomNumber");
                room[1] = rs.getString("RoomType");
                room[2] = rs.getInt("BookingCount");
                rooms.add(room);
            }
        }
        return rooms;
    }
    
    public double getTotalRevenueByDateRange(String startDate, String endDate) throws SQLException {
        double revenue = 0;
        try (Connection connection = getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(GET_TOTAL_REVENUE_BY_DATE_RANGE)) {
            preparedStatement.setString(1, startDate);
            preparedStatement.setString(2, endDate);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                revenue = rs.getDouble("TotalRevenue");
            }
        }
        return revenue;
    }
}