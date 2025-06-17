<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                <html lang="en">

                <head>
                    <meta charset="UTF-8">
                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                    <title>CartDetails</title>

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
                <style>
                    /* Custom CSS for Page Header/Breadcrumb */
                    .page-header {
                        background: url('/path/to/your/header-bg.jpg') no-repeat center center;
                        /* Optional: Add a background image */
                        background-size: cover;
                        background-color: #7fad39;
                        /* Example background color, adjust to your theme */
                        padding: 6rem 0;
                        /* Adjust padding as needed */
                        margin-bottom: 3rem;
                        /* Space below the header */
                    }

                    .page-header h1 {
                        color: #fff;
                        font-weight: 800;
                        margin-bottom: 1rem;
                    }

                    .page-header .breadcrumb {
                        background-color: transparent;
                        padding: 0;
                        margin: 0;
                    }

                    .page-header .breadcrumb-item a {
                        color: #fff;
                        text-decoration: none;
                        font-weight: 600;
                    }

                    .page-header .breadcrumb-item a:hover {
                        color: #f8f9fa;
                        /* Lighter on hover */
                    }

                    .page-header .breadcrumb-item.active {
                        color: #eee;
                        /* Slightly darker white for active item */
                        font-weight: 600;
                    }

                    /* Enhancements for Cart Table */
                    .table thead th {
                        border-bottom: 2px solid #dee2e6;
                        /* Stronger line under table header */
                    }

                    .table tbody tr {
                        border-bottom: 1px solid #dee2e6;
                        /* Lighter line between rows */
                    }

                    .table tbody tr:last-child {
                        border-bottom: none;
                        /* No border for the last row */
                    }

                    .table .quantity .form-control {
                        border-left: none !important;
                        /* Remove border from input to merge with buttons */
                        border-right: none !important;
                    }

                    /* Ensure buttons are properly styled */
                    .btn-minus,
                    .btn-plus {
                        width: 35px;
                        /* Fixed width for quantity buttons */
                        height: 35px;
                        /* Fixed height for quantity buttons */
                        display: flex;
                        align-items: center;
                        justify-content: center;
                    }

                    .btn-danger {
                        background-color: #dc3545;
                        /* Bootstrap danger color */
                        border-color: #dc3545;
                        color: #fff;
                    }

                    .btn-danger:hover {
                        background-color: #c82333;
                        border-color: #bd2130;
                    }

                    .btn-primary {
                        background-color: #007bff;
                        /* Example primary color, adjust to your theme */
                        border-color: #007bff;
                        color: #fff;
                    }

                    .btn-primary:hover {
                        background-color: #0056b3;
                        border-color: #004085;
                    }
                </style>

                <body>

                    <!-- Spinner Start -->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner End -->


                    <jsp:include page="../layout/header.jsp" />



                    <!-- begin Cart -->
                    <div class="container-fluid page-header py-5">
                        <h1 class="text-center text-white display-6">Chi tiết giỏ hàng</h1>
                        <ol class="breadcrumb justify-content-center mb-0">
                            <li class="breadcrumb-item"><a href="/">Home</a></li>
                            <li class="breadcrumb-item active text-white">Chi tiết giỏ hàng</li>
                        </ol>
                    </div>
                    <div class="container-fluid py-5">
                        <div class="container py-5">
                            <div class="table-responsive">

                                <table class="table table-hover align-middle">
                                    <thead>
                                        <tr class="text-primary">
                                            <th scope="col">Sản phẩm</th>
                                            <th scope="col">Tên sản phẩm</th>
                                            <th scope="col">Giá</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Tổng cộng</th>
                                            <th scope="col">Thao tác</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${empty listCartDetails}">
                                            <div class="alert alert-info text-center mt-3" role="alert">
                                                Giỏ hàng của bạn đang trống. Hãy thêm sản phẩm vào nhé!
                                            </div>
                                        </c:if>
                                        <c:forEach var="cartdetail" items="${listCartDetails}">

                                            <tr>
                                                <th scope="row">
                                                    <div class="d-flex align-items-center">
                                                        <img src="/uploads/products/${cartdetail.product.image}"
                                                            class="img-fluid rounded-circle"
                                                            style="width: 80px; height: 80px; object-fit: cover;"
                                                            alt="Big Banana">
                                                    </div>
                                                </th>
                                                <td>
                                                    <a href="/product/detail/${cartdetail.product.id}">
                                                        <p class="mb-0">${cartdetail.product.name}</p>
                                                    </a>

                                                </td>
                                                <td>
                                                    <p class="mb-0 text-dark fw-bold">

                                                        <fmt:formatNumber value="${cartdetail.price}" type="number"
                                                            groupingUsed="true" />
                                                    </p>
                                                </td>
                                                <td>
                                                    <div class="input-group quantity" style="width: 130px;">
                                                        <div class="input-group quantity" style="width: 130px;">
                                                            <div class="input-group-btn">

                                                                <button type="button"
                                                                    class="btn btn-sm btn-minus rounded-circle bg-light border change-quantity"
                                                                    data-type="minus">

                                                                    <i class="fa fa-minus"></i>
                                                                </button>


                                                            </div>
                                                            <input type="text"
                                                                class="form-control form-control-sm text-center border-0 quantity-input"
                                                                data-id="${cartdetail.id}"
                                                                data-price="${cartdetail.price}"
                                                                value="${cartdetail.quantity}">
                                                            <div class="input-group-btn">
                                                                <button type="button"
                                                                    class="btn btn-sm btn-plus rounded-circle bg-light border change-quantity"
                                                                    data-type="plus">
                                                                    <i class="fa fa-plus"></i>
                                                                </button>
                                                            </div>
                                                        </div>
                                                </td>
                                                <td>
                                                    <p class="mb-0 text-dark fw-bold product-total"
                                                        data-id="${cartdetail.id}">
                                                        <fmt:formatNumber
                                                            value="${cartdetail.quantity * cartdetail.price}"
                                                            type="number" groupingUsed="true" />
                                                    </p>
                                                </td>


                                                <td>

                                                    <form:form method="post"
                                                        action="/delete-cart-product/${cartdetail.id}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                        <button type="submit"
                                                            class="btn btn-sm btn-danger rounded-circle">
                                                            <i class="fa fa-times"></i>
                                                        </button>
                                                    </form:form>


                                                </td>
                                            </tr>


                                        </c:forEach>


                                    </tbody>
                                </table>
                            </div>

                            <div class="row g-4 mt-5">
                                <div class="col-sm-8 col-md-7 col-lg-6 col-xl-4">
                                    <c:if test="${empty listCartDetails}">
                                        <div class="alert alert-info text-center mt-3" role="alert">
                                            Giỏ hàng của bạn đang trống. Không có giá trị cần thanh toán !
                                        </div>
                                    </c:if>

                                    <c:if test="${not empty listCartDetails}">
                                        <div class="bg-light rounded p-4">
                                            <h2 class="display-6 mb-4 text-center">Tổng Cộng <span class="fw-normal">Giỏ
                                                    Hàng</span></h2>

                                            <c:set var="shippingFee" value="50000" />
                                            <c:set var="sumship" value="${sumPrice + shippingFee}" />

                                            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                                                <h5 class="mb-0 text-dark">Tạm tính:</h5>
                                                <p class="mb-0 text-dark fw-bold" id="subtotal-amount">
                                                    <fmt:formatNumber value="${sumPrice}" type="number"
                                                        groupingUsed="true" />
                                                </p>
                                            </div>

                                            <div class="d-flex justify-content-between mb-3 border-bottom pb-2">
                                                <h5 class="mb-0 text-dark">Phí vận chuyển:</h5>
                                                <p class="mb-0 text-dark fw-bold">
                                                    <fmt:formatNumber value="${shippingFee}" type="number"
                                                        groupingUsed="true" />
                                                </p>
                                            </div>

                                            <p class="text-end text-muted small mb-4" style="font-size:large;">Vận
                                                chuyển
                                                đến theo yêu cầu khách hàng
                                            </p>

                                            <div class="d-flex justify-content-between mb-4">
                                                <h4 class="mb-0 text-dark">Tổng tiền:</h4>
                                                <p class="mb-0 fw-bold text-danger" style="font-size: 1.5em;"
                                                    id="total-amount">
                                                    <fmt:formatNumber value="${sumship}" type="number"
                                                        groupingUsed="true" />
                                                </p>
                                            </div>

                                            <button
                                                class="btn btn-primary btn-block rounded-pill py-3 px-5 text-uppercase fw-bold w-100"
                                                type="button">Tiến
                                                hành Thanh toán</button>
                                        </div>

                                    </c:if>


                                </div>
                            </div>

                        </div>
                    </div>

                    <!-- End Cart -->

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
                        $(document).ready(function () {
                            const shippingFee = 50000;

                            function updateCartTotal() {
                                let subtotal = 0;
                                $('.quantity-input').each(function () {
                                    const quantity = parseInt($(this).val());
                                    const price = parseFloat($(this).data('price'));
                                    subtotal += quantity * price;
                                });

                                $('#subtotal-amount').text(subtotal.toLocaleString());
                                $('#total-amount').text((subtotal + shippingFee).toLocaleString());
                            }

                            $('.change-quantity').off('click').on('click', function (e) {
                                e.preventDefault();
                                e.stopPropagation();

                                const button = $(this);
                                const type = button.data('type');
                                const input = button.closest('.quantity').find('.quantity-input');
                                let quantity = parseInt(input.val());
                                const price = parseFloat(input.data('price'));
                                const id = input.data('id');

                                if (type === 'minus') {
                                    if (quantity > 1) {
                                        quantity -= 1;
                                    }
                                } else if (type === 'plus') {
                                    quantity += 1;
                                }

                                input.val(quantity);

                                const productTotal = quantity * price;
                                $('.product-total[data-id="' + id + '"]').text(productTotal.toLocaleString());

                                updateCartTotal();
                            });
                        });
                    </script>



                </body>

                </html>