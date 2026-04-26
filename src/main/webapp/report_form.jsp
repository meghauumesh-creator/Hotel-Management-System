<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reports - Grand Hotel</title>
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
            max-width: 800px;
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
        
        .report-option {
            margin-bottom: 40px;
            padding: 20px;
            border: 2px solid #e0e0e0;
            border-radius: 10px;
            transition: all 0.3s;
        }
        
        .report-option:hover {
            border-color: #667eea;
            box-shadow: 0 5px 15px rgba(0,0,0,0.1);
        }
        
        .report-title {
            font-size: 1.3rem;
            font-weight: bold;
            color: #667eea;
            margin-bottom: 15px;
        }
        
        .form-group {
            margin-bottom: 15px;
        }
        
        label {
            display: block;
            margin-bottom: 5px;
            font-weight: 600;
            color: #333;
        }
        
        input, select {
            width: 100%;
            padding: 10px;
            border: 2px solid #e0e0e0;
            border-radius: 5px;
            font-size: 14px;
        }
        
        input:focus, select:focus {
            outline: none;
            border-color: #667eea;
        }
        
        .btn-generate {
            padding: 10px 20px;
            background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            font-size: 14px;
            margin-top: 10px;
        }
        
        .btn-generate:hover {
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
        
        .alert-error {
            background: #f8d7da;
            color: #721c24;
            padding: 15px;
            border-radius: 8px;
            margin-bottom: 20px;
            border-left: 4px solid #dc3545;
        }
        
        hr {
            margin: 20px 0;
            border: none;
            border-top: 2px solid #e0e0e0;
        }
        
        @media (max-width: 768px) {
            .content {
                padding: 20px;
            }
        }
    </style>
</head>
<body>
    <div class="container">
        <div class="header">
            <h1>📊 Reports & Analytics</h1>
            <p>Generate various reports</p>
        </div>
        
        <div class="content">
            <% if(request.getAttribute("error") != null) { %>
                <div class="alert-error">
                    <%= request.getAttribute("error") %>
                </div>
            <% } %>
            
            <!-- Report 1: Reservations in Date Range -->
            <div class="report-option">
                <div class="report-title">1. Reservations in a Date Range</div>
                <form action="report" method="post">
                    <input type="hidden" name="reportType" value="dateRange">
                    <div class="form-group">
                        <label>Start Date:</label>
                        <input type="date" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>End Date:</label>
                        <input type="date" name="endDate" required>
                    </div>
                    <button type="submit" class="btn-generate">Generate Report</button>
                </form>
            </div>
            
            <hr>
            
            <!-- Report 2: Rooms Booked Most Frequently -->
            <div class="report-option">
                <div class="report-title">2. Rooms Booked Most Frequently</div>
                <form action="report" method="post">
                    <input type="hidden" name="reportType" value="frequentRooms">
                    <button type="submit" class="btn-generate">Generate Report</button>
                </form>
            </div>
            
            <hr>
            
            <!-- Report 3: Total Revenue Over a Period -->
            <div class="report-option">
                <div class="report-title">3. Total Revenue Over a Period</div>
                <form action="report" method="post">
                    <input type="hidden" name="reportType" value="revenue">
                    <div class="form-group">
                        <label>Start Date:</label>
                        <input type="date" name="startDate" required>
                    </div>
                    <div class="form-group">
                        <label>End Date:</label>
                        <input type="date" name="endDate" required>
                    </div>
                    <button type="submit" class="btn-generate">Generate Report</button>
                </form>
            </div>
            
            <a href="index.jsp" class="btn-back">← Back to Home</a>
        </div>
    </div>
</body>
</html>