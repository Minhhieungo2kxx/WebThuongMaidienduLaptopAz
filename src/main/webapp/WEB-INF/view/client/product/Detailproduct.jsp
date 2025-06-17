<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>${detailProduct.name}</title>

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
                    <style>
                        .border.rounded {
                            height: 300px;
                            /* Chiều cao cố định cho ảnh */
                            overflow: hidden;
                            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                            transition: transform 0.3s ease;
                        }

                        .border.rounded:hover {
                            transform: scale(1.05);
                            /* Hiệu ứng zoom khi hover */
                        }

                        .border.rounded img {
                            width: 100%;
                            height: 100%;
                            object-fit: cover;
                            object-position: center;
                        }

                        .product-details h4 {
                            font-size: 1.8rem;
                            font-weight: 700;
                            color: #2c3e50;
                        }

                        .product-details h5 {
                            font-size: 1.5rem;
                            color: #e74c3c;
                            font-weight: 600;
                        }

                        .product-details p {
                            font-size: 1rem;
                            color: #555;
                        }

                        .quantity .form-control {
                            width: 60px;
                            text-align: center;
                        }

                        .btn-primary {
                            background-color: #007bff;
                            border: none;
                            padding: 10px 20px;
                            font-weight: 600;
                            transition: background-color 0.3s ease;
                        }

                        .btn-primary:hover {
                            background-color: #0056b3;
                        }

                        .nav-tabs .nav-link {
                            color: #007bff;
                            font-weight: 600;
                            border-radius: 5px 5px 0 0;
                            transition: background-color 0.3s ease;
                        }

                        .nav-tabs .nav-link.active {
                            background-color: #007bff;
                            color: #fff;
                        }

                        .nav-tabs .nav-link:hover {
                            background-color: #e9ecef;
                        }

                        .tab-content {
                            background-color: #fff;
                            padding: 20px;
                            border-radius: 0 5px 5px 5px;
                            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                        }

                        .review-item {
                            border-bottom: 1px solid #e9ecef;
                            padding: 15px 0;
                        }

                        .review-item img {
                            border: 2px solid #007bff;
                        }

                        .review-item p.text-dark {
                            font-style: italic;
                            background-color: #f8f9fa;
                            padding: 10px;
                            border-radius: 5px;
                        }

                        .fruite-categorie {
                            background-color: #fff;
                            padding: 20px;
                            border-radius: 5px;
                            box-shadow: 0 2px 5px rgba(0, 0, 0, 0.1);
                        }

                        .fruite-categorie li {
                            margin-bottom: 10px;
                        }

                        .fruite-categorie a {
                            color: #2c3e50;
                            font-weight: 500;
                            transition: color 0.3s ease;
                        }

                        .fruite-categorie a:hover {
                            color: #007bff;
                        }

                        .fruite-categorie i {
                            color: #007bff;
                        }
                    </style>
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


                    <!-- begin Product -->
                    <div class="container-fluid py-5 mt-5">

                        <div class="container py-5">
                            <div class="row g-4 mb-5">
                                <div class="col-lg-8 col-xl-9">
                                    <div class="row g-4">
                                        <div class="col-lg-6">
                                            <div class="border rounded">
                                                <a href="#">
                                                    <img src="/uploads/products/${detailProduct.image}"
                                                        class="img-fluid rounded" alt="Image">
                                                </a>
                                            </div>
                                        </div>
                                        <div class="col-lg-6">
                                            <h4 class="fw-bold mb-3">${detailProduct.name}</h4>
                                            <p class="mb-3">${detailProduct.factory}</p>

                                            <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                <fmt:formatNumber value="${detailProduct.price}" type="number"
                                                    groupingUsed="true" /> VND
                                            </p>
                                            <div class="d-flex mb-4">
                                                <i class="fa fa-star text-secondary"></i>
                                                <i class="fa fa-star text-secondary"></i>
                                                <i class="fa fa-star text-secondary"></i>
                                                <i class="fa fa-star text-secondary"></i>
                                                <i class="fa fa-star"></i>
                                            </div>
                                            <p class="mb-4">${detailProduct.shortDesc}</p>

                                            <div class="input-group quantity mb-5" style="width: 100px;">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-sm btn-minus rounded-circle bg-light border">
                                                        <i class="fa fa-minus"></i>
                                                    </button>
                                                </div>
                                                <input type="text"
                                                    class="form-control form-control-sm text-center border-0" value="1">
                                                <div class="input-group-btn">
                                                    <button class="btn btn-sm btn-plus rounded-circle bg-light border">
                                                        <i class="fa fa-plus"></i>
                                                    </button>
                                                </div>
                                            </div>


                                            <form:form method="post" action="/add-cart/${detailProduct.id}">
                                                <button type="submit"
                                                    class="btn border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-shopping-cart me-2 text-primary"></i>
                                                    Add to cart
                                                </button>
                                                <div>
                                                    <input type="hidden" name="${_csrf.parameterName}"
                                                        value="${_csrf.token}" />
                                                </div>

                                            </form:form>

                                        </div>
                                        <div class="col-lg-12">
                                            <nav>
                                                <div class="nav nav-tabs mb-3">
                                                    <button class="nav-link border-white border-bottom-0 active"
                                                        type="button" role="tab" id="nav-about-tab" data-bs-toggle="tab"
                                                        data-bs-target="#nav-about" aria-controls="nav-about"
                                                        aria-selected="true">Description</button>
                                                    <button class="nav-link border-white border-bottom-0" type="button"
                                                        role="tab" id="nav-mission-tab" data-bs-toggle="tab"
                                                        data-bs-target="#nav-mission" aria-controls="nav-mission"
                                                        aria-selected="false">Reviews</button>
                                                </div>
                                            </nav>
                                            <div class="tab-content mb-5">
                                                <div class="tab-pane active" id="nav-about" role="tabpanel"
                                                    aria-labelledby="nav-about-tab">
                                                    <p>${detailProduct.detailDesc} </p>


                                                </div>
                                                <div class="tab-pane" id="nav-mission" role="tabpanel"
                                                    aria-labelledby="nav-mission-tab">
                                                    <div class="d-flex">
                                                        <img src="/client/img/anh2.jpg"
                                                            class="img-fluid rounded-circle p-3"
                                                            style="width: 100px; height: 100px;" alt="">
                                                        <div class="">
                                                            <p class="mb-2" style="font-size: 14px;">April 8, 2026</p>
                                                            <div class="d-flex justify-content-between">
                                                                <h5>Nguyễn Thu Hương</h5>
                                                                <div class="d-flex mb-3">
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star"></i>
                                                                </div>
                                                            </div>
                                                            <p class="text-dark">"Mình mua này tại FPT Shop, máy
                                                                chạy mượt, thiết kế
                                                                đẹp, pin lâu và bền hiệu năng sử dụng lâu dai. Nhân viên
                                                                tư
                                                                vấn nhiệt tình, giao hàng
                                                                nhanh, dịch vụ bảo hành rất đáng tin cậy. Chắc chắn sẽ
                                                                quay
                                                                lại mua thêm!"
                                                            </p>
                                                        </div>
                                                    </div>
                                                    <div class="d-flex">
                                                        <img src="/client/img/anh3.jpg"
                                                            class="img-fluid rounded-circle p-3"
                                                            style="width: 100px; height: 100px;" alt="">
                                                        <div class="">
                                                            <p class="mb-2" style="font-size: 14px;">April 10, 2026</p>
                                                            <div class="d-flex justify-content-between">
                                                                <h5>Lê Phương Anh</h5>
                                                                <div class="d-flex mb-3">
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star text-secondary"></i>
                                                                    <i class="fa fa-star"></i>
                                                                    <i class="fa fa-star"></i>
                                                                </div>
                                                            </div>
                                                            <p class="text-dark">"Mình mua này tại FPT Shop, máy
                                                                chạy mượt, thiết kế
                                                                đẹp, pin lâu. Nhân viên tư vấn nhiệt tình, giao hàng
                                                                nhanh, dịch vụ bảo hành rất đáng tin cậy. Chắc chắn sẽ
                                                                quay
                                                                lại mua thêm!"
                                                            </p>

                                                        </div>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>

                                    </div>
                                </div>
                                <div class="col-lg-4 col-xl-3">
                                    <div class="row g-4 fruite">
                                        <div class="col-lg-12">

                                            <div class="mb-4">
                                                <h4>Categories</h4>
                                                <ul class="list-unstyled fruite-categorie">
                                                    <li>
                                                        <div class="d-flex justify-content-between fruite-name">
                                                            <a href="#"><i class="fas fa-apple-alt me-2"></i>Apples</a>
                                                            <span>(3)</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="d-flex justify-content-between fruite-name">
                                                            <a href="#"><i class="fas fa-apple-alt me-2"></i>Dell</a>
                                                            <span>(5)</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="d-flex justify-content-between fruite-name">
                                                            <a href="#"><i class="fas fa-apple-alt me-2"></i>LG</a>
                                                            <span>(2)</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="d-flex justify-content-between fruite-name">
                                                            <a href="#"><i class="fas fa-apple-alt me-2"></i>Acer</a>
                                                            <span>(8)</span>
                                                        </div>
                                                    </li>
                                                    <li>
                                                        <div class="d-flex justify-content-between fruite-name">
                                                            <a href="#"><i class="fas fa-apple-alt me-2"></i>Asus</a>
                                                            <span>(5)</span>
                                                        </div>
                                                    </li>
                                                </ul>
                                            </div>
                                        </div>

                                    </div>
                                </div>
                            </div>

                        </div>

                    </div>

                    <!-- End Product -->

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