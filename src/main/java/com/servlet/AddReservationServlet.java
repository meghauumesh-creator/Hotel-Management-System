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

@WebServlet("/addReservation")
public class AddReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDAO reservationDAO;

    public void init() {
        reservationDAO = new ReservationDAO();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 1. Retrieve Parameters
        String customerName = request.getParameter("customerName");
        String roomNumber = request.getParameter("roomNumber");
        String roomType = request.getParameter("roomType");
        String ageStr = request.getParameter("age");
        String phoneNo = request.getParameter("phoneNo");
        String checkInStr = request.getParameter("checkIn");
        String checkOutStr = request.getParameter("checkOut");

        try {
            // --- DATA VALIDATION START ---

            // A. Name Validation
            if (customerName == null || !customerName.matches("^[a-zA-Z\\s]+$")) {
                request.setAttribute("error", "Invalid Name: Name should accept only characters.");
                request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                return;
            }

            // B. Age Validation (Numbers only AND No Negative Numbers)
            int age;
            try {
                age = Integer.parseInt(ageStr);
                if (age <= 0) {
                    request.setAttribute("error", "Invalid Age: Age should be a positive number.");
                    request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                    return;
                }
            } catch (NumberFormatException e) {
                request.setAttribute("error", "Invalid Age: Age should accept only numbers.");
                request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                return;
            }

            // C. Phone Number Validation
            if (phoneNo == null || !phoneNo.matches("^\\d{10}$")) {
                request.setAttribute("error", "Invalid Phone: Phone number should have 10 digits.");
                request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                return;
            }

            // D. Room Number Limit
            int roomNumInt = Integer.parseInt(roomNumber);
            if (roomNumInt > 2000) {
                request.setAttribute("error", "The room is not available");
                request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                return;
            }

            // --- DATA VALIDATION END ---

            // 2. Date Parsing
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date checkIn = dateFormat.parse(checkInStr);
            Date checkOut = dateFormat.parse(checkOutStr);

            // 3. Availability Check 
            // FIXED: Added checkIn and checkOut to match your DAO method signature
            boolean isAvailable = reservationDAO.isRoomAvailable(roomNumber, checkIn, checkOut);
            
            if (!isAvailable) {
                request.setAttribute("error", "The room is already reserved..please book another room");
                request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
                return;
            }

            // 4. Revenue Calculation
            double pricePerDay = "AC".equals(roomType) ? 4000 : 2500;
            long diffInMillies = checkOut.getTime() - checkIn.getTime();
            long days = diffInMillies / (1000 * 60 * 60 * 24);
            if (days <= 0) days = 1;
            double totalAmount = days * pricePerDay;

            // 5. Create Model and Save
            Reservation reservation = new Reservation();
            reservation.setCustomerName(customerName);
            reservation.setAge(age);
            reservation.setPhoneNo(phoneNo);
            reservation.setRoomNumber(roomNumber);
            reservation.setRoomType(roomType);
            reservation.setCheckIn(checkIn);
            reservation.setCheckOut(checkOut);
            reservation.setTotalAmount(totalAmount);

            int generatedId = reservationDAO.insertReservation(reservation);

            // 6. Success Response
            request.setAttribute("success", "Booking confirmed! Reservation ID: " + generatedId);
            request.getRequestDispatcher("reservationadd.jsp").forward(request, response);

        } catch (java.text.ParseException e) {
            request.setAttribute("error", "Invalid date format. Please use dd/mm/yyyy.");
            request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "The room is already reserved..please book another room");
            request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
    }
}
