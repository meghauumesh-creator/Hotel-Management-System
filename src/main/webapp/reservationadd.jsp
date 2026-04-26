<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>New Reservation - Grand Hotel</title>
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
            max-width: 600px;
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
        
        .form-container {
            padding: 40px;
        }
        
        .form-group {
            margin-bottom: 20px;
        }
        
        label {
            display: block;
            margin-bottom: 8px;
            font-weight: 600;
            color: #333;
        }
        
        input, select {
            width: 100%;
            padding: 12px;
            border: 2px solid #e0e0e0;
            border-radius: 8px;
            font-size: 16px;
            transition: border-color 0.3s;
        }
        
        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn-submit {
            width: 100%;
            padding: 14px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 8px;
            font-size: 18px;
            font-weight: bold;
            cursor: pointer;
            transition: transform 0.3s;
        }
        
        .btn-submit:hover {
            transform: translateY(-2px);
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
        
        .alert-success {
            background: #d4edda;
            color: #155724;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #28a745;
        }
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #dc3545;
        }
        
        .price-info {
            background: #f8f9fa;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
        }
        
        .price-info p {
            margin: 5px 0;
        }
        
        @media (max-width: 768px) {
            .form-container {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>🏨 New Reservation</h1>
            <p>Book your luxurious stay</p>
        </div>
        
        <div class="form-container">
            <% if(request.getAttribute("success") != null) { %>
                <div class="alert-success">
                    <%= request.getAttribute("success") %>
                </div>
            <% } %>
            
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <div class="price-info">
                <p><strong>Room Prices:</strong></p>
                <p>🏠 Normal Room: ₹2500 per day</p>
                <p>❄️ AC Room: ₹4000 per day</p>
            </div>
            
            <form action="addReservation" method="post">
                <div class="form-group">
                    <label>Customer Name:</label>
                    <input type="text" name="customerName" required>
                </div>
                
                <div class="form-group">
                    <label>Age:</label>
                    <input type="number" name="age" required>
                </div>
                
                <div class="form-group">
                    <label>Phone Number:</label>
                    <input type="tel" name="phoneNo" required>
                </div>
                
                <div class="form-group">
                    <label>Room Number:</label>
                    <input type="text" name="roomNumber" placeholder="e.g., 101, 102" required>
                </div>
                
                <div class="form-group">
                    <label>Room Type:</label>
                    <select name="roomType" required>
                        <option value="Normal">Normal Room - ₹2500/day</option>
                        <option value="AC">AC Room - ₹4000/day</option>
                    </select>
                </div>
                
                <div class="form-group">
                    <label>Check-in Date & Time:</label>
                    <input type="datetime-local" name="checkIn" required>
                </div>
                
                <div class="form-group">
                    <label>Check-out Date & Time:</label>
                    <input type="datetime-local" name="checkOut" required>
                </div>
                
                <button type="submit" class="btn-submit">Confirm Booking</button>
            </form>
            
            <a href="index.jsp" class="btn-back">← Back to Home</a>
        </div>
    </div>
</body>
</html>