package com.servlet;

import com.dao.ReservationDAO;
import com.model.Reservation;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/updateReservation")
public class UpdateReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDAO reservationDAO;
    
    public void init() {
        reservationDAO = new ReservationDAO();
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        int reservationId = Integer.parseInt(request.getParameter("reservationId"));
        String customerName = request.getParameter("customerName");
        int age = Integer.parseInt(request.getParameter("age"));
        String phoneNo = request.getParameter("phoneNo");
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
        Date checkIn = null;
        Date checkOut = null;
        
        try {
            checkIn = dateFormat.parse(request.getParameter("checkIn"));
            checkOut = dateFormat.parse(request.getParameter("checkOut"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        double pricePerDay = roomType.equals("AC") ? 4000 : 2500;
        long diffInMillies = checkOut.getTime() - checkIn.getTime();
        long days = diffInMillies / (1000 * 60 * 60 * 24);
        double totalAmount = days * pricePerDay;
        
        try {
            Reservation reservation = new Reservation();
            reservation.setReservationID(reservationId);
            reservation.setCustomerName(customerName);
            reservation.setAge(age);
            reservation.setPhoneNo(phoneNo);
            reservation.setRoomNumber(roomNumber);
            reservation.setRoomType(roomType);
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            reservation.setTotalAmount(totalAmount);
            
            boolean updated = reservationDAO.updateReservation(reservation);
            
            if (updated) {
                request.setAttribute("success", "Your update is done successfully for Reservation ID: " + reservationId);
            } else {
                request.setAttribute("error", "Reservation not found with ID: " + reservationId);
            }
            
            request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while updating the reservation.");
            request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
        }
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
    }
}