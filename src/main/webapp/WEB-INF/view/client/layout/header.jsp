<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!-- Navbar Start - FPT Laptop Hà Nội -->
            <div class="container-fluid fixed-top">
                <div class="container px-0">
                    <nav class="navbar navbar-light bg-white navbar-expand-xl">
                        <a href="/" class="navbar-brand">
                            <h1 class="text-primary display-6">FPT Laptop</h1>
                            <!-- Đổi tên thương hiệu thành FPT Laptop -->
                        </a>
                        <button class="navbar-toggler py-2 px-3" type="button" data-bs-toggle="collapse"
                            data-bs-target="#navbarCollapse">
                            <span class="fa fa-bars text-primary"></span>
                        </button>
                        <div class="collapse navbar-collapse bg-white" id="navbarCollapse">
                            <div class="navbar-nav mx-auto">
                                <a href="/" class="nav-item nav-link active">Trang Chủ</a>
                                <!-- Việt hóa "Trang trủ" thành "Trang Chủ" -->
                                <a href="#" class="nav-item nav-link">Sản Phẩm</a> <!-- Việt hóa "Sản phẩm" -->
                                <a href="/news" class="nav-item nav-link">Tin Tức & Ưu Đãi</a>
                                <!-- Đổi "Bài Viết" thành "Tin Tức & Ưu Đãi" -->
                                <a href="/contact" class="nav-item nav-link">Liên Hệ</a> <!-- Giữ nguyên "Liên hệ" -->
                                <a href="/review" class="nav-item nav-link">Về Chúng Tôi</a>
                                <!-- Thêm mục "Về Chúng Tôi" -->
                            </div>
                            <div class="d-flex m-3 me-0">
                                <c:if test="${not empty pageContext.request.userPrincipal}">



                                    <a href="/cart" class="position-relative me-4 my-auto">
                                        <i class="fa fa-shopping-cart fa-2x"></i>
                                        <!-- Thay đổi biểu tượng túi mua sắm thành giỏ hàng -->
                                        <span
                                            class="position-absolute bg-secondary rounded-circle d-flex align-items-center justify-content-center text-dark px-1"
                                            style="top: -5px; left: 15px; height: 20px; min-width: 20px;">${sessionScope.sum}</span>
                                    </a>
                                    <div class="dropdown my-auto">
                                        <a href="#" class="text-decoration-none" role="button" id="profileDropdown"
                                            data-bs-toggle="dropdown" aria-expanded="false">
                                            <i class="fas fa-user fa-2x text-danger"></i>
                                        </a>
                                        <ul class="dropdown-menu dropdown-menu-end p-4 dropdown-menu-profile"
                                            aria-labelledby="profileDropdown">
                                            <li class="d-flex flex-column align-items-center dropdown-profile-header">
                                                <img class="profile-avatar mb-3"
                                                    src="/uploads/avatars/${sessionScope.avatar}" alt="User Profile" />
                                                <div class="text-center fw-bold">FPT Shop</div>
                                                <div class="text-center text-muted small">
                                                    <c:out value="${sessionScope.email}" />
                                                </div>
                                            </li>
                                            <li><a class="dropdown-item" href="/update-user">Quản lý tài khoản</a></li>
                                            <li><a class="dropdown-item" href="#">Lịch sử mua hàng</a></li>
                                            <li>
                                                <hr class="dropdown-divider" />
                                            </li>
                                            <li>
                                                <form method="post" action="/logout">
                                                    <button class="dropdown-item text-danger">Đăng xuất</button>
                                                    <div>
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                    </div>
                                                </form>

                                            </li>
                                        </ul>
                                    </div>
                                </c:if>
                                <c:if test="${empty pageContext.request.userPrincipal}">

                                    <a href="/login" class="btn btn-outline-danger my-auto me-4">

                                        <i class="fas fa-sign-in-alt me-2"></i>
                                        Đăng nhập
                                    </a>
                                </c:if>
                            </div>
                        </div>
                    </nav>
                </div>
            </div>
            <!-- Navbar End -->