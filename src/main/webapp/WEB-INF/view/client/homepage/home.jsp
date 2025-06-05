<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Ecornomere</title>

                <!-- Google Web Fonts -->
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">
            </head>

            <body>

                <!-- Spinner Start -->
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>
                <!-- Spinner End -->


                <jsp:include page="../layout/header.jsp" />


                <!-- Modal Search Start -->
                <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-fullscreen">
                        <div class="modal-content rounded-0">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body d-flex align-items-center">
                                <div class="input-group w-75 mx-auto d-flex">
                                    <input type="search" class="form-control p-3" placeholder="keywords"
                                        aria-describedby="search-icon-1">
                                    <span id="search-icon-1" class="input-group-text p-3"><i
                                            class="fa fa-search"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Modal Search End -->


                <jsp:include page="../layout/banner.jsp" />

                <jsp:include page="../layout/feature.jsp" />

                <!-- Fruits Shop Start-->
                <div class="container-fluid fruite py-5">
                    <!-- Giữ nguyên class fruite để tận dụng CSS hiện có nếu cần -->
                    <div class="container py-5">
                        <div class="tab-class text-center">
                            <div class="row g-4">
                                <div class="col-lg-4 text-start">
                                    <h1>Sản phẩm nổi bật</h1>
                                    <!-- Đổi tiêu đề thành "Danh Sách Sản Phẩm Laptop" -->
                                </div>
                                <div class="col-lg-8 text-end">
                                    <ul class="nav nav-pills d-inline-flex text-center mb-5">
                                        <li class="nav-item">
                                            <a class="d-flex m-2 py-2 bg-light rounded-pill active"
                                                data-bs-toggle="pill" href="#tab-1">
                                                <span class="text-dark" style="width: 150px;">Tất Cả Laptop</span>
                                                <!-- Đổi "All Products" -->
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="d-flex py-2 m-2 bg-light rounded-pill" data-bs-toggle="pill"
                                                href="#tab-2">
                                                <span class="text-dark" style="width: 150px;">Laptop Gaming</span>
                                                <!-- Đổi "Vegetables" -->
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="d-flex m-2 py-2 bg-light rounded-pill" data-bs-toggle="pill"
                                                href="#tab-3">
                                                <span class="text-dark" style="width: 150px;">Laptop Văn Phòng-Sinh
                                                    Viên</span>
                                                <!-- Đổi "Fruits" -->
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="d-flex m-2 py-2 bg-light rounded-pill" data-bs-toggle="pill"
                                                href="#tab-4">
                                                <span class="text-dark" style="width: 150px;">Laptop Thiết kế đồ
                                                    họa</span>
                                                <!-- Đổi "Bread" -->
                                            </a>
                                        </li>
                                        <li class="nav-item">
                                            <a class="d-flex m-2 py-2 bg-light rounded-pill" data-bs-toggle="pill"
                                                href="#tab-5">
                                                <span class="text-dark" style="width: 150px;">Laptop mỏng nhẹ</span>
                                                <!-- Đổi "Meat" -->
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                            <div class="tab-content">
                                <div id="tab-1" class="tab-pane fade show p-0 active">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <!-- Laptop Item 1 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <!-- Đổi class fruite-item thành laptop-item -->
                                                        <div class="laptop-img"> <!-- Đổi class fruite-img -->
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Dell+XPS+15"
                                                                class="img-fluid w-100 rounded-top" alt="Dell XPS 15">
                                                            <!-- Thay ảnh và alt -->
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Laptop</div>
                                                        <!-- Đổi tag từ Fruits thành Laptop -->
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Dell XPS 15</h4> <!-- Đổi tên sản phẩm -->
                                                            <p>Hiệu năng mạnh mẽ, thiết kế sang trọng, phù hợp mọi nhu
                                                                cầu.</p>
                                                            <!-- Đổi mô tả -->
                                                            <div
                                                                class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0 me-3">25.990.000
                                                                    VNĐ
                                                                </p>
                                                                <!-- Đổi giá -->
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    <!-- Đổi biểu tượng và text -->
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Item 2 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=MacBook+Air+M3"
                                                                class="img-fluid w-100 rounded-top"
                                                                alt="MacBook Air M3">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Laptop</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>MacBook Air M3</h4>
                                                            <p>Mỏng nhẹ, hiệu năng vượt trội với chip M3, pin cực lâu.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">28.490.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Item 3 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=HP+Spectre+x360"
                                                                class="img-fluid w-100 rounded-top"
                                                                alt="HP Spectre x360">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Laptop</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>HP Spectre x360</h4>
                                                            <p>Thiết kế xoay gập linh hoạt, màn hình cảm ứng sắc nét.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">27.190.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Item 4 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Lenovo+ThinkPad"
                                                                class="img-fluid w-100 rounded-top"
                                                                alt="Lenovo ThinkPad">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Laptop</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Lenovo ThinkPad</h4>
                                                            <p>Độ bền vượt trội, bảo mật cao, lý tưởng cho doanh nghiệp.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">22.500.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Tab for Laptop Gaming -->
                                <div id="tab-2" class="tab-pane fade show p-0">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <!-- Laptop Gaming Item 1 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Acer+Predator"
                                                                class="img-fluid w-100 rounded-top" alt="Acer Predator">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Gaming</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Acer Predator Helios</h4>
                                                            <p>Sức mạnh tối thượng cho game thủ, tản nhiệt hiệu quả.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">35.000.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Gaming Item 2 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=ASUS+ROG"
                                                                class="img-fluid w-100 rounded-top" alt="ASUS ROG">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Gaming</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>ASUS ROG Zephyrus</h4>
                                                            <p>Thiết kế mỏng nhẹ, hiệu năng gaming đỉnh cao.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">32.890.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Gaming Item 3 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=MSI+Katana"
                                                                class="img-fluid w-100 rounded-top" alt="MSI Katana">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Gaming</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>MSI Katana GF66</h4>
                                                            <p>Trải nghiệm gaming mượt mà, cấu hình mạnh mẽ.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">29.990.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Gaming Item 4 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Lenovo+Legion"
                                                                class="img-fluid w-100 rounded-top" alt="Lenovo Legion">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Gaming</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Lenovo Legion 5 Pro</h4>
                                                            <p>Hiệu suất vượt trội, màn hình tần số quét cao.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">38.500.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Tab for Laptop Văn Phòng -->
                                <div id="tab-3" class="tab-pane fade show p-0">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <!-- Laptop Văn Phòng Item 1 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=HP+Pavilion"
                                                                class="img-fluid w-100 rounded-top" alt="HP Pavilion">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Văn Phòng</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>HP Pavilion 15</h4>
                                                            <p>Thiết kế thanh lịch, hiệu năng ổn định cho công việc.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">15.290.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Văn Phòng Item 2 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Dell+Vostro"
                                                                class="img-fluid w-100 rounded-top" alt="Dell Vostro">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Văn Phòng</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Dell Vostro 3510</h4>
                                                            <p>Bền bỉ, đáng tin cậy, lựa chọn tốt cho công việc hàng
                                                                ngày.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">13.500.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Văn Phòng Item 3 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Acer+Aspire"
                                                                class="img-fluid w-100 rounded-top" alt="Acer Aspire">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Văn Phòng</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Acer Aspire 5</h4>
                                                            <p>Cấu hình tốt trong tầm giá, phù hợp học tập và làm việc.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">12.890.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Văn Phòng Item 4 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=ASUS+Vivobook"
                                                                class="img-fluid w-100 rounded-top" alt="ASUS Vivobook">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Văn Phòng</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>ASUS Vivobook 14</h4>
                                                            <p>Thiết kế trẻ trung, hiệu năng ổn định, giá phải chăng.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">11.990.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Tab for Laptop Cao Cấp -->
                                <div id="tab-4" class="tab-pane fade show p-0">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <!-- Laptop Cao Cấp Item 1 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Microsoft+Surface"
                                                                class="img-fluid w-100 rounded-top"
                                                                alt="Microsoft Surface">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Cao Cấp</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Microsoft Surface Laptop 5</h4>
                                                            <p>Thiết kế sang trọng, màn hình PixelSense tuyệt đẹp.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">30.500.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Laptop Cao Cấp Item 2 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=LG+Gram"
                                                                class="img-fluid w-100 rounded-top" alt="LG Gram">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Cao Cấp</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>LG Gram 17</h4>
                                                            <p>Laptop 17 inch siêu nhẹ, pin cực khủng, hiệu năng ổn
                                                                định.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">29.700.000 VNĐ
                                                                </p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <!-- Tab for Phụ Kiện Laptop (tab-5) -->
                                <div id="tab-5" class="tab-pane fade show p-0">
                                    <div class="row g-4">
                                        <div class="col-lg-12">
                                            <div class="row g-4">
                                                <!-- Phụ Kiện Item 1 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Chuot+Gaming"
                                                                class="img-fluid w-100 rounded-top" alt="Chuột Gaming">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Phụ Kiện</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Chuột Gaming Logitech G502</h4>
                                                            <p>Thiết kế công thái học, độ chính xác cao cho game thủ.
                                                            </p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">1.290.000 VNĐ</p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <!-- Phụ Kiện Item 2 -->
                                                <div class="col-md-6 col-lg-4 col-xl-3">
                                                    <div class="rounded position-relative laptop-item">
                                                        <div class="laptop-img">
                                                            <img src="https://placehold.co/600x400/E2AF18/FFFFFF?text=Balo+Laptop"
                                                                class="img-fluid w-100 rounded-top" alt="Balo Laptop">
                                                        </div>
                                                        <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                            style="top: 10px; left: 10px;">Phụ Kiện</div>
                                                        <div
                                                            class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                            <h4>Balo Laptop Cao Cấp</h4>
                                                            <p>Chống sốc, chống nước, bảo vệ laptop tối ưu.</p>
                                                            <div class="d-flex justify-content-between flex-lg-wrap">
                                                                <p class="text-dark fs-5 fw-bold mb-0">750.000 VNĐ</p>
                                                                <a href="#"
                                                                    class="btn border border-secondary rounded-pill px-3 text-primary"><i
                                                                        class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                    Thêm vào giỏ hàng</a>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Fruits Shop End-->



                <jsp:include page="../layout/footer.jsp" />

                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>
            </body>

            </html>