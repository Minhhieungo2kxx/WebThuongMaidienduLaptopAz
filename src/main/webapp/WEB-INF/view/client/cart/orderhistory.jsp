<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
                        <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

                              <!DOCTYPE html>
                              <html lang="vi">

                              <head>
                                    <meta charset="UTF-8">
                                    <meta name="viewport" content="width=device-width, initial-scale=1.0">
                                    <title>Cập nhật thông tin người dùng - FPT Shop</title>

                                    <link rel="preconnect" href="https://fonts.googleapis.com">
                                    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                                    <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                                          rel="stylesheet">
                                    <link rel="stylesheet"
                                          href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                                    <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                                          rel="stylesheet">
                                    <link href="/client/css/bootstrap.min.css" rel="stylesheet">
                                    <link href="/client/css/style.css" rel="stylesheet">

                                    <style>
                                          .order-history-container {
                                                background: #fff;
                                                border-radius: 10px;
                                                box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                                                padding: 2rem;
                                                margin-bottom: 2rem;
                                          }

                                          .order-history-container h3 {
                                                font-family: 'Raleway', sans-serif;
                                                font-weight: 700;
                                                color: #1a1a1a;
                                          }

                                          .table-order {
                                                border-collapse: separate;
                                                border-spacing: 0;
                                                width: 100%;
                                                background-color: #fff;
                                          }

                                          .table-order th {
                                                background-color: #007bff;
                                                color: white;
                                                font-weight: 600;
                                                padding: 1rem;
                                                text-align: center;
                                          }

                                          .table-order td {
                                                padding: 1rem;
                                                vertical-align: middle;
                                                text-align: center;
                                          }

                                          .table-order tr:nth-child(even) {
                                                background-color: #f8f9fa;
                                          }

                                          .table-order tr:hover {
                                                background-color: #e9ecef;
                                                transition: background-color 0.3s ease;
                                          }

                                          .product-image {
                                                max-width: 80px;
                                                max-height: 80px;
                                                border-radius: 5px;
                                                object-fit: cover;
                                          }

                                          .status-badge {
                                                padding: 0.5rem 1rem;
                                                border-radius: 20px;
                                                font-size: 0.9rem;
                                                font-weight: 500;
                                          }

                                          .status-pending {
                                                background-color: #ffc107;
                                                color: #fff;
                                          }

                                          .status-completed {
                                                background-color: #28a745;
                                                color: #fff;
                                          }

                                          .status-cancelled {
                                                background-color: #dc3545;
                                                color: #fff;
                                          }

                                          .status-shipping {
                                                background-color: #dc3545;
                                                color: #fff;
                                          }

                                          .empty-message {
                                                font-family: 'Open Sans', sans-serif;
                                                font-size: 1.1rem;
                                                color: #6c757d;
                                                padding: 2rem;
                                                text-align: center;
                                                background-color: #f8f9fa;
                                                border-radius: 10px;
                                          }

                                          .order-id {
                                                font-weight: 600;
                                                color: #007bff;
                                          }

                                          hr.divider {
                                                border-top: 2px solid #e9ecef;
                                                margin: 1.5rem 0;
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

                                          .fade-out {
                                                animation: fadeOut 0.6s forwards;
                                          }

                                          @keyframes fadeOut {
                                                from {
                                                      opacity: 1;
                                                      transform: translateX(0);
                                                }

                                                to {
                                                      opacity: 0;
                                                      transform: translateX(20px);
                                                }
                                          }

                                          .order-block-fade-out {
                                                animation: fadeOutOrder 0.6s forwards;
                                          }

                                          @keyframes fadeOutOrder {
                                                from {
                                                      opacity: 1;
                                                }

                                                to {
                                                      opacity: 0;
                                                      height: 0;
                                                      margin: 0;
                                                      padding: 0;
                                                }
                                          }
                                    </style>

                              </head>

                              <body>
                                    <jsp:include page="../layout/header.jsp" />

                                    <div class="container-fluid px-4" style="padding-top: 5.5rem;">
                                          <h1 class="mt-4">Home</h1>
                                          <ol class="breadcrumb mb-4">
                                                <h5 style="color: red;">Order/History</h5>
                                          </ol>
                                          <div class="row">
                                                <div class="container mt-5">
                                                      <div class="row justify-content-center">
                                                            <div class="col-12">
                                                                  <div class="order-history-container">
                                                                        <div
                                                                              class="d-flex justify-content-between align-items-center mb-4">
                                                                              <h3>Lịch Sử Đơn Hàng</h3>
                                                                        </div>
                                                                        <hr class="divider" />

                                                                        <c:if test="${not empty successMessage}">
                                                                              <div class="alert alert-success alert-dismissible fade show"
                                                                                    role="alert">
                                                                                    ${successMessage}
                                                                                    <button type="button"
                                                                                          class="btn-close"
                                                                                          data-bs-dismiss="alert"
                                                                                          aria-label="Close"></button>
                                                                              </div>
                                                                        </c:if>

                                                                        <c:if test="${empty listOrderbyUser}">
                                                                              <div class="empty-message">
                                                                                    <h5>Hiện tại lịch sử mua hàng đang
                                                                                          rỗng.
                                                                                          Vui lòng mua hàng để hiển thị
                                                                                          lịch
                                                                                          sử tại đây!</h5>
                                                                              </div>
                                                                        </c:if>

                                                                        <c:if test="${not empty listOrderbyUser}">
                                                                              <div class="table-responsive">
                                                                                    <table
                                                                                          class="table table-bordered table-hover align-middle table-order">
                                                                                          <thead>
                                                                                                <tr>
                                                                                                      <th>Sản phẩm</th>
                                                                                                      <th>Tên sản phẩm
                                                                                                      </th>
                                                                                                      <th>Giá sản phẩm
                                                                                                      </th>
                                                                                                      <th>Số lượng</th>
                                                                                                      <th>Thành tiền
                                                                                                            (phí
                                                                                                            ship)</th>
                                                                                                      <th>Ngày thanh
                                                                                                            toán</th>
                                                                                                      <th>Trạng thái đơn
                                                                                                            hàng:</th>
                                                                                                      <th>Trạng thái
                                                                                                            thanh toán:
                                                                                                      </th>
                                                                                                      <th>Loại thanh
                                                                                                            toán:</th>
                                                                                                      <th>Thao tác</th>
                                                                                                </tr>
                                                                                          </thead>
                                                                                          <tbody>
                                                                                                <c:forEach
                                                                                                      items="${listOrderbyUser}"
                                                                                                      var="order">

                                                                                                      <tr
                                                                                                            id="order-block-${order.id}">
                                                                                                            <td
                                                                                                                  class="order-id">
                                                                                                                  Đơn
                                                                                                                  hàng
                                                                                                                  ${order.id}
                                                                                                            </td>

                                                                                                            <td></td>

                                                                                                            <td>
                                                                                                                  <span
                                                                                                                        id="total-price-${order.id}">
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${order.totalPrice}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />
                                                                                                                  </span>
                                                                                                                  VNĐ
                                                                                                            </td>


                                                                                                            <td></td>

                                                                                                            <td
                                                                                                                  style="color:red;font-size:1em;">
                                                                                                                  <span
                                                                                                                        id="total-price-ship-${order.id}">
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${order.totalPriceaddShip}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />
                                                                                                                  </span>
                                                                                                                  VNĐ
                                                                                                            </td>


                                                                                                            <td
                                                                                                                  style="color:red;font-size:1em;">
                                                                                                                  <fmt:parseDate
                                                                                                                        value="${order.paymentTime}"
                                                                                                                        var="parsedDate"
                                                                                                                        pattern="yyyyMMddHHmmss" />

                                                                                                                  <fmt:formatDate
                                                                                                                        value="${parsedDate}"
                                                                                                                        pattern="dd/MM/yyyy HH:mm:ss" />
                                                                                                            </td>

                                                                                                            <td
                                                                                                                  style="color:red;font-size:1.2em;">
                                                                                                                  <span
                                                                                                                        class="status-badge status-${order.status.toLowerCase()}">
                                                                                                                        ${order.status}
                                                                                                                  </span>
                                                                                                            </td>
                                                                                                            <td
                                                                                                                  style="color:red;font-size:1.2em;">
                                                                                                                  <span
                                                                                                                        class="status-badge status-${order.status.toLowerCase()}">
                                                                                                                        ${order.paymentStatus}
                                                                                                                  </span>
                                                                                                            </td>
                                                                                                            <td
                                                                                                                  style="color:red;font-size:1.2em;">
                                                                                                                  <span
                                                                                                                        class="status-badge status-${order.status.toLowerCase()}">
                                                                                                                        ${order.paymentMethod}
                                                                                                                  </span>
                                                                                                            </td>
                                                                                                      </tr>
                                                                                                      <c:forEach
                                                                                                            items="${order.orderDetails}"
                                                                                                            var="orderdetail">


                                                                                                            <tr
                                                                                                                  id="order-detail-row-${orderdetail.id}">


                                                                                                                  <td>
                                                                                                                        <img src="/uploads/products/${orderdetail.product.image}"
                                                                                                                              alt="Product Avatar"
                                                                                                                              class="product-image" />
                                                                                                                  </td>

                                                                                                                  <td>${orderdetail.product.name}
                                                                                                                  </td>

                                                                                                                  <td>
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${orderdetail.price}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />
                                                                                                                        VNĐ
                                                                                                                  </td>

                                                                                                                  <td>${orderdetail.quantity}
                                                                                                                  </td>

                                                                                                                  <td>
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${orderdetail.totalPrice}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />
                                                                                                                        VNĐ
                                                                                                                  </td>
                                                                                                                  <td>
                                                                                                                  </td>
                                                                                                                  <td>
                                                                                                                  </td>
                                                                                                                  <td>
                                                                                                                  </td>
                                                                                                                  <td>
                                                                                                                  </td>

                                                                                                                  <td>
                                                                                                                        <!-- Biến kiểm tra cho phép hủy -->
                                                                                                                        <c:set var="allowCancel"
                                                                                                                              value="false" />

                                                                                                                        <c:choose>


                                                                                                                              <c:when
                                                                                                                                    test="${order.paymentMethod == 'COD'}">
                                                                                                                                    <c:if
                                                                                                                                          test="${order.status == 'Pending'}">
                                                                                                                                          <c:set var="allowCancel"
                                                                                                                                                value="true" />
                                                                                                                                    </c:if>
                                                                                                                              </c:when>



                                                                                                                              <c:otherwise>


                                                                                                                                    <c:if
                                                                                                                                          test="${order.paymentStatus == 'Unpaid'}">
                                                                                                                                          <c:set var="allowCancel"
                                                                                                                                                value="true" />
                                                                                                                                    </c:if>


                                                                                                                                    <c:if
                                                                                                                                          test="${order.paymentStatus == 'Paid' && order.status == 'Pending'}">
                                                                                                                                          <c:set var="allowCancel"
                                                                                                                                                value="true" />
                                                                                                                                    </c:if>

                                                                                                                              </c:otherwise>

                                                                                                                        </c:choose>



                                                                                                                        <!-- Nút Hủy -->
                                                                                                                        <c:if
                                                                                                                              test="${allowCancel}">
                                                                                                                              <button
                                                                                                                                    class="btn btn-danger btn-sm btn-cancel-order-detail"
                                                                                                                                    data-id="${orderdetail.id}"
                                                                                                                                    data-order-id="${order.id}">
                                                                                                                                    <span
                                                                                                                                          class="btn-text">Hủy</span>
                                                                                                                                    <span class="spinner-border spinner-border-sm d-none"
                                                                                                                                          role="status"></span>
                                                                                                                              </button>
                                                                                                                        </c:if>

                                                                                                                  </td>

                                                                                                            </tr>


                                                                                                      </c:forEach>

                                                                                                </c:forEach>
                                                                                          </tbody>
                                                                                    </table>
                                                                              </div>
                                                                        </c:if>
                                                                  </div>
                                                            </div>
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

                                    <jsp:include page="../layout/footer.jsp" />

                                    <script
                                          src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                                    <script
                                          src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                                    <script src="/client/lib/easing/easing.min.js"></script>
                                    <script src="/client/lib/waypoints/waypoints.min.js"></script>
                                    <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                                    <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
                                    <script src="/client/js/main.js"></script>
                                    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>

                                    <script>
                                          $(document).on("click", ".btn-cancel-order-detail", function () {

                                                let btn = $(this);
                                                let id = btn.data("id");
                                                let orderId = btn.data("order-id");

                                                if (!confirm("Bạn có chắc muốn hủy sản phẩm này?")) return;

                                                // Hiện spinner
                                                btn.find(".btn-text").addClass("d-none");
                                                btn.find(".spinner-border").removeClass("d-none");
                                                btn.prop("disabled", true);

                                                $.ajax({
                                                      url: "/api/order-detail/cancel/" + id,
                                                      method: "POST",
                                                      success: function (resp) {

                                                            // 1️ Animation fade-out cho dòng OrderDetail
                                                            $("#order-detail-row-" + id)
                                                                  .addClass("fade-out")
                                                                  .delay(600)
                                                                  .queue(function (next) {
                                                                        $(this).remove();
                                                                        next();
                                                                  });

                                                            // 2️ Nếu Order bị xóa hoàn toàn
                                                            if (resp.orderDeleted) {
                                                                  $("#order-block-" + orderId)
                                                                        .addClass("order-block-fade-out")
                                                                        .delay(600)
                                                                        .queue(function (next) {
                                                                              $(this).remove();
                                                                              next();
                                                                        });
                                                                  return;
                                                            }

                                                            // 3️ Cập nhật tổng tiền
                                                            $("#total-price-" + orderId)
                                                                  .text(resp.newTotal.toLocaleString());

                                                            $("#total-price-ship-" + orderId)
                                                                  .text(resp.newTotalShip.toLocaleString());
                                                      },

                                                      error: function (xhr) {
                                                            alert(xhr.responseJSON.error);
                                                      },

                                                      complete: function () {
                                                            // Tắt spinner, bật lại nút
                                                            btn.find(".spinner-border").addClass("d-none");
                                                            btn.find(".btn-text").removeClass("d-none");
                                                            btn.prop("disabled", false);
                                                      }
                                                });

                                          });
                                    </script>


                              </body>

                              </html>