<%@ page contentType="text/html;charset=UTF-8" language="java" %>
      <!DOCTYPE html>
      <html lang="vi">

      <head>
            <meta charset="UTF-8">
            <title>Thanh toán thất bại</title>
            <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
      </head>

      <body style="background: #f4f7f9">

            <div class="container mt-5">
                  <div class="card shadow-lg p-4">
                        <div class="text-center">
                              <img src="https://upload.wikimedia.org/wikipedia/vi/f/fe/MoMo_Logo.png" width="120"
                                    class="mb-3">

                              <h2 class="text-danger font-weight-bold">Thanh toán MoMo thất bại!</h2>
                              <p class="text-muted">Rất tiếc, giao dịch của bạn chưa được xử lý.</p>
                        </div>

                        <hr>

                        <div class="mt-3">
                              <h5><strong>Thông báo lỗi:</strong></h5>
                              <p class="text-danger">${message}</p>
                        </div>

                        <div class="text-center mt-4">
                              <a href="/" class="btn btn-primary px-4">Về trang chủ</a>
                              <a href="/cart" class="btn btn-outline-danger px-4">Thử lại thanh toán</a>
                        </div>
                  </div>
            </div>

      </body>

      </html>