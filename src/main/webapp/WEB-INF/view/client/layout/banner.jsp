<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!-- Hero Start - FPT Laptop Hà Nội -->
            <div class="container-fluid py-5 mb-5 hero-header">
                <div class="container py-5">
                    <div class="row g-5 align-items-center">
                        <div class="col-md-12 col-lg-7">
                            <h4 class="mb-3 text-secondary">Khám Phá Thế Giới Laptop Tại FPT</h4>
                            <h1 class="mb-5 display-3 text-primary">Laptop Chính Hãng Ưu Đãi Hấp Dẫn</h1>
                            <!-- Bạn có thể thêm nút kêu gọi hành động tại đây nếu muốn -->
                            <!-- <a href="#" class="btn btn-primary border-0 border-secondary py-3 px-4 rounded-pill text-white">Mua Ngay</a> -->
                        </div>
                        <div class="col-md-12 col-lg-5">
                            <div id="carouselId" class="carousel slide position-relative" data-bs-ride="carousel">
                                <div class="carousel-inner" role="listbox">
                                    <div class="carousel-item active rounded">
                                        <!-- Thay thế bằng ảnh laptop thực tế của bạn -->
                                        <img src="../client/img/laptop1.jpg"
                                            class="img-fluid w-100 h-100 bg-secondary rounded"
                                            alt="Laptop Chính Hãng 1">
                                        <a href="#" class="btn px-4 py-2 text-white rounded">Laptop</a>
                                    </div>
                                    <div class="carousel-item rounded">
                                        <!-- Thay thế bằng ảnh laptop thực tế của bạn -->
                                        <img src="../client/img/laptop2.jpg" class="img-fluid w-100 h-100 rounded"
                                            alt="Laptop Chính Hãng 2">
                                        <a href="#" class="btn px-4 py-2 text-white rounded">Laptop</a>
                                    </div>
                                    <div class="carousel-item rounded">
                                        <!-- Thay thế bằng ảnh laptop thực tế của bạn -->
                                        <img src="../client/img/laptop3.jpg" class="img-fluid w-100 h-100 rounded"
                                            alt="Laptop Chính Hãng 3">
                                        <a href="#" class="btn px-4 py-2 text-white rounded">Laptop</a>
                                    </div>
                                </div>
                                <button class="carousel-control-prev" type="button" data-bs-target="#carouselId"
                                    data-bs-slide="prev">
                                    <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Trước</span>
                                </button>
                                <button class="carousel-control-next" type="button" data-bs-target="#carouselId"
                                    data-bs-slide="next">
                                    <span class="carousel-control-next-icon" aria-hidden="true"></span>
                                    <span class="visually-hidden">Tiếp</span>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!-- Hero End -->