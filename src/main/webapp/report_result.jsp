<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List, com.model.Reservation, java.text.SimpleDateFormat" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Report Result - Grand Hotel</title>
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
        
        .report-table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        
        .report-table th {
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            padding: 12px;
            text-align: left;
        }
        
        .report-table td {
            padding: 12px;
            border-bottom: 1px solid #e0e0e0;
        }
        
        .report-table tr:hover {
            background: #f5f5f5;
        }
        
        .revenue-box {
            text-align: center;
            padding: 30px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            border-radius: 10px;
            color: white;
            margin: 20px 0;
        }
        
        .revenue-amount {
            font-size: 3rem;
            font-weight: bold;
            margin-top: 10px;
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
        
        .btn-new-report {
            display: inline-block;
            margin-top: 20px;
            margin-left: 10px;
            padding: 10px 20px;
            background: #28a745;
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
            <h1>📈 Report Result</h1>
            <p>Generated Report Details</p>
        </div>
        
        <div class="content">
            <%
                String reportType = (String) request.getAttribute("reportType");
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                
                if("dateRange".equals(reportType)) {
                    List<Reservation> reservations = (List<Reservation>) request.getAttribute("reservations");
                    String startDate = (String) request.getAttribute("startDate");
                    String endDate = (String) request.getAttribute("endDate");
            %>
                <h2>Reservations from <%= startDate %> to <%= endDate %></h2>
                
                <% if(reservations != null && !reservations.isEmpty()) { %>
                    <table class="report-table">
                        <thead>
                            <tr>
                                <th>Reservation ID</th>
                                <th>Customer Name</th>
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
                    <div class="no-data">No reservations found in this date range.</div>
                <% } %>
                
            <% } else if("frequentRooms".equals(reportType)) {
                List<Object[]> rooms = (List<Object[]>) request.getAttribute("rooms");
            %>
                <h2>Rooms Booked Most Frequently</h2>
                
                <% if(rooms != null && !rooms.isEmpty()) { %>
                    <table class="report-table">
                        <thead>
                            <tr>
                                <th>Room Number</th>
                                <th>Room Type</th>
                                <th>Number of Bookings</th>
                            </tr>
                        </thead>
                        <tbody>
                            <% for(Object[] room : rooms) { %>
                                <tr>
                                    <td><%= room[0] %></td>
                                    <td><%= room[1] %></td>
                                    <td><%= room[2] %></td>
                                </tr>
                            <% } %>
                        </tbody>
                    </table>
                <% } else { %>
                    <div class="no-data">No booking data available.</div>
                <% } %>
                
            <% } else if("revenue".equals(reportType)) {
                double revenue = (Double) request.getAttribute("revenue");
                String startDate = (String) request.getAttribute("startDate");
                String endDate = (String) request.getAttribute("endDate");
            %>
                <h2>Total Revenue from <%= startDate %> to <%= endDate %></h2>
                <div class="revenue-box">
                    <p>Total Revenue Generated</p>
                    <div class="revenue-amount">₹<%= String.format("%.2f", revenue) %></div>
                </div>
            <% } %>
            
            <div>
                <a href="report" class="btn-new-report">Generate New Report</a>
                <a href="index.jsp" class="btn-back">Back to Home</a>
            </div>
        </div>
    </div>
</body>
</html>