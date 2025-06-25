<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

                        <!DOCTYPE html>
                        <html lang="en">

                        <head>
                              <meta charset="UTF-8">
                              <meta name="viewport" content="width=device-width, initial-scale=1.0">
                              <title>Thanh Toán Giỏ Hàng - Cửa Hàng Trực Tuyến</title>

                              <link rel="preconnect" href="https://fonts.googleapis.com">
                              <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                              <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                                    rel="stylesheet">

                              <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                              <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                                    rel="stylesheet">

                              <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                              <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                              <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                              <link href="/client/css/style.css" rel="stylesheet">

                              <style>
                                    body {
                                          background-color: #f8f9fa;
                                          /* Light background for a cleaner look */
                                    }

                                    .page-header {
                                          background: linear-gradient(rgba(0, 0, 0, 0.5), rgba(0, 0, 0, 0.5)), url('/client/img/header-bg.jpg') no-repeat center center;
                                          /* Add a background image if available */
                                          background-size: cover;
                                          min-height: 250px;
                                          /* Adjust as needed */
                                          display: flex;
                                          align-items: center;
                                          justify-content: center;
                                          flex-direction: column;
                                          color: #fff;
                                          position: relative;
                                    }

                                    .page-header h1 {
                                          font-size: 3.5rem;
                                          font-weight: 800;
                                          margin-bottom: 10px;
                                    }

                                    .breadcrumb-item a {
                                          color: #e9ecef;
                                    }

                                    .breadcrumb-item.active {
                                          color: #fff;
                                    }

                                    .section-title {
                                          font-size: 2.2rem;
                                          font-weight: 700;
                                          color: #343a40;
                                          /* Darker color for headings */
                                          border-bottom: 2px solid #007bff;
                                          /* Underline effect */
                                          padding-bottom: 10px;
                                          margin-bottom: 30px !important;
                                    }

                                    .table thead th {
                                          background-color: #007bff;
                                          /* Primary color for table header */
                                          color: #fff;
                                          border-bottom: none;
                                          font-weight: 600;
                                    }

                                    .table-hover tbody tr:hover {
                                          background-color: #e2f2ff;
                                          /* Lighter hover effect */
                                    }

                                    .product-image {
                                          width: 80px;
                                          height: 80px;
                                          object-fit: cover;
                                          border-radius: 8px;
                                          border: 1px solid #ddd;
                                    }

                                    .input-group.quantity {
                                          width: 120px;
                                          /* Slightly wider for better appearance */
                                    }

                                    .form-control:read-only {
                                          background-color: #e9ecef;
                                          /* Distinct background for read-only fields */
                                          cursor: not-allowed;
                                    }

                                    .info-section {
                                          background-color: #ffffff;
                                          padding: 30px;
                                          border-radius: 12px;
                                          box-shadow: 0 4px 15px rgba(0, 0, 0, 0.08);
                                          /* Softer shadow */
                                          margin-bottom: 2rem;
                                    }

                                    .info-section h2 {
                                          font-size: 2rem;
                                          color: #007bff;
                                          /* Primary color for section titles */
                                          margin-bottom: 25px;
                                    }

                                    .form-label {
                                          font-weight: 600;
                                          color: #495057;
                                    }

                                    .form-control {
                                          border-radius: 8px;
                                          padding: 10px 15px;
                                          border: 1px solid #ced4da;
                                    }

                                    .form-control:focus {
                                          border-color: #80bdff;
                                          box-shadow: 0 0 0 0.25rem rgba(0, 123, 255, 0.25);
                                    }

                                    .alert-info {
                                          background-color: #e0f7fa;
                                          color: #007bff;
                                          border-color: #007bff;
                                          border-radius: 8px;
                                          padding: 15px;
                                          font-size: 1.1rem;
                                    }

                                    .text-danger.fw-bold {
                                          font-size: 1.8em !important;
                                          /* Emphasize total price */
                                          color: #dc3545 !important;
                                    }

                                    .btn-primary {
                                          background-color: #007bff;
                                          border-color: #007bff;
                                          padding: 12px 25px;
                                          font-size: 1.2rem;
                                          border-radius: 8px;
                                          transition: background-color 0.3s ease, border-color 0.3s ease, transform 0.2s ease;
                                    }

                                    .btn-primary:hover {
                                          background-color: #0056b3;
                                          border-color: #0056b3;
                                          transform: translateY(-2px);
                                    }

                                    .border-top {
                                          border-color: #dee2e6 !important;
                                    }

                                    .error {
                                          color: #dc3545;
                                          font-size: 0.9rem;
                                          margin-top: 5px;
                                    }
                              </style>
                        </head>

                        <body>
                              <div id="spinner"
                                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                                    <div class="spinner-grow text-primary" role="status"></div>
                              </div>

                              <jsp:include page="../layout/header.jsp" />

                              <div class="container-fluid page-header py-5">
                                    <h1 class="text-white display-6 animated fadeInDown">Thanh Toán Giỏ Hàng</h1>
                                    <ol class="breadcrumb justify-content-center mb-0 animated fadeInUp">
                                          <li class="breadcrumb-item"><a href="/">Trang chủ</a></li>
                                          <li class="breadcrumb-item active text-white">Thanh toán</li>
                                    </ol>
                              </div>

                              <div class="container-fluid py-5">
                                    <div class="container py-5">
                                          <div class="row g-5">
                                                <div class="col-lg-7">
                                                      <h2 class="section-title text-start">Chi Tiết Giỏ Hàng</h2>
                                                      <div class="table-responsive mb-5">
                                                            <table class="table table-hover align-middle">
                                                                  <thead>
                                                                        <tr>
                                                                              <th scope="col">Sản phẩm</th>
                                                                              <th scope="col">Tên sản phẩm</th>
                                                                              <th scope="col">Giá</th>
                                                                              <th scope="col">Số lượng</th>
                                                                              <th scope="col">Tổng cộng</th>
                                                                        </tr>
                                                                  </thead>
                                                                  <tbody>
                                                                        <c:if test="${empty listCartDetails}">
                                                                              <tr>
                                                                                    <td colspan="5"
                                                                                          class="text-center py-4">
                                                                                          <div class="alert alert-info mb-0"
                                                                                                role="alert">
                                                                                                Giỏ hàng của bạn đang
                                                                                                trống. Hãy thêm sản phẩm
                                                                                                vào nhé!
                                                                                          </div>
                                                                                    </td>
                                                                              </tr>
                                                                        </c:if>
                                                                        <c:forEach var="cartdetail"
                                                                              items="${listCartDetails}">
                                                                              <tr>
                                                                                    <td>
                                                                                          <div
                                                                                                class="d-flex align-items-center">
                                                                                                <img src="/uploads/products/${cartdetail.product.image}"
                                                                                                      class="img-fluid rounded product-image"
                                                                                                      alt="${cartdetail.product.name}">
                                                                                          </div>
                                                                                    </td>
                                                                                    <td>
                                                                                          <a href="/product/detail/${cartdetail.product.id}"
                                                                                                class="text-dark text-decoration-none fw-bold">
                                                                                                <p class="mb-0">
                                                                                                      ${cartdetail.product.name}
                                                                                                </p>
                                                                                          </a>
                                                                                    </td>
                                                                                    <td>
                                                                                          <p
                                                                                                class="mb-0 text-dark fw-bold">
                                                                                                <fmt:formatNumber
                                                                                                      value="${cartdetail.price}"
                                                                                                      type="number"
                                                                                                      groupingUsed="true" />
                                                                                                VNĐ
                                                                                          </p>
                                                                                    </td>
                                                                                    <td>
                                                                                          <div
                                                                                                class="input-group quantity">
                                                                                                <input type="text"
                                                                                                      class="form-control form-control-sm text-center border"
                                                                                                      value="${cartdetail.quantity}"
                                                                                                      readonly="true">
                                                                                          </div>
                                                                                    </td>
                                                                                    <td>
                                                                                          <p class="mb-0 text-dark fw-bold product-total"
                                                                                                data-id="${cartdetail.id}">
                                                                                                <fmt:formatNumber
                                                                                                      value="${cartdetail.quantity * cartdetail.price}"
                                                                                                      type="number"
                                                                                                      groupingUsed="true" />
                                                                                                VNĐ
                                                                                          </p>
                                                                                    </td>
                                                                              </tr>
                                                                        </c:forEach>
                                                                  </tbody>
                                                            </table>
                                                      </div>
                                                </div>

                                                <div class="col-lg-5">
                                                      <form:form method="post" action="/place-order"
                                                            modelAttribute="PaymentDefault">

                                                            <div class="info-section mb-4">
                                                                  <h2 class="text-center">Thông tin người nhận</h2>
                                                                  <div class="form-item mb-3">
                                                                        <label for="receiverName"
                                                                              class="form-label my-2">Tên người nhận
                                                                              <sup class="text-danger">*</sup></label>
                                                                        <form:input type="text" class="form-control"
                                                                              id="receiverName" path="receiverName"
                                                                              placeholder="Nhập tên người nhận" />
                                                                        <form:errors path="receiverName"
                                                                              cssClass="error" element="div" />
                                                                  </div>
                                                                  <div class="form-item mb-4">
                                                                        <label for="receiverAddress"
                                                                              class="form-label my-2">Địa chỉ <sup
                                                                                    class="text-danger">*</sup></label>
                                                                        <form:input type="text" class="form-control"
                                                                              id="receiverAddress"
                                                                              path="receiverAddress"
                                                                              placeholder="Nhập địa chỉ" />
                                                                        <form:errors path="receiverAddress"
                                                                              cssClass="error" element="div" />
                                                                  </div>
                                                                  <div class="form-item mb-4">
                                                                        <label for="receiverPhone"
                                                                              class="form-label my-2">Số điện thoại <sup
                                                                                    class="text-danger">*</sup></label>
                                                                        <form:input type="text" class="form-control"
                                                                              id="receiverPhone" path="receiverPhone"
                                                                              placeholder="Nhập số điện thoại" />
                                                                        <form:errors path="receiverPhone"
                                                                              cssClass="error" element="div" />
                                                                  </div>
                                                                  <div class="text-center mt-3">
                                                                        <a href="/cart"
                                                                              class="text-decoration-none text-primary fw-bold hover-link">
                                                                              &larr; Quay lại giỏ hàng
                                                                        </a>
                                                                  </div>
                                                            </div>

                                                            <div class="info-section">
                                                                  <c:if test="${empty listCartDetails}">
                                                                        <div class="alert alert-info text-center"
                                                                              role="alert">
                                                                              Giỏ hàng của bạn đang trống. Không có giá
                                                                              trị cần thanh toán!
                                                                        </div>
                                                                  </c:if>

                                                                  <c:if test="${not empty listCartDetails}">
                                                                        <h2 class="text-center">Thông tin <span
                                                                                    class="fw-normal">Thanh Toán</span>
                                                                        </h2>

                                                                        <c:set var="shippingFee" value="50000" />
                                                                        <c:set var="sumship"
                                                                              value="${sumPrice + shippingFee}" />
                                                                        <input type="hidden" name="summoney"
                                                                              value="${sumPrice + shippingFee}" />

                                                                        <div
                                                                              class="d-flex justify-content-between mb-2">
                                                                              <h5 class="mb-0 text-dark">Tạm tính:</h5>
                                                                              <p class="mb-0 text-dark fw-bold">
                                                                                    <fmt:formatNumber
                                                                                          value="${sumPrice}"
                                                                                          type="number"
                                                                                          groupingUsed="true" />
                                                                                    VNĐ
                                                                              </p>
                                                                        </div>

                                                                        <div
                                                                              class="d-flex justify-content-between mb-2">
                                                                              <h5 class="mb-0 text-dark">Phí vận chuyển:
                                                                              </h5>
                                                                              <p class="mb-0 text-dark fw-bold">
                                                                                    <fmt:formatNumber
                                                                                          value="${shippingFee}"
                                                                                          type="number"
                                                                                          groupingUsed="true" />
                                                                                    VNĐ
                                                                              </p>

                                                                        </div>

                                                                        <div
                                                                              class="d-flex justify-content-between align-items-center mb-4">
                                                                              <h5 class="mb-0 text-dark">Hình thức vận
                                                                                    chuyển:</h5>
                                                                              <p class="text-end text-muted small mb-0"
                                                                                    style="font-size:1.05rem;">Thanh
                                                                                    toán khi nhận hàng</p>
                                                                        </div>

                                                                        <div
                                                                              class="d-flex justify-content-between align-items-center mb-4 pt-3 border-top">
                                                                              <h4 class="mb-0 text-dark">Tổng tiền:</h4>
                                                                              <p class="mb-0 fw-bold text-danger"
                                                                                    style="font-size: 1.8em;">
                                                                                    <fmt:formatNumber value="${sumship}"
                                                                                          type="number"
                                                                                          groupingUsed="true" />
                                                                                    VNĐ
                                                                              </p>
                                                                        </div>

                                                                        <button type="submit"
                                                                              class="btn btn-primary btn-block w-100">
                                                                              Xác nhận Thanh toán
                                                                        </button>
                                                                  </c:if>
                                                            </div>
                                                      </form:form>
                                                </div>
                                          </div>
                                    </div>
                              </div>

                              <jsp:include page="../layout/footer.jsp" />

                              <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                                          class="fa fa-arrow-up"></i></a>

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