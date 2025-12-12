<%@ page contentType="text/html;charset=UTF-8" language="java" %>
      <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                  <meta charset="UTF-8" />
                  <title>Thanh toán thành công</title>
                  <link rel="stylesheet"
                        href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" />
            </head>

            <body style="background: #f4f7f9">
                  <div class="container mt-5">
                        <div class="card shadow-lg p-4">
                              <div class="text-center">
                                    <img src="https://upload.wikimedia.org/wikipedia/vi/f/fe/MoMo_Logo.png" width="120"
                                          class="mb-3" />

                                    <h2 class="text-success font-weight-bold">
                                          Thanh toán MoMo thành công!
                                    </h2>
                                    <p class="text-muted">
                                          Cảm ơn bạn đã thanh toán tại cửa hàng của chúng tôi.
                                    </p>
                              </div>

                              <hr />

                              <div class="mt-3">
                                    <h5><strong>Thông tin giao dịch:</strong></h5>

                                    <p><strong>Mã đơn hàng:</strong> ${orderId}</p>
                                    <p><strong>Mã giao dịch MoMo:</strong> ${transId}</p>
                                    <p>
                                          <strong>Số tiền:</strong>
                                          <span class="text-danger font-weight-bold">${amount}</span>
                                    </p>
                                    <p><strong>Nội dung thanh toán:</strong> ${orderInfo}</p>
                                    <p><strong>Loại thanh toán:</strong> ${payType}</p>
                                    <p>
                                          <strong>Thời gian thanh toán:</strong>
                                          <fmt:parseDate value="${paymentTime}" var="parsedDate"
                                                pattern="yyyyMMddHHmmss" />
                                          <fmt:formatDate value="${parsedDate}" pattern="dd/MM/yyyy HH:mm:ss" />
                                    </p>
                              </div>

                              <div class="text-center mt-4">
                                    <a href="/" class="btn btn-primary px-4">Về trang chủ</a>
                                    <a href="/order-history" class="btn btn-outline-success px-4">Xem đơn hàng</a>
                              </div>
                        </div>
                  </div>
            </body>

            </html>