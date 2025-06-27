<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                  <!DOCTYPE html>
                  <html lang="vi">

                  <head>
                        <meta charset="UTF-8">
                        <meta name="viewport" content="width=device-width, initial-scale=1.0">
                        <title>Thanh toán thành công</title>
                        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css"
                              rel="stylesheet">
                        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                              rel="stylesheet">
                  </head>

                  <body>
                        <div class="container mt-5">
                              <div class="row justify-content-center">
                                    <div class="col-md-8">
                                          <div class="card shadow">
                                                <div class="card-body text-center p-5">
                                                      <div class="mb-4">
                                                            <i class="fas fa-check-circle text-success"
                                                                  style="font-size: 5rem;"></i>
                                                      </div>

                                                      <h2 class="text-success mb-4">Thanh toán thành công!</h2>

                                                      <div class="alert alert-success" role="alert">
                                                            <h5 class="alert-heading">Cảm ơn bạn đã mua hàng!</h5>
                                                            <p class="mb-0">Đơn hàng của bạn đã được xử lý thành công.
                                                            </p>
                                                      </div>

                                                      <div class="row">
                                                            <div class="col-md-6">
                                                                  <div class="card bg-light">
                                                                        <div class="card-body">
                                                                              <h6 class="card-title text-muted">Mã giao
                                                                                    dịch</h6>
                                                                              <p class="card-text fw-bold">
                                                                                    ${transactionId}</p>
                                                                        </div>
                                                                  </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                  <div class="card bg-light">
                                                                        <div class="card-body">
                                                                              <h6 class="card-title text-muted">Tổng
                                                                                    tiền</h6>
                                                                              <p class="card-text fw-bold text-danger">
                                                                                    <fmt:formatNumber
                                                                                          value="${totalPrice/100}"
                                                                                          type="number"
                                                                                          groupingUsed="true" /> VNĐ
                                                                              </p>
                                                                        </div>
                                                                  </div>
                                                            </div>
                                                      </div>

                                                      <c:if test="${not empty paymentTime}">
                                                            <div class="mt-3">
                                                                  <small class="text-muted">
                                                                        Thời gian thanh toán:
                                                                        <fmt:parseDate value="${paymentTime}"
                                                                              var="parsedDate"
                                                                              pattern="yyyyMMddHHmmss" />
                                                                        <fmt:formatDate value="${parsedDate}"
                                                                              pattern="dd/MM/yyyy HH:mm:ss" />
                                                                  </small>
                                                            </div>
                                                      </c:if>

                                                      <div class="mt-5">
                                                            <a href="/" class="btn btn-primary me-3">
                                                                  <i class="fas fa-home"></i> Về trang chủ
                                                            </a>
                                                            <a href="/order-history" class="btn btn-outline-primary">
                                                                  <i class="fas fa-list"></i> Xem đơn hàng
                                                            </a>
                                                      </div>

                                                      <div class="mt-4">
                                                            <p class="text-muted small">
                                                                  <i class="fas fa-info-circle"></i>
                                                                  Nhân viên cửa hàng FPT SHOP sẽ trao đổi xác nhận đơn
                                                                  hàng trong vài
                                                                  phút tới.Hẹn gặp lại !
                                                            </p>
                                                      </div>
                                                </div>
                                          </div>
                                    </div>
                              </div>
                        </div>

                        <script
                              src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
                  </body>

                  </html>