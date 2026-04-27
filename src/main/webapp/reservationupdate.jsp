<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Reservation - Grand Hotel</title>
    <style>
        * { margin: 0; padding: 0; box-sizing: border-box; }
        body {
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            min-height: 100vh;
            padding: 20px;
        }
        .container {
            max-width: 600px;
            margin: 0 auto;
            background: white;
            border-radius: 15px;
            box-shadow: 0 20px 60px rgba(0,0,0,0.3);
            overflow: hidden;
        }
        .header {
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            padding: 30px;
            text-align: center;
        }
        .header h1 { font-size: 2rem; margin-bottom: 10px; }
        .form-container { padding: 40px; }
        .form-group { margin-bottom: 20px; }
        label { display: block; margin-bottom: 8px; font-weight: 600; color: #333; }
        input, select {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        input:focus { outline: none; border-color: #f5576c; }
        .btn-submit {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
        }
        .alert-success { background: #d4edda; color: #155724; padding: 15px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #28a745; }
        .alert-error { background: #f8d7da; color: #721c24; padding: 15px; border-radius: 8px; margin-bottom: 20px; border-left: 4px solid #dc3545; }
        .btn-back { display: inline-block; margin-top: 20px; padding: 10px 20px; background: #6c757d; color: white; text-decoration: none; border-radius: 8px; }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>✏️ Update Reservation</h1>
            <p>Modify your booking details</p>
        </div>
        
        <div class="form-container">
            <% if(request.getAttribute("success") != null) { %>
                <div class="alert-success"><%= request.getAttribute("success") %></div>
            <% } %>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert-error"><%= request.getAttribute("error") %></div>
            <% } %>
            
            <form action="updateReservation" method="post">
                <div class="form-group">
                    <label>Reservation ID:</label>
                    <input type="number" name="reservationId" placeholder="Enter ID" min="1" required>
                </div>
                
                <div class="form-group">
                    <label>Customer Name:</label>
                    <input type="text" name="customerName" pattern="[A-Za-z\s]+" title="Name should accept only characters." required>
                </div>
                
                <div class="form-group">
                    <label>Age:</label>
                    <input type="number" name="age" min="1" max="120" required>
                </div>
                
                <div class="form-group">
                    <label>Phone Number:</label>
                    <input type="text" name="phoneNo" pattern="\d{10}" title="Must be exactly 10 digits." required>
                </div>
                
                <div class="form-group">
                    <label>Room Number:</label>
                    <input type="number" name="roomNumber" placeholder="e.g., 101" required>
                </div>
                
                <div class="form-group">
                    <label>Room Type:</label>
                    <select name="roomType" required>
                        <option value="Normal">Normal Room - ₹2500/day</option>
                        <option value="AC">AC Room - ₹4000/day</option>
                    </select>
                </div>
                
                <%-- Corrected Date Inputs for Update --%>
                <div class="form-group">
                    <label>Check-in Date (dd/mm/yyyy):</label>
                    <input type="text" name="checkIn" placeholder="dd/mm/yyyy" pattern="\d{2}/\d{2}/\d{4}" title="Please use dd/mm/yyyy format" required>
                </div>
                
                <div class="form-group">
                    <label>Check-out Date (dd/mm/yyyy):</label>
                    <input type="text" name="checkOut" placeholder="dd/mm/yyyy" pattern="\d{2}/\d{2}/\d{4}" title="Please use dd/mm/yyyy format" required>
                </div>
                
                <button type="submit" class="btn-submit">Update Reservation</button>
            </form>
            
            <a href="index.jsp" class="btn-back">← Back to Home</a>
        </div>
    </div>
</body>
</html>
