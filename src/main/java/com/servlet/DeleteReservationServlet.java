package com.servlet;

import com.dao.ReservationDAO;
import com.model.Reservation;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/deleteReservation")
public class DeleteReservationServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDAO reservationDAO;
    
    @Override
    public void init() {
        reservationDAO = new ReservationDAO();
        System.out.println("DeleteReservationServlet initialized");
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        System.out.println("DeleteReservationServlet doPost called");
        
        String reservationIdParam = request.getParameter("reservationId");
        System.out.println("Received reservationId parameter: " + reservationIdParam);
        
        // Check if parameter is null or empty
        if (reservationIdParam == null || reservationIdParam.trim().isEmpty()) {
            System.out.println("Reservation ID is empty");
            request.setAttribute("error", "Please enter a Reservation ID");
            request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
            return;
        }
        
        try {
            int reservationId = Integer.parseInt(reservationIdParam.trim());
            System.out.println("Parsed reservation ID: " + reservationId);
            
            // First check if reservation exists
            Reservation reservation = reservationDAO.selectReservation(reservationId);
            System.out.println("Reservation found: " + (reservation != null));
            
            if (reservation == null) {
                System.out.println("Reservation not found with ID: " + reservationId);
                request.setAttribute("error", "Reservation not found with ID: " + reservationId + ". Please check the ID and try again.");
                request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
                return;
            }
            
            // Attempt to delete
            boolean deleted = reservationDAO.deleteReservation(reservationId);
            System.out.println("Delete operation result: " + deleted);
            
            if (deleted) {
                String successMsg = "Your reservation is canceled successfully for Reservation ID: " + reservationId + 
                                    ". Customer: " + reservation.getCustomerName() + ", Room: " + reservation.getRoomNumber();
                System.out.println(successMsg);
                request.setAttribute("success", successMsg);
            } else {
                System.out.println("Failed to delete reservation");
                request.setAttribute("error", "Failed to cancel reservation. Please try again.");
            }
            
            request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
            
        } catch (NumberFormatException e) {
            System.out.println("Number format exception: " + e.getMessage());
            request.setAttribute("error", "Invalid Reservation ID format. Please enter a valid number.");
            request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
        } catch (Exception e) {
            System.out.println("Exception in delete: " + e.getMessage());
            e.printStackTrace();
            String errorMsg = "Database error occurred: " + e.getMessage();
            request.setAttribute("error", errorMsg);
            request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
        }
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        System.out.println("DeleteReservationServlet doGet called");
        request.getRequestDispatcher("reservationdelete.jsp").forward(request, response);
    }
}