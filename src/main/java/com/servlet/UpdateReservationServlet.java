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
        
        // 1. Retrieve Raw Parameters
        String resIdStr = request.getParameter("reservationId");
        String customerName = request.getParameter("customerName");
        String ageStr = request.getParameter("age");
        String phoneNo = request.getParameter("phoneNo");
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        String checkInStr = request.getParameter("checkIn");
        String checkOutStr = request.getParameter("checkOut");

        try {
            // --- DATA VALIDATION START ---

            // A. Reservation ID Validation (Must be a positive number)
            int reservationId;
            try {
                reservationId = Integer.parseInt(resIdStr);
                if (reservationId <= 0) throw new Exception();
            } catch (Exception e) {
                request.setAttribute("error", "Invalid Reservation ID: Must be a positive number.");
                request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
                return;
            }

            // B. Name Validation (Only letters and spaces)
            if (customerName == null || !customerName.matches("^[a-zA-Z\\s]+$")) {
                request.setAttribute("error", "Invalid Name: Name should accept only characters.");
                request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
                return;
            }

            // C. Age Validation (Positive numbers only)
            int age;
            try {
                age = Integer.parseInt(ageStr);
                if (age <= 0) throw new Exception();
            } catch (Exception e) {
                request.setAttribute("error", "Invalid Age: Age should accept only positive numbers.");
                request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
                return;
            }

            // D. Phone Number Validation (Exactly 10 digits)
            if (phoneNo == null || !phoneNo.matches("^\\d{10}$")) {
                request.setAttribute("error", "Invalid Phone: Phone number should have 10 digits.");
                request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
                return;
            }

            // --- DATA VALIDATION END ---

            // 2. Date Parsing (Corrected to dd/MM/yyyy)
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date checkIn = dateFormat.parse(checkInStr);
            Date checkOut = dateFormat.parse(checkOutStr);

            // 3. Revenue Recalculation
            double pricePerDay = roomType.equals("AC") ? 4000 : 2500;
            long diffInMillies = checkOut.getTime() - checkIn.getTime();
            long days = diffInMillies / (1000 * 60 * 60 * 24);
            if (days <= 0) days = 1;
            double totalAmount = days * pricePerDay;

            // 4. Create and Set Model
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
            
            // 5. Execute Update
            boolean updated = reservationDAO.updateReservation(reservation);
            
            if (updated) {
                request.setAttribute("success", "Your update is done successfully for Reservation ID: " + reservationId);
            } else {
                request.setAttribute("error", "Reservation not found with ID: " + reservationId);
            }
            
        } catch (java.text.ParseException e) {
            request.setAttribute("error", "Invalid date format. Please use dd/mm/yyyy.");
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred: " + e.getMessage());
        }
        
        request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reservationupdate.jsp").forward(request, response);
    }
}
