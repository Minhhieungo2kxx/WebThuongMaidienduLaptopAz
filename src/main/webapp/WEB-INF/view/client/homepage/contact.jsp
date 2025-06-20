<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                  <!DOCTYPE html>
                  <html lang="vi">

                  <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Liên hệ FPT Shop Hà Nội - Thông tin và Hỗ trợ</title>

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
                              /* Custom CSS cho trang liên hệ */
                              body {
                                    font-family: 'Open Sans', sans-serif;
                                    background-color: #f8f9fa;
                                    /* Nền nhẹ nhàng */
                              }

                              .contact-hero {
                                    background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('https://via.placeholder.com/1920x400/000000/FFFFFF?text=FPT+Shop+Contact') no-repeat center center;
                                    background-size: cover;
                                    color: #fff;
                                    padding: 80px 0;
                                    text-align: center;
                              }

                              .contact-hero h1 {
                                    font-family: 'Raleway', sans-serif;
                                    font-size: 3rem;
                                    margin-bottom: 15px;
                              }

                              .contact-section {
                                    padding: 60px 0;
                              }

                              .contact-info-card,
                              .contact-form-card {
                                    background-color: #fff;
                                    border-radius: 0.75rem;
                                    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.08);
                                    /* Đổ bóng nhẹ nhưng rõ hơn */
                                    padding: 30px;
                                    height: 100%;
                                    /* Đảm bảo chiều cao đồng nhất */
                              }

                              .contact-info-card h3,
                              .contact-form-card h3 {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 700;
                                    margin-bottom: 25px;
                                    color: #333;
                                    text-align: center;
                              }

                              .contact-info-item {
                                    display: flex;
                                    align-items: center;
                                    margin-bottom: 20px;
                                    color: #555;
                              }

                              .contact-info-item .icon {
                                    font-size: 1.8rem;
                                    color: #7fad39;
                                    /* Màu chủ đạo của theme */
                                    margin-right: 15px;
                                    min-width: 40px;
                                    /* Giữ icon không bị xê dịch */
                                    text-align: center;
                              }

                              .contact-info-item p {
                                    margin-bottom: 0;
                                    font-size: 1.1rem;
                              }

                              .contact-info-item a {
                                    color: #555;
                                    text-decoration: none;
                                    transition: color 0.2s ease;
                              }

                              .contact-info-item a:hover {
                                    color: #7fad39;
                              }

                              .contact-form .form-control {
                                    border-radius: 0.5rem;
                                    padding: 12px 15px;
                                    border: 1px solid #ddd;
                              }

                              .contact-form .form-control:focus {
                                    border-color: #7fad39;
                                    box-shadow: 0 0 0 0.25rem rgba(127, 173, 57, 0.25);
                                    /* Shadow nhẹ khi focus */
                              }

                              .contact-form textarea.form-control {
                                    min-height: 150px;
                                    resize: vertical;
                              }

                              .contact-form .btn-primary {
                                    background-color: #7fad39;
                                    border-color: #7fad39;
                                    padding: 12px 30px;
                                    font-size: 1.1rem;
                                    font-weight: 600;
                                    border-radius: 0.5rem;
                                    transition: background-color 0.3s ease, border-color 0.3s ease, transform 0.2s ease;
                              }

                              .contact-form .btn-primary:hover {
                                    background-color: #6a962d;
                                    border-color: #6a962d;
                                    transform: translateY(-2px);
                              }

                              .map-container {
                                    border-radius: 0.75rem;
                                    overflow: hidden;
                                    /* Đảm bảo iframe không tràn ra khỏi góc bo tròn */
                                    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.08);
                                    margin-top: 40px;
                                    /* Khoảng cách với phần trên */
                                    height: 450px;
                                    /* Chiều cao cố định cho bản đồ */
                              }

                              .map-container iframe {
                                    width: 100%;
                                    height: 100%;
                                    border: 0;
                              }

                              .working-hours-card {
                                    background-color: #fff;
                                    border-radius: 0.75rem;
                                    box-shadow: 0 0.25rem 0.75rem rgba(0, 0, 0, 0.05);
                                    padding: 30px;
                                    margin-top: 30px;
                              }

                              .working-hours-card h4 {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 700;
                                    margin-bottom: 20px;
                                    color: #333;
                                    text-align: center;
                              }

                              .working-hours-card ul {
                                    list-style: none;
                                    padding: 0;
                              }

                              .working-hours-card ul li {
                                    display: flex;
                                    justify-content: space-between;
                                    margin-bottom: 10px;
                                    font-size: 1.05rem;
                                    color: #555;
                                    padding-bottom: 5px;
                                    border-bottom: 1px dashed #eee;
                              }

                              .working-hours-card ul li:last-child {
                                    border-bottom: none;
                                    margin-bottom: 0;
                              }

                              .social-icons {
                                    margin-top: 30px;
                                    text-align: center;
                              }

                              .social-icons a {
                                    display: inline-flex;
                                    /* Đảm bảo căn giữa icon */
                                    justify-content: center;
                                    align-items: center;
                                    width: 45px;
                                    height: 45px;
                                    background-color: #7fad39;
                                    color: #fff;
                                    border-radius: 50%;
                                    /* Hình tròn */
                                    font-size: 1.3rem;
                                    margin: 0 8px;
                                    transition: background-color 0.3s ease, transform 0.2s ease;
                              }

                              .social-icons a:hover {
                                    background-color: #6a962d;
                                    transform: translateY(-3px);
                              }

                              /* Responsive adjustments */
                              @media (max-width: 768px) {
                                    .contact-hero h1 {
                                          font-size: 2.5rem;
                                    }

                                    .contact-section {
                                          padding: 40px 0;
                                    }

                                    .contact-info-card,
                                    .contact-form-card {
                                          margin-bottom: 30px;
                                    }

                                    .map-container {
                                          height: 350px;
                                    }
                              }

                              @media (max-width: 576px) {
                                    .contact-hero h1 {
                                          font-size: 2rem;
                                    }

                                    .contact-info-item {
                                          flex-direction: column;
                                          align-items: flex-start;
                                          text-align: left;
                                    }

                                    .contact-info-item .icon {
                                          margin-right: 0;
                                          margin-bottom: 10px;
                                    }

                                    .contact-info-card,
                                    .contact-form-card {
                                          padding: 20px;
                                    }

                                    .contact-form .btn-primary {
                                          width: 100%;
                                    }

                                    .map-container {
                                          height: 300px;
                                    }
                              }
                        </style>
                  </head>

                  <body>
                        <jsp:include page="../layout/header.jsp" />

                        <div class="container-fluid contact-hero">
                              <div class="container py-5">
                                    <h1 class="display-4 text-white animated slideInDown">Liên Hệ FPT Shop Hà Nội</h1>
                                    <p class="fs-5 text-white-50 animated slideInDown">Chúng tôi luôn sẵn lòng lắng nghe
                                          và hỗ trợ bạn.</p>
                              </div>
                        </div>

                        <div class="container-fluid contact-section">
                              <div class="container">
                                    <div class="row g-4">
                                          <div class="col-lg-6">
                                                <div class="contact-info-card">
                                                      <h3>Thông tin liên hệ</h3>
                                                      <div class="contact-info-item">
                                                            <div class="icon"><i class="fas fa-map-marker-alt"></i>
                                                            </div>
                                                            <p><strong>Địa chỉ:</strong> 264 Cầu Giấy, P. Quan Hoa, Q.
                                                                  Cầu Giấy, Hà Nội</p>
                                                      </div>
                                                      <div class="contact-info-item">
                                                            <div class="icon"><i class="fas fa-phone-alt"></i></div>
                                                            <p><strong>Điện thoại:</strong> <a
                                                                        href="tel:+8418006601">1800 6601</a> (Miễn phí
                                                                  cuộc gọi)</p>
                                                      </div>
                                                      <div class="contact-info-item">
                                                            <div class="icon"><i class="fas fa-envelope"></i></div>
                                                            <p><strong>Email:</strong> <a
                                                                        href="mailto:hotro@fptshop.com.vn">hotro@fptshop.com.vn</a>
                                                            </p>
                                                      </div>
                                                      <div class="contact-info-item">
                                                            <div class="icon"><i class="fas fa-clock"></i></div>
                                                            <div>
                                                                  <p><strong>Giờ làm việc:</strong></p>
                                                                  <ul class="list-unstyled">
                                                                        <li>Thứ 2 - Thứ 7: 08:00 - 21:00</li>
                                                                        <li>Chủ Nhật: 08:30 - 20:00</li>
                                                                  </ul>
                                                            </div>
                                                      </div>

                                                      <div class="social-icons">
                                                            <a href="https://www.facebook.com/FPTShopOnline"
                                                                  target="_blank" aria-label="Facebook"><i
                                                                        class="fab fa-facebook-f"></i></a>
                                                            <a href="https://zalo.me/fptshop" target="_blank"
                                                                  aria-label="Zalo"><i class="fab fa-weibo"></i></a>
                                                            <%-- Weibo icon tạm thay cho Zalo nếu không có icon Zalo
                                                                  --%>
                                                                  <a href="https://www.youtube.com/user/FPTShopOnline"
                                                                        target="_blank" aria-label="YouTube"><i
                                                                              class="fab fa-youtube"></i></a>
                                                                  <a href="#" aria-label="TikTok"><i
                                                                              class="fab fa-tiktok"></i></a>
                                                      </div>
                                                </div>
                                          </div>

                                          <div class="col-lg-6">
                                                <div class="contact-form-card">
                                                      <h3>Gửi tin nhắn cho chúng tôi</h3>
                                                      <form class="contact-form">
                                                            <div class="mb-3">
                                                                  <input type="text" class="form-control" id="name"
                                                                        placeholder="Tên của bạn" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                  <input type="email" class="form-control" id="email"
                                                                        placeholder="Email của bạn" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                  <input type="text" class="form-control" id="subject"
                                                                        placeholder="Tiêu đề" required>
                                                            </div>
                                                            <div class="mb-3">
                                                                  <textarea class="form-control" id="message" rows="5"
                                                                        placeholder="Nội dung tin nhắn"
                                                                        required></textarea>
                                                            </div>
                                                            <div class="d-grid">
                                                                  <button type="submit" class="btn btn-primary">Gửi tin
                                                                        nhắn</button>
                                                            </div>
                                                      </form>
                                                </div>
                                          </div>
                                    </div>

                                    <div class="map-container">
                                          <iframe
                                                src="https://www.google.com/maps/embed?pb=!1m18!1m12!1m3!1d3724.316827014457!2d105.79256427506991!3d21.018317780621453!2m3!1f0!2f0!3f0!3m2!1i1024!2i768!4f13.1!3m3!1m2!1s0x3135ab2f6645366d%3A0xc49d0315a6b0c2a!2zR1BTLTgxIEZQUCBTaG9w!5e0!3m2!1svi!2svn!4v1718601600000!5m2!1svi!2svn"
                                                allowfullscreen="" loading="lazy"
                                                referrerpolicy="no-referrer-when-downgrade"></iframe>
                                    </div>

                                    <%-- <div class="working-hours-card">
                                          <h4>Giờ làm việc</h4>
                                          <ul>
                                                <li><span>Thứ 2 - Thứ 6:</span> <span>08:00 - 21:00</span></li>
                                                <li><span>Thứ 7:</span> <span>08:00 - 21:00</span></li>
                                                <li><span>Chủ Nhật:</span> <span>08:30 - 20:00</span></li>
                                          </ul>
                              </div> --%>

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