<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                  <!DOCTYPE html>
                  <html lang="vi">

                  <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Về FPT Shop Hà Nội - Chuyên gia công nghệ của bạn</title>

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
                              /* Custom CSS cho trang giới thiệu */
                              body {
                                    font-family: 'Open Sans', sans-serif;
                                    background-color: #f8f9fa;
                                    /* Nền nhẹ nhàng */
                              }

                              .about-hero {
                                    background: linear-gradient(rgba(0, 0, 0, 0.6), rgba(0, 0, 0, 0.6)), url('https://via.placeholder.com/1920x400/000000/FFFFFF?text=FPT+Shop+Storefront') no-repeat center center;
                                    /* Thay bằng ảnh mặt tiền cửa hàng FPT Shop thực tế */
                                    background-size: cover;
                                    color: #fff;
                                    padding: 80px 0;
                                    text-align: center;
                              }

                              .about-hero h1 {
                                    font-family: 'Raleway', sans-serif;
                                    font-size: 3.5rem;
                                    margin-bottom: 15px;
                                    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
                                    /* Thêm bóng cho chữ */
                              }

                              .about-hero p {
                                    font-size: 1.2rem;
                                    max-width: 800px;
                                    margin: 0 auto;
                              }

                              .section-padding {
                                    padding: 60px 0;
                              }

                              .section-title {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 700;
                                    color: #333;
                                    margin-bottom: 40px;
                                    text-align: center;
                                    position: relative;
                              }

                              .section-title::after {
                                    content: '';
                                    position: absolute;
                                    bottom: -10px;
                                    left: 50%;
                                    transform: translateX(-50%);
                                    width: 80px;
                                    height: 3px;
                                    background-color: #7fad39;
                                    /* Màu chủ đạo */
                                    border-radius: 5px;
                              }

                              .about-content img {
                                    border-radius: 1rem;
                                    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
                                    transition: transform 0.3s ease;
                              }

                              .about-content img:hover {
                                    transform: scale(1.02);
                              }

                              .about-text h4 {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 600;
                                    color: #444;
                                    margin-bottom: 20px;
                              }

                              .about-text p {
                                    color: #666;
                                    line-height: 1.7;
                                    margin-bottom: 15px;
                                    font-size: 1.05rem;
                              }

                              .feature-box {
                                    text-align: center;
                                    padding: 30px 20px;
                                    background-color: #fff;
                                    border-radius: 0.75rem;
                                    box-shadow: 0 0.25rem 0.75rem rgba(0, 0, 0, 0.05);
                                    transition: transform 0.3s ease, box-shadow 0.3s ease;
                                    height: 100%;
                                    /* Đảm bảo chiều cao đồng nhất */
                              }

                              .feature-box:hover {
                                    transform: translateY(-10px);
                                    box-shadow: 0 0.75rem 2rem rgba(0, 0, 0, 0.1);
                              }

                              .feature-box .icon {
                                    font-size: 3rem;
                                    color: #7fad39;
                                    margin-bottom: 20px;
                              }

                              .feature-box h5 {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 700;
                                    color: #333;
                                    margin-bottom: 15px;
                              }

                              .feature-box p {
                                    color: #777;
                                    font-size: 0.95rem;
                              }

                              .team-member {
                                    text-align: center;
                                    margin-bottom: 30px;
                              }

                              .team-member img {
                                    width: 150px;
                                    height: 150px;
                                    border-radius: 50%;
                                    object-fit: cover;
                                    border: 5px solid #fff;
                                    box-shadow: 0 0.5rem 1.5rem rgba(0, 0, 0, 0.1);
                                    margin-bottom: 15px;
                                    transition: transform 0.3s ease;
                              }

                              .team-member img:hover {
                                    transform: translateY(-5px);
                              }

                              .team-member h5 {
                                    font-family: 'Raleway', sans-serif;
                                    font-weight: 700;
                                    color: #333;
                                    margin-bottom: 5px;
                              }

                              .team-member p {
                                    color: #777;
                                    font-size: 0.95rem;
                              }

                              .call-to-action {
                                    background-color: #7fad39;
                                    color: #fff;
                                    padding: 50px 0;
                                    text-align: center;
                                    border-radius: 1rem;
                                    margin: 60px 0;
                              }

                              .call-to-action h2 {
                                    font-family: 'Raleway', sans-serif;
                                    font-size: 2.5rem;
                                    margin-bottom: 20px;
                              }

                              .call-to-action p {
                                    font-size: 1.15rem;
                                    margin-bottom: 30px;
                                    max-width: 800px;
                                    margin-left: auto;
                                    margin-right: auto;
                              }

                              .call-to-action .btn-light {
                                    background-color: #fff;
                                    color: #7fad39;
                                    padding: 12px 30px;
                                    font-size: 1.1rem;
                                    font-weight: 600;
                                    border-radius: 0.5rem;
                                    transition: background-color 0.3s ease, transform 0.2s ease;
                              }

                              .call-to-action .btn-light:hover {
                                    background-color: #eee;
                                    transform: translateY(-3px);
                              }

                              /* Responsive adjustments */
                              @media (max-width: 768px) {
                                    .about-hero h1 {
                                          font-size: 2.8rem;
                                    }

                                    .section-padding {
                                          padding: 40px 0;
                                    }

                                    .about-content img {
                                          margin-bottom: 30px;
                                    }

                                    .call-to-action h2 {
                                          font-size: 2rem;
                                    }
                              }

                              @media (max-width: 576px) {
                                    .about-hero h1 {
                                          font-size: 2.2rem;
                                    }

                                    .section-title {
                                          margin-bottom: 30px;
                                    }

                                    .feature-box {
                                          margin-bottom: 30px;
                                    }

                                    .call-to-action {
                                          background-color: #7fad39;
                                          color: #fff;
                                          padding: 50px 0;
                                          text-align: center;
                                          border-radius: 1rem;
                                          margin: 60px auto;
                                          /* Thay đổi ở đây: 60px cho top/bottom, auto cho left/right */
                                          max-width: 1140px;
                                          /* Thêm max-width nếu bạn muốn nó không chiếm toàn bộ chiều rộng trên màn hình lớn */
                                    }

                                    /* Các CSS khác của .call-to-action giữ nguyên */
                              }
                        </style>
                  </head>

                  <body>
                        <jsp:include page="../layout/header.jsp" />

                        <div class="container-fluid about-hero">
                              <div class="container py-5">
                                    <h1 class="display-3 text-white animated slideInDown">FPT Shop Hà Nội: Đồng hành
                                          cùng đam mê công nghệ</h1>
                                    <p class="fs-5 text-white-50 animated slideInDown">Khám phá câu chuyện về FPT Shop –
                                          Nơi công nghệ kết nối với cuộc sống của bạn.</p>
                              </div>
                        </div>

                        <div class="container-fluid section-padding">
                              <div class="container">
                                    <h2 class="section-title">Về Chúng Tôi</h2>
                                    <div class="row g-5 align-items-center about-content">
                                          <div class="col-lg-6">
                                                <img src="https://cdn2.fptshop.com.vn/unsafe/800x0/gallery1_fd1d8856d1.png"
                                                      class="img-fluid rounded" alt="FPT Shop Interior">
                                          </div>
                                          <div class="col-lg-6 about-text">
                                                <h4>Chào mừng đến với FPT Shop Hà Nội</h4>
                                                <p>Là một phần không thể thiếu của hệ thống FPT Shop rộng khắp Việt Nam,
                                                      các cửa hàng FPT Shop tại Hà Nội tự hào là điểm đến tin cậy hàng
                                                      đầu cho mọi tín đồ công nghệ. Từ những ngày đầu tiên, chúng tôi đã
                                                      không ngừng nỗ lực mang đến những sản phẩm công nghệ **chính hãng,
                                                      đa dạng,** cùng dịch vụ **chăm sóc khách hàng tận tâm** nhất.</p>
                                                <p>Với tầm nhìn **"Trở thành nhà bán lẻ sản phẩm công nghệ hàng đầu,
                                                      mang lại giá trị tốt nhất cho khách hàng"**, chúng tôi cam kết
                                                      mang đến những trải nghiệm mua sắm vượt trội. FPT Shop Hà Nội
                                                      không chỉ là nơi bạn tìm thấy smartphone, laptop, phụ kiện công
                                                      nghệ mới nhất mà còn là trung tâm tư vấn, hỗ trợ kỹ thuật chuyên
                                                      nghiệp, giúp bạn đưa ra lựa chọn tối ưu và khai thác trọn vẹn tiềm
                                                      năng công nghệ.</p>
                                                <p>Chúng tôi hiểu rằng công nghệ thay đổi mỗi ngày, vì vậy FPT Shop Hà
                                                      Nội luôn cập nhật xu hướng, đa dạng hóa sản phẩm và không ngừng
                                                      nâng cao chất lượng dịch vụ để luôn là người bạn đồng hành đáng
                                                      tin cậy của bạn trên hành trình khám phá thế giới số.</p>
                                          </div>
                                    </div>
                              </div>
                        </div>

                        <div class="container-fluid bg-light section-padding">
                              <div class="container">
                                    <h2 class="section-title">Tại Sao Chọn FPT Shop Hà Nội?</h2>
                                    <div class="row g-4">
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-check-circle"></i></div>
                                                      <h5>Sản Phẩm Chính Hãng</h5>
                                                      <p>Chúng tôi cam kết 100% sản phẩm là hàng chính hãng, có nguồn
                                                            gốc rõ ràng, bảo hành uy tín.</p>
                                                </div>
                                          </div>
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-handshake"></i></div>
                                                      <h5>Dịch Vụ Tận Tâm</h5>
                                                      <p>Đội ngũ nhân viên chuyên nghiệp, nhiệt tình luôn sẵn sàng tư
                                                            vấn và hỗ trợ bạn mọi lúc.</p>
                                                </div>
                                          </div>
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-truck-fast"></i></div>
                                                      <h5>Giao Hàng Nhanh Chóng</h5>
                                                      <p>Mua sắm online tiện lợi, giao hàng thần tốc ngay trong ngày tại
                                                            Hà Nội.</p>
                                                </div>
                                          </div>
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-medal"></i></div>
                                                      <h5>Bảo Hành Chuyên Nghiệp</h5>
                                                      <p>Hệ thống bảo hành rộng khắp, quy trình nhanh gọn, đảm bảo quyền
                                                            lợi khách hàng tối đa.</p>
                                                </div>
                                          </div>
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-tags"></i></div>
                                                      <h5>Ưu Đãi Hấp Dẫn</h5>
                                                      <p>Thường xuyên có các chương trình khuyến mãi, giảm giá sốc, trả
                                                            góp 0% hấp dẫn.</p>
                                                </div>
                                          </div>
                                          <div class="col-lg-4 col-md-6">
                                                <div class="feature-box">
                                                      <div class="icon"><i class="fas fa-tools"></i></div>
                                                      <h5>Hỗ Trợ Kỹ Thuật</h5>
                                                      <p>Đội ngũ kỹ thuật viên tay nghề cao, hỗ trợ cài đặt, sửa chữa
                                                            nhanh chóng, hiệu quả.</p>
                                                </div>
                                          </div>
                                    </div>
                              </div>
                        </div>

                        <div class="container call-to-action mx-auto" bis_skin_checked="1">
                              <h2>Sẵn Sàng Khám Phá Công Nghệ Mới?</h2>
                              <p>Ghé thăm FPT Shop Hà Nội ngay hôm nay để trải nghiệm không gian mua sắm hiện đại và
                                    nhận tư vấn chuyên nghiệp từ đội ngũ của chúng tôi!</p>
                              <a href="/" class="btn btn-light">Tìm Cửa Hàng Gần Nhất</a>
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