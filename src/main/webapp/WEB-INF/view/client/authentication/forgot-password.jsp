<%@ page language="java" contentType="text/html;
charset=UTF-8" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="vi">

            <head>
                <meta charset="UTF-8" />
                <meta name="viewport" content="width=device-width, initial-scale=1.0" />
                <title>Quên Mật Khẩu</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css" rel="stylesheet" />
                <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0/css/all.min.css"
                    rel="stylesheet" />
                <style>
                    body {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        min-height: 100vh;
                        font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                    }

                    .forgot-password-container {
                        min-height: 100vh;
                        display: flex;
                        align-items: center;
                        justify-content: center;
                        padding: 20px;
                    }

                    .forgot-password-card {
                        background: rgba(255, 255, 255, 0.95);
                        border-radius: 20px;
                        box-shadow: 0 20px 40px rgba(0, 0, 0, 0.1);
                        backdrop-filter: blur(10px);
                        border: 1px solid rgba(255, 255, 255, 0.2);
                        overflow: hidden;
                        max-width: 450px;
                        width: 100%;
                    }

                    .card-header {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        color: white;
                        text-align: center;
                        padding: 40px 30px;
                        border: none;
                    }

                    .card-header h3 {
                        margin: 0;
                        font-weight: 300;
                        font-size: 24px;
                    }

                    .card-header i {
                        font-size: 48px;
                        margin-bottom: 15px;
                        opacity: 0.9;
                    }

                    .card-body {
                        padding: 40px 30px;
                    }

                    .form-group {
                        margin-bottom: 25px;
                    }

                    .form-label {
                        font-weight: 600;
                        color: #333;
                        margin-bottom: 8px;
                    }

                    .form-control {
                        border: 2px solid #e1e8ed;
                        border-radius: 10px;
                        padding: 12px 16px;
                        font-size: 16px;
                        transition: all 0.3s ease;
                    }

                    .form-control:focus {
                        border-color: #667eea;
                        box-shadow: 0 0 0 0.2rem rgba(102, 126, 234, 0.25);
                    }

                    .input-group {
                        position: relative;
                    }

                    .input-group-text {
                        background: transparent;
                        border: 2px solid #e1e8ed;
                        border-right: none;
                        border-radius: 10px 0 0 10px;
                        color: #667eea;
                    }

                    .input-group .form-control {
                        border-left: none;
                        border-radius: 0 10px 10px 0;
                    }

                    .btn-primary {
                        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                        border: none;
                        border-radius: 10px;
                        padding: 12px 30px;
                        font-weight: 600;
                        font-size: 16px;
                        width: 100%;
                        transition: all 0.3s ease;
                    }

                    .btn-primary:hover {
                        transform: translateY(-2px);
                        box-shadow: 0 10px 20px rgba(102, 126, 234, 0.3);
                    }

                    .btn-link {
                        color: #667eea;
                        text-decoration: none;
                        font-weight: 500;
                        transition: all 0.3s ease;
                    }

                    .btn-link:hover {
                        color: #764ba2;
                        text-decoration: underline;
                    }

                    .alert {
                        border-radius: 10px;
                        border: none;
                        padding: 15px 20px;
                        margin-bottom: 25px;
                    }

                    .alert-danger {
                        background: linear-gradient(135deg, #ff6b6b 0%, #ee5a24 100%);
                        color: white;
                    }

                    .alert-success {
                        background: linear-gradient(135deg, #51cf66 0%, #40c057 100%);
                        color: white;
                    }

                    .invalid-feedback {
                        display: block;
                        color: #dc3545;
                        font-size: 14px;
                        margin-top: 5px;
                    }

                    .form-control.is-invalid {
                        border-color: #dc3545;
                    }

                    .back-to-login {
                        text-align: center;
                        margin-top: 20px;
                        padding-top: 20px;
                        border-top: 1px solid #e1e8ed;
                    }

                    .description {
                        color: #6c757d;
                        font-size: 14px;
                        margin-bottom: 25px;
                        text-align: center;
                        line-height: 1.5;
                    }

                    @media (max-width: 576px) {
                        .forgot-password-container {
                            padding: 10px;
                        }

                        .card-header {
                            padding: 30px 20px;
                        }

                        .card-body {
                            padding: 30px 20px;
                        }
                    }
                </style>
            </head>

            <body>
                <div class="forgot-password-container">
                    <div class="forgot-password-card">
                        <div class="card-header">
                            <i class="fas fa-key"></i>
                            <h3>Quên Mật Khẩu</h3>
                        </div>

                        <div class="card-body">
                            <p class="description">
                                Nhập địa chỉ email của bạn và chúng tôi sẽ gửi mật khẩu mới đến
                                email của bạn.
                            </p>

                            <!-- Hiển thị thông báo lỗi -->
                            <c:if test="${not empty errorMessage}">
                                <div class="alert alert-danger" role="alert">
                                    <i class="fas fa-exclamation-triangle me-2"></i>
                                    ${errorMessage}
                                </div>
                            </c:if>
                            <c:if test="${not empty successMessage}">
                                <div class="alert alert-success alert-dismissible fade show my-3" role="alert">
                                    <i class="fas fa-check-circle me-2"></i>
                                    ${successMessage}
                                    <button type="button" class="btn-close" data-bs-dismiss="alert"
                                        aria-label="Close"></button>
                                </div>
                            </c:if>


                            <form:form method="post" modelAttribute="forgotPasswordDTO" action="/forgot-password">
                                <div class="form-group">
                                    <label for="email" class="form-label">
                                        <i class="fas fa-envelope me-1"></i>
                                        Địa chỉ Email
                                    </label>
                                    <div class="input-group">
                                        <span class="input-group-text">
                                            <i class="fas fa-at"></i>
                                        </span>
                                        <form:input path="email" type="email"
                                            class="form-control ${not empty errors.email ? 'is-invalid' : ''}"
                                            id="email" placeholder="Nhập địa chỉ email của bạn" autocomplete="email" />
                                    </div>
                                    <form:errors path="email" cssClass="invalid-feedback" />
                                </div>

                                <button type="submit" class="btn btn-primary">
                                    <i class="fas fa-paper-plane me-2"></i>
                                    Gửi Mật Khẩu Mới
                                </button>
                            </form:form>

                            <div class="back-to-login">
                                <p class="mb-0">
                                    Đã nhớ mật khẩu?
                                    <a href="/login" class="btn-link">
                                        <i class="fas fa-sign-in-alt me-1"></i>
                                        Đăng nhập ngay
                                    </a>
                                </p>
                            </div>
                        </div>
                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/js/bootstrap.bundle.min.js"></script>
                <script>
                    // Thêm hiệu ứng loading khi submit form
                    document.querySelector('form').addEventListener('submit', function (e) {
                        const submitBtn = document.querySelector('button[type="submit"]');
                        submitBtn.innerHTML =
                            '<i class="fas fa-spinner fa-spin me-2"></i>Đang gửi...';
                        submitBtn.disabled = true;
                    });

                    // Auto focus vào input email
                    document.getElementById('email').focus();
                </script>
            </body>

            </html>