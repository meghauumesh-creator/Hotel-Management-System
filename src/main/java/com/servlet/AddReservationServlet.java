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
String customerName = request.getParameter("customerName");
String roomNumber = request.getParameter("roomNumber");
String roomType = request.getParameter("roomType");
// Handling potential NullPointer if parameters are missing
String ageStr = request.getParameter("age");
String phoneNo = request.getParameter("phoneNo");
int age = (ageStr != null && !ageStr.isEmpty()) ? Integer.parseInt(ageStr) : 0;
SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
Date checkIn = null;
Date checkOut = null;
try {
checkIn = dateFormat.parse(request.getParameter("checkIn"));
checkOut = dateFormat.parse(request.getParameter("checkOut"));
// 1. Check if Room is already reserved

// Ensure your ReservationDAO has the isRoomAvailable method implemented

boolean isAvailable = reservationDAO.isRoomAvailable(roomNumber, checkIn, checkOut);
if (!isAvailable) {

// This is the requirement: show error if room is already taken

request.setAttribute("error", "This room is already reserved! Please select other rooms.");

request.getRequestDispatcher("reservationadd.jsp").forward(request, response);

return; // Stop further execution

}
// 2. Calculate Total Amount

double pricePerDay = roomType.equals("AC") ? 4000 : 2500;

long diffInMillies = checkOut.getTime() - checkIn.getTime();

long days = diffInMillies / (1000 * 60 * 60 * 24);

// If stay is less than 24 hours but spans a night, count as 1 day

if (days <= 0) days = 1;
double totalAmount = days * pricePerDay;
// 3. Create and set Reservation object

Reservation reservation = new Reservation();

reservation.setCustomerName(customerName);

reservation.setAge(age);

reservation.setPhoneNo(phoneNo);

reservation.setRoomNumber(roomNumber);

reservation.setRoomType(roomType);

reservation.setCheckIn(checkIn);

reservation.setCheckOut(checkOut);

reservation.setTotalAmount(totalAmount);
// 4. Save to Database

int generatedId = reservationDAO.insertReservation(reservation);
// 5. Success response

request.setAttribute("success", "Booking confirmed! Reservation ID: " + generatedId);

request.setAttribute("totalAmount", totalAmount);

request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
} catch (Exception e) {

e.printStackTrace();

request.setAttribute("error", "An error occurred while processing your booking.The room is already reserved.Please select another room number ! ");

request.getRequestDispatcher("reservationadd.jsp").forward(request, response);

}

}
protected void doGet(HttpServletRequest request, HttpServletResponse response)

throws ServletException, IOException {

request.getRequestDispatcher("reservationadd.jsp").forward(request, response);

}

} s ServletException, IOException {
        request.getRequestDispatcher("reservationadd.jsp").forward(request, response);
    }
}
