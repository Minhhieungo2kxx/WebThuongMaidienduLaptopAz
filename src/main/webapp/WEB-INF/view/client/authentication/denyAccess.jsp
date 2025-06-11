<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>Truy cập bị từ chối - Ecornomere</title>

                    <link rel="preconnect" href="https://fonts.googleapis.com">
                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                    <link
                        href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                        rel="stylesheet">

                    <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                        rel="stylesheet">

                    <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                    <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                    <link href="/client/css/style.css" rel="stylesheet">

                    <style>
                        body {
                            display: flex;
                            justify-content: center;
                            align-items: center;
                            min-height: 100vh;
                            background-color: #f8f9fa;
                            /* Màu nền nhẹ nhàng */
                        }

                        .access-denied-container {
                            text-align: center;
                            background-color: #ffffff;
                            padding: 40px;
                            border-radius: 10px;
                            box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                            max-width: 500px;
                            width: 90%;
                        }

                        .access-denied-container h1 {
                            color: #dc3545;
                            /* Màu đỏ cảnh báo */
                            margin-bottom: 20px;
                            font-size: 2.5rem;
                        }

                        .access-denied-container p {
                            color: #6c757d;
                            font-size: 1.1rem;
                            margin-bottom: 30px;
                        }

                        .access-denied-container .btn-home {
                            background-color: #007bff;
                            /* Màu xanh của Bootstrap primary */
                            color: #fff;
                            padding: 12px 25px;
                            border-radius: 5px;
                            text-decoration: none;
                            font-weight: 600;
                            transition: background-color 0.3s ease;
                        }

                        .access-denied-container .btn-home:hover {
                            background-color: #0056b3;
                        }

                        .access-denied-container .icon {
                            font-size: 5rem;
                            color: #dc3545;
                            margin-bottom: 20px;
                        }
                    </style>
                </head>

                <body>
                    <div class="access-denied-container">
                        <div class="icon">
                            <i class="fas fa-exclamation-triangle"></i>
                        </div>
                        <h1>Truy cập bị từ chối!</h1>
                        <p>Bạn không có quyền truy cập vào khu vực quản trị. Vui lòng liên hệ quản trị viên nếu bạn tin
                            rằng đây là một lỗi.</p>
                        <a href="/" class="btn-home">
                            <i class="fas fa-home me-2"></i>Quay về trang chủ
                        </a>
                    </div>

                    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                    <script src="/client/lib/easing/easing.min.js"></script>
                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                    <script src="/client/js/main.js"></script>
                </body>

                </html>