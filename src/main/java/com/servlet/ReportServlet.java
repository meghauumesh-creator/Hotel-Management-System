package com.servlet;

import com.dao.ReservationDAO;
import com.model.Reservation;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private ReservationDAO reservationDAO;
    
    public void init() {
        reservationDAO = new ReservationDAO();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.getRequestDispatcher("report_form.jsp").forward(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String reportType = request.getParameter("reportType");
        
        try {
            if ("dateRange".equals(reportType)) {
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                List<Reservation> reservations = reservationDAO.getReservationsByDateRange(startDate, endDate);
                request.setAttribute("reportType", "dateRange");
                request.setAttribute("reservations", reservations);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
                
            } else if ("frequentRooms".equals(reportType)) {
                List<Object[]> rooms = reservationDAO.getRoomsBookedMostFrequently();
                request.setAttribute("reportType", "frequentRooms");
                request.setAttribute("rooms", rooms);
                
            } else if ("revenue".equals(reportType)) {
                String startDate = request.getParameter("startDate");
                String endDate = request.getParameter("endDate");
                double revenue = reservationDAO.getTotalRevenueByDateRange(startDate, endDate);
                request.setAttribute("reportType", "revenue");
                request.setAttribute("revenue", revenue);
                request.setAttribute("startDate", startDate);
                request.setAttribute("endDate", endDate);
            }
            
            request.getRequestDispatcher("report_result.jsp").forward(request, response);
            
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "An error occurred while generating the report.");
            request.getRequestDispatcher("report_form.jsp").forward(request, response);
        }
    }
}