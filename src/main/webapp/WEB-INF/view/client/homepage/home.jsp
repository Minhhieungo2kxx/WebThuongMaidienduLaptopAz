<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


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

                    <style>
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

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->


                    <jsp:include page="../layout/header.jsp" />

                    <!-- Search Overlay -->
                    <div id="search-overlay" class="search-overlay d-none">
                        <div class="search-box">
                            <form action="/product/search" method="get">
                                <input type="text" name="searchTerm" placeholder="Tìm kiếm sản phẩm..."
                                    class="form-control form-control-lg" autofocus />
                                <button type="submit" class="btn btn-primary mt-3">Tìm kiếm</button>
                            </form>
                        </div>
                    </div>






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
                                                <a class="nav-link d-flex m-2 py-2 bg-light rounded-pill active"
                                                    data-bs-toggle="pill" href="#tab-1">
                                                    <span class="text-dark" style="width: 150px;">Tất Cả Laptop</span>
                                                    <!-- Đổi "All Products" -->
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link d-flex py-2 m-2 bg-light rounded-pill"
                                                    data-bs-toggle="pill" href="#tab-2">
                                                    <span class="text-dark" style="width: 150px;">Laptop Gaming</span>
                                                    <!-- Đổi "Vegetables" -->
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link d-flex m-2 py-2 bg-light rounded-pill"
                                                    data-bs-toggle="pill" href="#tab-3">
                                                    <span class="text-dark" style="width: 150px;">Laptop Văn Phòng
                                                    </span>
                                                    <!-- Đổi "Fruits" -->
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link d-flex m-2 py-2 bg-light rounded-pill"
                                                    data-bs-toggle="pill" href="#tab-4">
                                                    <span class="text-dark" style="width: 150px;">Laptop Thiết kế đồ
                                                        họa</span>
                                                    <!-- Đổi "Bread" -->
                                                </a>
                                            </li>
                                            <li class="nav-item">
                                                <a class="nav-link d-flex m-2 py-2 bg-light rounded-pill"
                                                    data-bs-toggle="pill" href="#tab-5">
                                                    <span class="text-dark" style="width: 150px;">Laptop Doanh nhân-
                                                        mỏng nhẹ</span>
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

                                                    <c:forEach var="product" items="${allProducts}">
                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative laptop-item">
                                                                <div class="laptop-img">
                                                                    <img src="/uploads/products/${product.image}"
                                                                        class="img-fluid w-100 rounded-top"
                                                                        alt="${product.name}" />
                                                                </div>
                                                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                    style="top: 10px; left: 10px;">
                                                                    Laptop
                                                                </div>
                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <a href="/product/detail/${product.id}"
                                                                        title="Xem chi tiet">
                                                                        <h4>${product.name}</h4>
                                                                    </a>
                                                                    <p style="font-size: 16px; color: #555;">Số lượng
                                                                        còn: ${product.quantity}</p>
                                                                    <p
                                                                        style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                        ${product.shortDesc}</p>
                                                                    <div
                                                                        class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                            <fmt:formatNumber value="${product.price}"
                                                                                type="number" groupingUsed="true" /> VNĐ
                                                                        </p>

                                                                        <c:choose>
                                                                            <c:when test="${product.quantity > 0}">
                                                                                <!-- Chỉ hiển thị nếu còn hàng -->
                                                                                <form:form method="post"
                                                                                    action="/add-cart/${product.id}">
                                                                                    <button type="submit"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                        <i
                                                                                            class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                        Thêm vào giỏ hàng
                                                                                    </button>
                                                                                    <input type="hidden"
                                                                                        name="${_csrf.parameterName}"
                                                                                        value="${_csrf.token}" />
                                                                                </form:form>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!-- Hiển thị khi hết hàng -->
                                                                                <span class="text-danger fw-bold">Hết
                                                                                    hàng</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>


                                                </div>
                                            </div>
                                        </div>
                                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                            <ul class="pagination justify-content-center mt-4">

                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage > 0}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">«</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Duyệt qua số trang -->
                                                <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                                    <li class="page-item ${i == currentPage ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}">${i + 1}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage < totalPages - 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">»</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </nav>

                                    </div>
                                    <!-- Tab for Laptop Gaming -->
                                    <div id="tab-2" class="tab-pane fade show p-0">
                                        <div class="row g-4">
                                            <div class="col-lg-12">
                                                <div class="row g-4">
                                                    <c:forEach var="gamming" items="${gamingProducts}">
                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative laptop-item">
                                                                <div class="laptop-img">
                                                                    <img src="/uploads/products/${gamming.image}"
                                                                        class="img-fluid w-100 rounded-top"
                                                                        alt="${gamming.name}" />
                                                                </div>
                                                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                    style="top: 10px; left: 10px;">
                                                                    Gaming
                                                                </div>
                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <a href="/product/detail/${gamming.id}"
                                                                        title="Xem chi tiet">
                                                                        <h4>${gamming.name}</h4>
                                                                    </a>

                                                                    <p style="font-size: 16px; color: #555;">Số lượng
                                                                        còn: ${gamming.quantity}</p>
                                                                    <p
                                                                        style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                        ${gamming.shortDesc}</p>
                                                                    <div
                                                                        class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                            <fmt:formatNumber value="${gamming.price}"
                                                                                type="number" groupingUsed="true" /> VNĐ
                                                                        </p>
                                                                        <c:choose>
                                                                            <c:when test="${gamming.quantity > 0}">
                                                                                <!-- Chỉ hiển thị nếu còn hàng -->
                                                                                <form:form method="post"
                                                                                    action="/add-cart/${gamming.id}">
                                                                                    <button type="submit"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                        <i
                                                                                            class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                        Thêm vào giỏ hàng
                                                                                    </button>
                                                                                    <input type="hidden"
                                                                                        name="${_csrf.parameterName}"
                                                                                        value="${_csrf.token}" />
                                                                                </form:form>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!-- Hiển thị khi hết hàng -->
                                                                                <span class="text-danger fw-bold">Hết
                                                                                    hàng</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>

                                                </div>
                                            </div>
                                        </div>
                                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                            <ul class="pagination justify-content-center mt-4">

                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage1 > 0}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage1 - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">«</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Duyệt qua số trang -->
                                                <c:forEach var="i" begin="0" end="${totalPages1 - 1}">
                                                    <li class="page-item ${i == currentPage1 ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}">${i + 1}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage1 < totalPages1 - 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage1 + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">»</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </nav>
                                    </div>
                                    <!-- Tab for Laptop Văn Phòng -->
                                    <div id="tab-3" class="tab-pane fade show p-0">
                                        <div class="row g-4">
                                            <div class="col-lg-12">
                                                <div class="row g-4">

                                                    <c:forEach var="office" items="${officeProducts}">
                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative laptop-item">
                                                                <div class="laptop-img">
                                                                    <img src="/uploads/products/${office.image}"
                                                                        class="img-fluid w-100 rounded-top"
                                                                        alt="${office.name}" />
                                                                </div>
                                                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                    style="top: 10px; left: 10px;">
                                                                    Văn phòng
                                                                </div>
                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <a href="/product/detail/${office.id}"
                                                                        title="Xem chi tiet">
                                                                        <h4>${office.name}</h4>
                                                                    </a>

                                                                    <p style="font-size: 16px; color: #555;">Số lượng
                                                                        còn: ${office.quantity}</p>
                                                                    <p
                                                                        style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                        ${office.shortDesc}</p>
                                                                    <div
                                                                        class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                            <fmt:formatNumber value="${office.price}"
                                                                                type="number" groupingUsed="true" /> VNĐ
                                                                        </p>
                                                                        <c:choose>
                                                                            <c:when test="${office.quantity > 0}">
                                                                                <!-- Chỉ hiển thị nếu còn hàng -->
                                                                                <form:form method="post"
                                                                                    action="/add-cart/${office.id}">
                                                                                    <button type="submit"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                        <i
                                                                                            class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                        Thêm vào giỏ hàng
                                                                                    </button>
                                                                                    <input type="hidden"
                                                                                        name="${_csrf.parameterName}"
                                                                                        value="${_csrf.token}" />
                                                                                </form:form>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!-- Hiển thị khi hết hàng -->
                                                                                <span class="text-danger fw-bold">Hết
                                                                                    hàng</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>

                                                </div>
                                            </div>
                                        </div>
                                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                            <ul class="pagination justify-content-center mt-4">

                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage2 > 0}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage2 - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">«</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Duyệt qua số trang -->
                                                <c:forEach var="i" begin="0" end="${totalPages2 - 1}">
                                                    <li class="page-item ${i == currentPage2 ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}">${i + 1}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage2 < totalPages2 - 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage2 + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">»</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </nav>
                                    </div>
                                    <!-- Tab for Laptop Cao Cấp -->
                                    <div id="tab-4" class="tab-pane fade show p-0">
                                        <div class="row g-4">
                                            <div class="col-lg-12">
                                                <div class="row g-4">
                                                    <c:forEach var="design" items="${designProducts}">
                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative laptop-item">
                                                                <div class="laptop-img">
                                                                    <img src="/uploads/products/${design.image}"
                                                                        class="img-fluid w-100 rounded-top"
                                                                        alt="${office.name}" />
                                                                </div>
                                                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                    style="top: 10px; left: 10px;">
                                                                    Đồ họa
                                                                </div>
                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <a href="/product/detail/${design.id}"
                                                                        title="Xem chi tiet">
                                                                        <h4>${design.name}</h4>
                                                                    </a>

                                                                    <p style="font-size: 16px; color: #555;">Số lượng
                                                                        còn: ${design.quantity}</p>
                                                                    <p
                                                                        style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                        ${design.shortDesc}</p>
                                                                    <div
                                                                        class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                            <fmt:formatNumber value="${design.price}"
                                                                                type="number" groupingUsed="true" /> VNĐ
                                                                        </p>
                                                                        <c:choose>
                                                                            <c:when test="${design.quantity > 0}">
                                                                                <!-- Chỉ hiển thị nếu còn hàng -->
                                                                                <form:form method="post"
                                                                                    action="/add-cart/${design.id}">
                                                                                    <button type="submit"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                        <i
                                                                                            class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                        Thêm vào giỏ hàng
                                                                                    </button>
                                                                                    <input type="hidden"
                                                                                        name="${_csrf.parameterName}"
                                                                                        value="${_csrf.token}" />
                                                                                </form:form>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!-- Hiển thị khi hết hàng -->
                                                                                <span class="text-danger fw-bold">Hết
                                                                                    hàng</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                            <ul class="pagination justify-content-center mt-4">

                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage3 > 0}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage3 - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">«</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Duyệt qua số trang -->
                                                <c:forEach var="i" begin="0" end="${totalPages3 - 1}">
                                                    <li class="page-item ${i == currentPage3 ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}">${i + 1}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage3 < totalPages3 - 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage3 + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">»</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </nav>
                                    </div>
                                    <!-- Tab for Phụ Kiện Laptop (tab-5) -->
                                    <div id="tab-5" class="tab-pane fade show p-0">
                                        <div class="row g-4">
                                            <div class="col-lg-12">
                                                <div class="row g-4">
                                                    <c:forEach var="personal" items="${personalProducts}">
                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                            <div class="rounded position-relative laptop-item">
                                                                <div class="laptop-img">
                                                                    <img src="/uploads/products/${personal.image}"
                                                                        class="img-fluid w-100 rounded-top"
                                                                        alt="${personal.name}" />
                                                                </div>
                                                                <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                    style="top: 10px; left: 10px;">
                                                                    Mỏng nhẹ
                                                                </div>
                                                                <div
                                                                    class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                    <a href="/product/detail/${personal.id}"
                                                                        title="Xem chi tiet">
                                                                        <h4>${personal.name}</h4>
                                                                    </a>

                                                                    <p style="font-size: 16px; color: #555;">Số lượng
                                                                        còn: ${personal.quantity}</p>
                                                                    <p
                                                                        style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                        ${personal.shortDesc}</p>
                                                                    <div
                                                                        class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                        <p class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                            <fmt:formatNumber value="${personal.price}"
                                                                                type="number" groupingUsed="true" /> VNĐ
                                                                        </p>
                                                                        <c:choose>
                                                                            <c:when test="${personal.quantity > 0}">
                                                                                <!-- Chỉ hiển thị nếu còn hàng -->
                                                                                <form:form method="post"
                                                                                    action="/add-cart/${personal.id}">
                                                                                    <button type="submit"
                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                        <i
                                                                                            class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                        Thêm vào giỏ hàng
                                                                                    </button>
                                                                                    <input type="hidden"
                                                                                        name="${_csrf.parameterName}"
                                                                                        value="${_csrf.token}" />
                                                                                </form:form>
                                                                            </c:when>
                                                                            <c:otherwise>
                                                                                <!-- Hiển thị khi hết hàng -->
                                                                                <span class="text-danger fw-bold">Hết
                                                                                    hàng</span>
                                                                            </c:otherwise>
                                                                        </c:choose>
                                                                    </div>
                                                                </div>
                                                            </div>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                            </div>
                                        </div>
                                        <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                            <ul class="pagination justify-content-center mt-4">

                                                <!-- Nút Previous -->
                                                <c:if test="${currentPage4 > 0}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage4 - 1}"
                                                            aria-label="Previous">
                                                            <span aria-hidden="true">«</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                                <!-- Duyệt qua số trang -->
                                                <c:forEach var="i" begin="0" end="${totalPages4 - 1}">
                                                    <li class="page-item ${i == currentPage4 ? 'active' : ''}">
                                                        <a class="page-link" href="?page=${i}">${i + 1}</a>
                                                    </li>
                                                </c:forEach>

                                                <!-- Nút Next -->
                                                <c:if test="${currentPage4 < totalPages4 - 1}">
                                                    <li class="page-item">
                                                        <a class="page-link" href="?page=${currentPage4 + 1}"
                                                            aria-label="Next">
                                                            <span aria-hidden="true">»</span>
                                                        </a>
                                                    </li>
                                                </c:if>

                                            </ul>
                                        </nav>
                                    </div>
                                    <!-- Laptop doanh nhan -->

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
                    <script>
                        // Ghi nhớ tab được chọn
                        document.querySelectorAll('.nav-link').forEach(function (tab) {
                            tab.addEventListener('click', function () {
                                const target = this.getAttribute('href');
                                localStorage.setItem('activeTab', target);
                            });
                        });

                        // Kích hoạt lại tab đã lưu khi load trang
                        window.addEventListener('DOMContentLoaded', function () {
                            const lastTab = localStorage.getItem('activeTab');
                            if (lastTab) {
                                const triggerTab = document.querySelector('.nav-link[href="' + lastTab + '"]');
                                if (triggerTab) {
                                    new bootstrap.Tab(triggerTab).show();
                                }
                            }
                        });
                    </script>

                    <script>
                        const searchIcon = document.getElementById("search-icon");
                        const searchOverlay = document.getElementById("search-overlay");

                        // Hiện overlay khi nhấn icon
                        searchIcon.addEventListener("click", function () {
                            searchOverlay.classList.remove("d-none");
                            // Tự focus vào ô input
                            const input = searchOverlay.querySelector("input");
                            if (input) {
                                setTimeout(() => input.focus(), 100);
                            }
                        });

                        // Ẩn overlay khi nhấn ngoài vùng hoặc ESC
                        searchOverlay.addEventListener("click", function (e) {
                            if (e.target === this) {
                                searchOverlay.classList.add("d-none");
                            }
                        });

                        document.addEventListener("keydown", function (e) {
                            if (e.key === "Escape") {
                                searchOverlay.classList.add("d-none");
                            }
                        });
                    </script>

                </body>

                </html>