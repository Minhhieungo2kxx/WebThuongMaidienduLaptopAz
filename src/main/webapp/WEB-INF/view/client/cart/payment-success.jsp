<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                  <html lang="en">

                  <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Thanh Toán Thành Công!</title>

                        <link rel="preconnect" href="https://fonts.googleapis.com">
                        <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                        <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                              rel="stylesheet">

                        <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                              rel="stylesheet">

                        <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                        <link href="/client/css/style.css" rel="stylesheet">

                        <style>
                              body {
                                    background-color: #f8f9fa;
                                    /* Nền nhẹ nhàng cho toàn trang */
                                    display: flex;
                                    flex-direction: column;
                                    min-height: 100vh;
                                    /* Đảm bảo body chiếm toàn bộ chiều cao màn hình */
                              }

                              .success-container {
                                    flex-grow: 1;
                                    /* Cho phép container chiếm hết không gian còn lại */
                                    display: flex;
                                    align-items: center;
                                    justify-content: center;
                                    padding: 8rem 1rem;
                                    /* Padding cho container */
                              }

                              .success-card {
                                    background-color: #ffffff;
                                    border-radius: 1rem;
                                    /* Bo tròn góc thẻ */
                                    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
                                    /* Đổ bóng nhẹ nhàng, nổi bật */
                                    padding: 3rem 2rem;
                                    text-align: center;
                                    max-width: 600px;
                                    /* Giới hạn chiều rộng để nội dung không bị trải dài quá */
                                    width: 100%;
                              }

                              .success-icon {
                                    font-size: 4rem;
                                    /* Kích thước biểu tượng lớn */
                                    color: #28a745;
                                    /* Màu xanh lá cây của success */
                                    margin-bottom: 1.5rem;
                                    animation: bounceIn 0.8s ease-out;
                                    /* Hiệu ứng động khi xuất hiện */
                              }

                              .success-card h2 {
                                    color: #28a745;
                                    /* Màu xanh lá cây */
                                    font-weight: 700;
                                    margin-bottom: 1rem;
                                    font-size: 2.2rem;
                              }

                              .success-card p {
                                    color: #6c757d;
                                    font-size: 1.1rem;
                                    margin-bottom: 1.5rem;
                                    line-height: 1.6;
                              }

                              .order-id {
                                    font-weight: bold;
                                    color: #343a40;
                                    font-size: 1.2rem;
                              }

                              .btn-home {
                                    background-color: #7fad39;
                                    /* Màu chủ đạo của theme bạn (giống màu header) */
                                    border-color: #7fad39;
                                    color: #fff;
                                    padding: 0.9rem 2rem;
                                    font-size: 1.1rem;
                                    font-weight: 600;
                                    border-radius: 0.5rem;
                                    text-decoration: none;
                                    transition: background-color 0.3s ease, border-color 0.3s ease, transform 0.2s ease;
                              }

                              .btn-home:hover {
                                    background-color: #6a962d;
                                    /* Màu đậm hơn khi hover */
                                    border-color: #6a962d;
                                    color: #fff;
                                    /* Đảm bảo màu chữ vẫn trắng */
                                    transform: translateY(-2px);
                                    /* Hiệu ứng nhấc nhẹ nút lên */
                              }

                              /* Animation for success icon */
                              @keyframes bounceIn {
                                    0% {
                                          transform: scale(0.1);
                                          opacity: 0;
                                    }

                                    60% {
                                          transform: scale(1.1);
                                          opacity: 1;
                                    }

                                    100% {
                                          transform: scale(1);
                                    }
                              }

                              /* Responsive adjustments */
                              @media (max-width: 576px) {
                                    .success-card {
                                          padding: 2rem 1rem;
                                    }

                                    .success-icon {
                                          font-size: 3rem;
                                    }

                                    .success-card h2 {
                                          font-size: 1.8rem;
                                    }

                                    .success-card p {
                                          font-size: 1rem;
                                    }

                                    .btn-home {
                                          padding: 0.75rem 1.5rem;
                                          font-size: 1rem;
                                    }
                              }
                        </style>
                  </head>

                  <body>

                        <div id="spinner"
                              class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                              <div class="spinner-grow text-primary" role="status"></div>
                        </div>
                        <jsp:include page="../layout/header.jsp" />

                        <div class="success-container">
                              <div class="success-card">
                                    <i class="fas fa-check-circle success-icon"></i>
                                    <h2>Thanh Toán Thành Công!</h2>
                                    <p>Cảm ơn bạn đã đặt hàng tại cửa hàng của chúng tôi. Đơn hàng của bạn đã được xác
                                          nhận và đang được xử lý.</p>

                                    <c:if test="${not empty orderId}">
                                          <p class="order-id mb-3">Mã đơn hàng của bạn là: <strong>${orderId}</strong>
                                          </p>
                                    </c:if>

                                    <a href="/" class="btn btn-home">
                                          <i class="fas fa-home me-2"></i> Quay về Trang chủ
                                    </a>
                              </div>
                        </div>

                        <jsp:include page="../layout/footer.jsp" />

                        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                        <script
                              src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                        <script src="/client/lib/easing/easing.min.js"></script>
                        <script src="/client/lib/waypoints/waypoints.min.js"></script>
                        <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                        <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
                        <script src="/client/js/main.js"></script>

                  </body>

                  </html>