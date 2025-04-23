<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

    

  <!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Best Buy Clone (Simplified)</title>
    <style>
        /* Basic Reset */
        * {
            margin: 0;
            padding: 0;
            box-sizing: border-box;
            font-family: sans-serif; /* Using a common sans-serif font */
        }

        body {
            background-color: #f0f0f0; /* Light grey background */
            color: #333;
        }

        a {
            text-decoration: none;
            color: inherit; /* Inherit color from parent */
        }

        ul {
            list-style: none;
        }

        /* Header */
        .header-top {
            background-color: #0046be; /* Best Buy Blue */
            color: white;
            padding: 10px 20px;
            display: flex;
            justify-content: space-between;
            align-items: center;
            font-size: 0.9em;
        }

        .header-top .logo img {
            height: 30px; /* Adjust logo size */
        }

        .header-top .search-bar {
            flex-grow: 1;
            margin: 0 20px;
            max-width: 500px; /* Limit search bar width */
        }

        .header-top .search-bar input[type="text"] {
            width: calc(100% - 40px); /* Adjust width considering button/icon */
            padding: 8px 10px;
            border: none;
            border-radius: 4px 0 0 4px;
            font-size: 1em;
        }

        .header-top .search-bar button {
            padding: 8px 15px;
            background-color: #ffc600; /* Yellow */
            border: none;
            border-radius: 0 4px 4px 0;
            cursor: pointer;
            font-size: 1em;
        }

        .header-top .header-links a,
        .header-top .account-links a,
        .header-top .cart a {
            margin-left: 15px;
            opacity: 0.9;
            transition: opacity 0.2s ease;
        }

        .header-top .header-links a:hover,
        .header-top .account-links a:hover,
        .header-top .cart a:hover {
            opacity: 1;
        }

        /* Main Navigation */
        .main-nav {
            background-color: #0053a0; /* Slightly different blue */
            color: white;
            padding: 10px 20px;
            display: flex;
            gap: 20px;
            font-size: 1em;
        }

        .main-nav a {
            opacity: 0.9;
            transition: opacity 0.2s ease;
        }

        .main-nav a:hover {
            opacity: 1;
        }

        /* Banner/Hero Section */
        .hero-message {
            background-color: #ffc600; /* Yellow */
            text-align: center;
            padding: 10px 0;
            font-weight: bold;
            font-size: 1.1em;
        }

        .hero-content {
            display: flex;
            background-color: #0046be; /* Best Buy Blue */
            color: white;
            padding: 20px;
            min-height: 300px; /* Minimum height */
        }

        .hero-promo {
            flex: 1;
            background-color: #0046be; /* Blue background */
            padding: 20px;
            position: relative; /* For clock positioning */
            overflow: hidden; /* Hide overflowing clock */
        }

        .hero-promo h2 {
            font-size: 2.5em;
            margin-bottom: 10px;
            color: #ffc600; /* Yellow text */
        }

        .hero-promo p {
            font-size: 1.2em;
            margin-bottom: 20px;
        }

        .hero-promo .clock-graphic {
            position: absolute;
            top: 20px; /* Adjust position */
            right: 20px; /* Adjust position */
            width: 100px; /* Adjust size */
            height: 100px; /* Adjust size */
            background-color: #ffc600; /* Yellow background */
            border-radius: 50%; /* Circle */
            display: flex;
            justify-content: center;
            align-items: center;
            font-size: 3em;
            color: #0046be; /* Blue text */
            transform: rotate(-20deg); /* Rotate slightly */
            /* Basic clock hand representation */
            &::before, &::after {
                content: '';
                position: absolute;
                background-color: #0046be;
            }
            &::before { /* Hour hand */
                width: 6px;
                height: 30px;
                top: 20px;
                left: 47px;
                transform-origin: bottom center;
                transform: rotate(45deg);
            }
            &::after { /* Minute hand */
                 width: 4px;
                height: 40px;
                top: 10px;
                left: 48px;
                transform-origin: bottom center;
                transform: rotate(150deg);
            }
        }


        .hero-categories {
            flex: 2;
            padding: 20px;
            display: grid;
            grid-template-columns: repeat(auto-fit, minmax(120px, 1fr)); /* Responsive grid */
            gap: 15px;
        }

        .category-item {
            text-align: center;
            color: white;
            padding: 15px 10px;
            border: 1px solid rgba(255, 255, 255, 0.3); /* Light border */
            border-radius: 5px;
            transition: background-color 0.2s ease;
            cursor: pointer;
        }

        .category-item:hover {
            background-color: rgba(255, 255, 255, 0.1); /* Slight hover effect */
        }

        .category-item .icon {
            font-size: 2em; /* Placeholder icon size */
            margin-bottom: 5px;
        }

        /* Add some spacing for main content area below hero */
        .main-content {
            padding: 20px;
        }

        /* Responsive Adjustments */
        @media (max-width: 768px) {
            .header-top {
                flex-direction: column;
                align-items: flex-start;
            }

            .header-top .search-bar {
                margin: 10px 0;
                width: 100%;
            }

            .header-top .search-links {
                display: flex;
                flex-wrap: wrap;
                gap: 10px;
            }

            .header-top .account-links {
                 margin-top: 10px;
                 width: 100%;
                 display: flex;
                 justify-content: space-around; /* Distribute links */
            }

            .header-top .cart {
                margin-top: 10px;
                width: 100%;
                text-align: center;
            }


            .main-nav {
                flex-direction: column;
                gap: 10px;
            }

            .hero-content {
                flex-direction: column;
            }

            .hero-promo,
            .hero-categories {
                flex: none; /* Remove flex grow on small screens */
                width: 100%;
                padding: 15px;
            }

             .hero-promo .clock-graphic {
                 position: static; /* Position normally within flow */
                 margin: 20px auto; /* Center the clock */
             }

            .hero-categories {
                grid-template-columns: repeat(auto-fit, minmax(100px, 1fr)); /* Smaller grid items */
                gap: 10px;
            }
        }

    </style>
</head>
<body>

    <header>
        <div class="header-top">
            <div class="logo">
                <img src="https://via.placeholder.com/100x30?text=Best+Buy" alt="Best Buy Logo">
            </div>
            <div class="search-bar">
                <form action="#">
                    <input type="text" placeholder="Search Best Buy">
                    <button type="submit">Q</button> </form>
            </div>
            <div class="header-links">
                <a href="#">Top Deals</a>
                <a href="#">Deal of the Day</a>
                <a href="#">Credit Cards</a>
                <a href="#">For Your Business</a>
                <a href="#">Gift Cards</a>
                <a href="#">Gift Ideas</a>
            </div>
            <div class="account-links">
                 <a href="#">Account</a>
                 <a href="#">Shopping History</a>
                 <a href="#">Order Status</a>
                 <a href="#">Saved Items</a>
            </div>
            <div class="cart">
                <a href="#">🛒 Cart</a> </div>
        </div>

        <nav class="main-nav">
            <a href="#">Products</a>
            <a href="#">Brands</a>
            <a href="#">Deals</a>
            <a href="#">Services</a>
        </nav>
    </header>

    <main>
        <section class="hero-message">
            Ready in one hour with Store Pickup.
        </section>

        <section class="hero-content">
            <div class="hero-promo">
                <h2>Last-Second Savings Event.</h2>
                <p>5 days of great deals</p>
                <div class="clock-graphic">
                    </div>
            </div>
            <div class="hero-categories">
                <div class="category-item">
                    <div class="icon">📺</div>
                    TV
                </div>
                 <div class="category-item">
                    <div class="icon">💻</div>
                    Laptops & Computers
                </div>
                 <div class="category-item">
                    <div class="icon">📱</div>
                    Tablets & Readers
                </div>
                 <div class="category-item">
                    <div class="icon">🎮</div>
                    Video Games, Consoles & VR
                </div>
                 <div class="category-item">
                    <div class="icon">🧺</div>
                    Major Appliances
                </div>
                 <div class="category-item">
                    <div class="icon">📱</div>
                    Cell Phones
                </div>
                 <div class="category-item">
                    <div class="icon">🔊</div>
                    Sound Bars, Speakers & Accessories
                </div>
                 <div class="category-item">
                    <div class="icon">💿</div>
                    Streaming Media & Blu-ray Players
                </div>
                <div class="category-item">
                    <div class="icon">🎧</div>
                    Headphones & Earbuds
                </div>
                 <div class="category-item">
                    <div class="icon">💻</div>
                    PC Gaming
                </div>
                 <div class="category-item">
                    <div class="icon">🏠</div>
                    Smart Home, Security & Wi-Fi
                </div>
                 <div class="category-item">
                    <div class="icon">🎬</div>
                    Movies, TV Shows & Music
                </div>
                </div>
        </section>

        <section class="main-content">
            <h2>More Content Below</h2>
            <p>This area can be used for other product listings, promotions, etc.</p>
            </section>

    </main>

    <footer>
        <div style="text-align: center; padding: 20px; background-color: #333; color: white; margin-top: 30px;">
            &copy; 2023 Best Buy Clone
        </div>
    </footer>

    <script>
        // Basic JavaScript for potential future use or simple interactions

        // Example: Log a message when the page loads
        console.log("Page loaded: Best Buy Clone (Simplified)");

        // Example: Add a simple hover effect using JS (optional, CSS is preferred for this)
        const categoryItems = document.querySelectorAll('.category-item');
        categoryItems.forEach(item => {
            item.addEventListener('mouseover', () => {
                item.style.backgroundColor = 'rgba(255, 255, 255, 0.2)'; // Brighter hover
            });
            item.addEventListener('mouseout', () => {
                 item.style.backgroundColor = 'rgba(255, 255, 255, 0.1)'; // Back to default hover
            });
        });

         // Example: Add a focus effect on the search input (optional)
         const searchInput = document.querySelector('.search-bar input[type="text"]');
         searchInput.addEventListener('focus', () => {
             searchInput.style.outline = '2px solid #ffc600';
         });
          searchInput.addEventListener('blur', () => {
             searchInput.style.outline = 'none';
         });

    </script>

</body>
</html>