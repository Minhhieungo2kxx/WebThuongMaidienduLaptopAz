<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

            <!DOCTYPE html>
            <html lang="vi">

            <head>
                  <meta charset="UTF-8">
                  <meta name="viewport" content="width=device-width, initial-scale=1.0">
                  <title>Thanh toán thất bại</title>
                  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet">
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
                                                      <i class="fas fa-times-circle text-danger"
                                                            style="font-size: 5rem;"></i>
                                                </div>

                                                <h2 class="text-danger mb-4">Thanh toán thất bại!</h2>

                                                <div class="alert alert-danger" role="alert">
                                                      <h5 class="alert-heading">Đã có lỗi xảy ra!</h5>
                                                      <p class="mb-0">
                                                            <c:choose>
                                                                  <c:when test="${not empty message}">
                                                                        ${message}
                                                                  </c:when>
                                                                  <c:otherwise>
                                                                        Một lỗi không xác định đã xảy ra. Vui lòng thử
                                                                        lại sau.
                                                                  </c:otherwise>
                                                            </c:choose>
                                                      </p>
                                                </div>

                                                <a href="/" class="btn btn-primary mt-4">
                                                      Quay lại trang trang trủ
                                                </a>
                                          </div>
                                    </div>
                              </div>
                        </div>
                  </div>

                  <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
            </body>

            </html>