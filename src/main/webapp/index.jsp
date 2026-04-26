<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Grand Hotel Management System</title>
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
        }
        
        .hero {
            background: linear-gradient(rgba(0,0,0,0.5), rgba(0,0,0,0.5)), url('https://images.unsplash.com/photo-1566073771259-6a8506099945?ixlib=rb-1.2.1&auto=format&fit=crop&w=1950&q=80');
            background-size: cover;
            background-position: center;
            height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
        }
        
        .container {
            text-align: center;
            color: white;
        }
        
        .hotel-name {
            font-size: 4rem;
            font-weight: bold;
            margin-bottom: 1rem;
            text-shadow: 2px 2px 4px rgba(0,0,0,0.5);
            animation: fadeInDown 1s ease;
        }
        
        .tagline {
            font-size: 1.5rem;
            margin-bottom: 3rem;
            opacity: 0.9;
            animation: fadeInUp 1s ease;
        }
        
        .menu-grid {
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(250px, 1fr));
            gap: 2rem;
            max-width: 1200px;
            margin: 0 auto;
            padding: 2rem;
            animation: fadeIn 1.5s ease;
        }
        
        .menu-card {
            background: white;
            border-radius: 15px;
            padding: 2rem;
            text-decoration: none;
            color: #333;
            transition: transform 0.3s ease, box-shadow 0.3s ease;
            display: block;
        }
        
        .menu-card:hover {
            transform: translateY(-10px);
            box-shadow: 0 20px 40px rgba(0,0,0,0.2);
        }
        
        .menu-icon {
            font-size: 3rem;
            margin-bottom: 1rem;
        }
        
        .menu-title {
            font-size: 1.5rem;
            font-weight: bold;
            margin-bottom: 0.5rem;
            color: #667eea;
        }
        
        .menu-desc {
            color: #666;
            font-size: 0.9rem;
        }
        
        @keyframes fadeInDown {
            from {
                opacity: 0;
                transform: translateY(-30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        @keyframes fadeInUp {
            from {
                opacity: 0;
                transform: translateY(30px);
            }
            to {
                opacity: 1;
                transform: translateY(0);
            }
        }
        
        @keyframes fadeIn {
            from {
                opacity: 0;
            }
            to {
                opacity: 1;
            }
        }
        
        @media (max-width: 768px) {
            .hotel-name {
                font-size: 2rem;
            }
            .tagline {
                font-size: 1rem;
            }
            .menu-grid {
                grid-template-columns: 1fr;
                padding: 1rem;
            }
        }
    </style>
</head>
<body>
    <div class="hero">
        <div class="container">
            <div class="hotel-name">🏨 GRAND MAHAL HOTEL</h1>
            <div class="tagline">Experience Luxury, Comfort, and Excellence</div>
            
            <div class="menu-grid">
                <a href="addReservation" class="menu-card">
                    <div class="menu-icon">📝</div>
                    <div class="menu-title">New Reservation</div>
                    <div class="menu-desc">Book your stay with us</div>
                </a>
                
                <a href="updateReservation" class="menu-card">
                    <div class="menu-icon">✏️</div>
                    <div class="menu-title">Update Booking</div>
                    <div class="menu-desc">Modify your reservation details</div>
                </a>
                
                <a href="deleteReservation" class="menu-card">
                    <div class="menu-icon">❌</div>
                    <div class="menu-title">Cancel Booking</div>
                    <div class="menu-desc">Cancel your reservation</div>
                </a>
                
                <a href="displayReservations" class="menu-card">
                    <div class="menu-icon">📋</div>
                    <div class="menu-title">View All Bookings</div>
                    <div class="menu-desc">See all reservations</div>
                </a>
                
                <a href="report" class="menu-card">
                    <div class="menu-icon">📊</div>
                    <div class="menu-title">Reports</div>
                    <div class="menu-desc">View analytics and reports</div>
                </a>
            </div>
        </div>
    </div>
</body>
</html>