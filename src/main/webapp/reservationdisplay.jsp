<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Reservation, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>All Reservations - Grand Hotel</title>
    <style>
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
        }
        
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        
        .container {
            max-width: 1400px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        
        .header {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        
        .header h1 {
            font-size: 2rem;
            margin-bottom: 10px;
        }
        
        .content {
            padding: 40px;
        }
        
        .reservation-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .reservation-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px;
            text-align: left;
        }
        
        .reservation-table td {
            padding: 12px;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .reservation-table tr:hover {
            background: #f5f5f5;
        }
        
        .btn-back {
            display: inline-block;
            margin-top: 20px;
            padding: 10px 20px;
            background: #6c757d;
            color: white;
            text-decoration: none;
            border-radius: 8px;
            text-align: center;
        }
        
        .no-data {
            text-align: center;
            padding: 40px;
            color: #666;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #dc3545;
        }
        
        @media (max-width: 768px) {
            .content {
                padding: 20px;
                overflow-x: auto;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📋 All Reservations</h1>
            <p>Complete list of all hotel bookings</p>
        </div>
        
        <div class="content">
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <%
                List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                
                if(reservations != null && !reservations.isEmpty()) {
            %>
                <table class="reservation-table">
                    <thead>
                        <tr>
                            <th>Reservation ID</th>
                            <th>Customer Name</th>
                            <th>Age</th>
                            <th>Phone No</th>
                            <th>Room No</th>
                            <th>Room Type</th>
                            <th>Check In</th>
                            <th>Check Out</th>
                            <th>Total Amount</th>
                        </tr>
                    </thead>
                    <tbody>
                        <% for(Reservation r : reservations) { %>
                            <tr>
                                <td><%= r.getReservationID() %></td>
                                <td><%= r.getCustomerName() %></td>
                                <td><%= r.getAge() %></td>
                                <td><%= r.getPhoneNo() %></td>
                                <td><%= r.getRoomNumber() %></td>
                                <td><%= r.getRoomType() %></td>
                                <td><%= sdf.format(r.getCheckIn()) %></td>
                                <td><%= sdf.format(r.getCheckOut()) %></td>
                                <td>₹<%= String.format("%.2f", r.getTotalAmount()) %></td>
                            </tr>
                        <% } %>
                    </tbody>
                </table>
            <% } else { %>
                <div class="no-data">
                    <p>No reservations found.</p>
                </div>
            <% } %>
            
            <a href="index.jsp" class="btn-back">← Back to Home</a>
        </div>
    </div>
</body>
</html>