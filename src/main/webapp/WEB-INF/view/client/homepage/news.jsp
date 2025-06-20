<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                  <!DOCTYPE html>
                  <html lang="vi">

                  <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Tin tức FPT Shop Hà Nội - Cập nhật mới nhất</title>

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
                              /* Custom CSS cho trang tin tức */
                              .hero-section {
                                    background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('https://cdn.tgdd.vn/Files/2021/05/27/1356073/fpt-shop-la-gi-co-tot-khong-cac-san-pham-dich-vu-uu-dai-202105271408103381.jpg') no-repeat center center;
                                    background-size: cover;
                                    color: #fff;
                                    padding: 80px 0;
                                    margin-bottom: 30px;
                              }

                              .hero-section h1 {
                                    font-weight: 800;
                                    text-shadow: 2px 2px 4px rgba(0, 0, 0, 0.5);
                              }

                              .hero-section .btn-home {
                                    background-color: #7fad39;
                                    /* Màu xanh lá đặc trưng của FPT */
                                    border-color: #7fad39;
                                    color: #fff;
                                    padding: 12px 30px;
                                    font-size: 1.1rem;
                                    border-radius: 50px;
                                    transition: all 0.3s ease;
                              }

                              .hero-section .btn-home:hover {
                                    background-color: #6a962f;
                                    border-color: #6a962f;
                                    transform: translateY(-3px);
                                    box-shadow: 0 4px 8px rgba(0, 0, 0, 0.2);
                              }

                              .news-section {
                                    padding: 30px 0;
                              }

                              .news-card {
                                    border: 1px solid #e0e0e0;
                                    border-radius: 8px;
                                    overflow: hidden;
                                    box-shadow: 0 4px 12px rgba(0, 0, 0, 0.08);
                                    transition: transform 0.3s ease, box-shadow 0.3s ease;
                                    height: 100%;
                                    /* Đảm bảo các card có chiều cao bằng nhau trong cùng một hàng */
                                    display: flex;
                                    flex-direction: column;
                              }

                              .news-card:hover {
                                    transform: translateY(-5px);
                                    box-shadow: 0 8px 20px rgba(0, 0, 0, 0.15);
                              }

                              .news-card img {
                                    width: 100%;
                                    height: 200px;
                                    /* Chiều cao cố định cho ảnh để đồng bộ */
                                    object-fit: cover;
                                    border-bottom: 1px solid #eee;
                              }

                              .news-card-body {
                                    padding: 20px;
                                    flex-grow: 1;
                                    /* Cho phép phần nội dung giãn nở */
                                    display: flex;
                                    flex-direction: column;
                                    justify-content: space-between;
                              }

                              .news-card-title {
                                    font-size: 1.3rem;
                                    font-weight: 600;
                                    color: #333;
                                    margin-bottom: 10px;
                                    line-height: 1.4;
                                    max-height: 2.8em;
                                    /* Giới hạn chiều cao tiêu đề cho 2 dòng */
                                    overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 2;
                                    -webkit-box-orient: vertical;
                              }

                              .news-card-text {
                                    font-size: 0.95rem;
                                    color: #666;
                                    line-height: 1.6;
                                    margin-bottom: 15px;
                                    max-height: 4.8em;
                                    /* Giới hạn chiều cao mô tả cho 3 dòng */
                                    overflow: hidden;
                                    text-overflow: ellipsis;
                                    display: -webkit-box;
                                    -webkit-line-clamp: 3;
                                    -webkit-box-orient: vertical;
                              }

                              .news-card-meta {
                                    font-size: 0.85rem;
                                    color: #888;
                                    margin-top: auto;
                                    /* Đẩy meta xuống cuối */
                                    padding-top: 10px;
                                    border-top: 1px dashed #eee;
                              }

                              .news-card-meta span {
                                    display: flex;
                                    align-items: center;
                              }

                              .news-card-meta i {
                                    margin-right: 5px;
                                    color: #7fad39;
                              }

                              .btn-read-more {
                                    display: inline-flex;
                                    align-items: center;
                                    background-color: #7fad39;
                                    color: #fff;
                                    padding: 8px 18px;
                                    border-radius: 5px;
                                    text-decoration: none;
                                    transition: background-color 0.3s ease;
                              }

                              .btn-read-more:hover {
                                    background-color: #6a962f;
                                    color: #fff;
                              }

                              .btn-read-more i {
                                    margin-left: 8px;
                                    transition: margin-left 0.3s ease;
                              }

                              .btn-read-more:hover i {
                                    margin-left: 12px;
                              }

                              /* Sidebar */
                              .sidebar {
                                    background-color: #f8f9fa;
                                    padding: 20px;
                                    border-radius: 8px;
                                    box-shadow: 0 2px 8px rgba(0, 0, 0, 0.05);
                              }

                              .sidebar h4 {
                                    font-size: 1.4rem;
                                    color: #333;
                                    margin-bottom: 20px;
                                    padding-bottom: 10px;
                                    border-bottom: 2px solid #7fad39;
                              }

                              .sidebar ul {
                                    list-style: none;
                                    padding: 0;
                                    margin: 0;
                              }

                              .sidebar ul li {
                                    margin-bottom: 10px;
                              }

                              .sidebar ul li a {
                                    color: #555;
                                    text-decoration: none;
                                    display: flex;
                                    align-items: center;
                                    padding: 5px 0;
                                    transition: color 0.3s ease, transform 0.3s ease;
                              }

                              .sidebar ul li a:hover {
                                    color: #7fad39;
                                    transform: translateX(5px);
                              }

                              .sidebar ul li i {
                                    margin-right: 8px;
                                    color: #7fad39;
                              }

                              /* Đảm bảo các mục phân trang nằm trên một hàng */
                              .pagination {
                                    display: flex;
                                    /* Sử dụng flexbox */
                                    flex-wrap: wrap;
                                    /* Cho phép xuống dòng nếu quá dài */
                                    padding-left: 0;
                                    list-style: none;
                                    border-radius: 0.3rem;
                                    /* Theo Bootstrap */
                              }

                              .page-item {
                                    display: list-item;
                                    /* Mặc định của li, nhưng để đảm bảo */
                              }

                              /* Đảm bảo link và span trong page-item hiển thị đúng */
                              .page-link {
                                    display: block;
                                    /* Hoặc inline-block nếu cần */
                              }
                        </style>
                  </head>

                  <body>
                        <jsp:include page="../layout/header.jsp" />

                        <div class="container-fluid hero-section">
                              <div class="container text-center py-5">
                                    <h1 class="display-3 text-white mb-4 animated slideInDown">Cập Nhật Tin Tức FPT Shop
                                          Hà Nội</h1>
                                    <p class="fs-5 text-white-50 mb-0 animated slideInDown">Những thông tin mới nhất về
                                          sản phẩm, khuyến mãi và sự kiện tại các cửa hàng FPT Shop Hà Nội, mang đến cho
                                          bạn trải nghiệm công nghệ tuyệt vời.</p>
                                    <a href="#news-grid" class="btn btn-home mt-4 animated slideInUp">Xem tin mới
                                          nhất</a>
                              </div>
                        </div>

                        <div class="container-fluid news-section">
                              <div class="container">
                                    <div class="row g-4">
                                          <div class="col-lg-8">
                                                <div class="row g-4" id="news-grid">
                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://cdn2.fptshop.com.vn/unsafe/1920x0/filters:format(webp):quality(75)/2020_1_3_637136495671711106_tt-laptop-q9-6.JPG"
                                                                        class="img-fluid"
                                                                        alt="Khuyến mãi Laptop Gaming">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">FPT Shop Hà Nội:
                                                                              "Tái định nghĩa" chơi game với laptop
                                                                              gaming giảm giá cực sốc!</h5>
                                                                        <p class="news-card-text">Đừng bỏ lỡ cơ hội sở
                                                                              hữu những chiếc laptop gaming mạnh mẽ nhất
                                                                              với ưu đãi chưa từng có tại FPT Shop Hà
                                                                              Nội. Hiệu năng đỉnh cao, thiết kế ấn tượng
                                                                              đang chờ đón bạn!</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 1568 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://i1-vnexpress.vnecdn.net/2025/05/26/z6640560454139-1f834208a47e3a5-8736-6395-1748274854.jpg?w=1020&h=0&q=100&dpr=1&fit=crop&s=QBXAHY9q8Jee6pwHX5V_GA"
                                                                        class="img-fluid"
                                                                        alt="Mùa hè sôi động với điện thoại">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">Mùa hè rực lửa:
                                                                              Smartphone giảm giá bùng nổ tại FPT Shop
                                                                              Hà Nội!</h5>
                                                                        <p class="news-card-text">Sắm ngay dế yêu mới
                                                                              với hàng ngàn ưu đãi hấp dẫn, từ iPhone
                                                                              đời mới đến Android cao cấp, tất cả đều có
                                                                              tại FPT Shop Hà Nội. Số lượng có hạn,
                                                                              nhanh tay kẻo lỡ!</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 2102 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://i1-vnexpress.vnecdn.net/2025/05/26/z6640560452972-a53ff7f35967b21-9602-1753-1748274854.jpg?w=1020&h=0&q=100&dpr=1&fit=crop&s=5579jQt60-7qH6FB2fwJtw"
                                                                        class="img-fluid"
                                                                        alt="Khai trương cửa hàng mới">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">Chính thức khai
                                                                              trương FPT Shop Hà Nội chi nhánh mới: Ưu
                                                                              đãi ngập tràn!</h5>
                                                                        <p class="news-card-text">FPT Shop tự hào thông
                                                                              báo khai trương cửa hàng mới tại trung tâm
                                                                              Hà Nội. Ghé thăm ngay để trải nghiệm không
                                                                              gian mua sắm hiện đại và nhận vô vàn quà
                                                                              tặng giá trị!</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 1876 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://i1-sohoa.vnecdn.net/2024/12/17/FPTSHOP-1734418268-5636-1734418493.png?w=1020&h=0&q=100&dpr=1&fit=crop&s=Pr02ahg9uO7F4p23s3Q0BA"
                                                                        class="img-fluid"
                                                                        alt="Ưu đãi đặc biệt khách hàng thân thiết">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">FPT Shop Hà Nội tri
                                                                              ân khách hàng thân thiết: Ưu đãi độc quyền
                                                                              không thể bỏ lỡ!</h5>
                                                                        <p class="news-card-text">Chương trình đặc biệt
                                                                              dành riêng cho thành viên FPT Shop. Tận
                                                                              hưởng giảm giá sâu, quà tặng độc quyền và
                                                                              ưu tiên dịch vụ khi mua sắm tại bất kỳ chi
                                                                              nhánh nào ở Hà Nội.</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 1250 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://cdn2.fptshop.com.vn/unsafe/Uploads/images/tin-tuc/182889/Originals/laptop.png"
                                                                        class="img-fluid"
                                                                        alt="Săn deal phụ kiện cuối tuần">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">Săn deal phụ kiện
                                                                              cuối tuần: Giảm ngay 30% tại FPT Shop Hà
                                                                              Nội!</h5>
                                                                        <p class="news-card-text">Cơ hội vàng để nâng
                                                                              cấp phụ kiện công nghệ của bạn! Từ tai
                                                                              nghe, sạc dự phòng đến ốp lưng thời trang,
                                                                              tất cả đều đang giảm giá mạnh chỉ trong
                                                                              hai ngày cuối tuần này.</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 987 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <div class="col-md-6">
                                                            <div class="news-card">
                                                                  <img src="https://i1-sohoa.vnecdn.net/2024/10/15/FPT-3329-1728987340.png?w=1020&h=0&q=100&dpr=1&fit=crop&s=3q2ElmHMSFGQTVHMm3s4rw"
                                                                        class="img-fluid"
                                                                        alt="Sản phẩm công nghệ mới ra mắt">
                                                                  <div class="news-card-body">
                                                                        <h5 class="news-card-title">Khám phá sản phẩm
                                                                              công nghệ mới nhất vừa "cập bến" FPT Shop
                                                                              Hà Nội!</h5>
                                                                        <p class="news-card-text">Luôn đi đầu xu hướng,
                                                                              FPT Shop Hà Nội liên tục cập nhật những
                                                                              siêu phẩm công nghệ mới nhất. Ghé thăm
                                                                              ngay để trải nghiệm và là một trong những
                                                                              người đầu tiên sở hữu!</p>
                                                                        <div
                                                                              class="news-card-meta d-flex justify-content-between align-items-center">
                                                                              <span><i class="fas fa-calendar-alt"></i>
                                                                                    <fmt:formatDate
                                                                                          value="<%= new java.util.Date() %>"
                                                                                          pattern="dd/MM/yyyy" />
                                                                              </span>
                                                                              <span><i class="fas fa-eye"></i> 1721 lượt
                                                                                    xem</span>
                                                                        </div>
                                                                        <a href="#" class="btn btn-read-more mt-3">Đọc
                                                                              thêm <i
                                                                                    class="fas fa-arrow-right"></i></a>
                                                                  </div>
                                                            </div>
                                                      </div>
                                                </div>


                                          </div>

                                          <div class="col-lg-4">
                                                <div class="sidebar mb-4">
                                                      <h4>Danh mục tin tức</h4>
                                                      <ul>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Khuyến
                                                                        mãi</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Sản phẩm
                                                                        mới</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Sự
                                                                        kiện</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Tin tức
                                                                        công nghệ</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Hướng dẫn
                                                                        sử dụng</a></li>
                                                      </ul>
                                                </div>

                                                <div class="sidebar recent-posts mb-4">
                                                      <h4>Bài viết nổi bật</h4>
                                                      <ul>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Mở hộp
                                                                        iPhone 15 Pro Max: Có gì hot?</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Top 5
                                                                        laptop văn phòng đáng mua nhất 2024</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Hướng dẫn
                                                                        chọn tai nghe Bluetooth phù hợp</a></li>
                                                            <li><a href="#"><i class="fas fa-angle-right"></i> Ưu đãi
                                                                        trả góp 0% khi mua tại FPT Shop</a></li>
                                                      </ul>
                                                </div>

                                                <div class="sidebar tags mb-4">
                                                      <h4>Thẻ phổ biến</h4>
                                                      <div class="d-flex flex-wrap">
                                                            <a href="#"
                                                                  class="btn btn-sm btn-outline-secondary m-1">Laptop</a>
                                                            <a href="#"
                                                                  class="btn btn-sm btn-outline-secondary m-1">Điện
                                                                  thoại</a>
                                                            <a href="#"
                                                                  class="btn btn-sm btn-outline-secondary m-1">Khuyến
                                                                  mãi</a>
                                                            <a href="#" class="btn btn-sm btn-outline-secondary m-1">Phụ
                                                                  kiện</a>
                                                            <a href="#" class="btn btn-sm btn-outline-secondary m-1">FPT
                                                                  Shop</a>
                                                            <a href="#" class="btn btn-sm btn-outline-secondary m-1">Hà
                                                                  Nội</a>
                                                      </div>
                                                </div>
                                          </div>
                                    </div>
                              </div>

                        </div>
                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                              <ul class="pagination justify-content-center">
                                    <li class="page-item disabled">
                                          <a class="page-link" href="#" tabindex="-1" aria-disabled="true">Trước</a>
                                    </li>
                                    <li class="page-item active"><a class="page-link" href="#">1</a></li>
                                    <li class="page-item"><a class="page-link" href="#">2</a>
                                    </li>
                                    <li class="page-item"><a class="page-link" href="#">3</a>
                                    </li>
                                    <li class="page-item">
                                          <a class="page-link" href="#">Sau</a>
                                    </li>
                              </ul>
                        </nav>

                        <jsp:include page="../layout/footer.jsp" />
                        <%-- Giả sử bạn có file footer.jsp trong /layout --%>

                              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                              <script
                                    src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                              <%-- <script src="/client/lib/easing/easing.min.js"></script>
                                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script> --%>

                                    <script src="/client/js/main.js"></script>
                  </body>

                  </html>