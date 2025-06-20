<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
                  <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

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
                              <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                              <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                                    rel="stylesheet">
                              <link href="/client/css/bootstrap.min.css" rel="stylesheet">
                              <link href="/client/css/style.css" rel="stylesheet">

                              <style>
                                    .form-container {
                                          background: #fff;
                                          padding: 30px;
                                          border-radius: 10px;
                                          box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
                                    }

                                    .form-label {
                                          font-weight: 600;
                                          color: #333;
                                    }

                                    .form-control {
                                          border-radius: 5px;
                                          border: 1px solid #ced4da;
                                    }

                                    .form-control:focus {
                                          box-shadow: 0 0 5px rgba(0, 123, 255, 0.3);
                                          border-color: #007bff;
                                    }

                                    .avatar-preview {
                                          border: 2px solid #e9ecef;
                                          border-radius: 50%;
                                          padding: 5px;
                                          background: #f8f9fa;
                                          display: block;
                                          margin-left: auto;
                                          margin-right: auto;
                                    }

                                    .btn-primary {
                                          background-color: #007bff;
                                          border: none;
                                          padding: 10px;
                                          border-radius: 5px;
                                          font-weight: 600;
                                    }

                                    .btn-primary:hover {
                                          background-color: #0056b3;
                                    }

                                    .error {
                                          color: red;
                                          font-size: 0.9em;
                                          margin-top: 5px;
                                          display: block;
                                    }
                              </style>
                        </head>

                        <body>
                              <jsp:include page="../layout/header.jsp" />
                              <div id="layoutSidenav_content">
                                    <main>
                                          <div class="container-fluid px-4">
                                                <h2 class="mt-4">Dashboard</h2>
                                                <ol class="breadcrumb mb-4">
                                                      <li class="breadcrumb-item"><a href="/admin">Admin</a></li>
                                                      <li class="breadcrumb-item active">Cập nhật người dùng</li>
                                                </ol>
                                                <div class="row justify-content-center">
                                                      <div class="col-md-6 col-12">
                                                            <div class="form-container">
                                                                  <h3 class="mb-4 text-center">Cập nhật thông tin người
                                                                        dùng</h3>
                                                                  <hr class="mb-4" />

                                                                  <form:form method="post" action="/setting-user"
                                                                        modelAttribute="Userupdate"
                                                                        enctype="multipart/form-data">
                                                                        <form:input type="hidden" path="id" />

                                                                        <div class="mb-3">
                                                                              <label for="email"
                                                                                    class="form-label">Email</label>
                                                                              <form:input type="email"
                                                                                    class="form-control" id="email"
                                                                                    path="email" readonly="true" />
                                                                        </div>

                                                                        <div class="mb-3">
                                                                              <label for="password"
                                                                                    class="form-label">Mật khẩu
                                                                                    mới</label>
                                                                              <form:input type="password"
                                                                                    class="form-control" id="password"
                                                                                    path="password"
                                                                                    placeholder="Để trống nếu không muốn thay đổi" />
                                                                              <form:errors path="password"
                                                                                    cssClass="error" element="div" />
                                                                        </div>

                                                                        <div class="mb-3">
                                                                              <label for="phone" class="form-label">Số
                                                                                    điện thoại</label>
                                                                              <form:input type="text"
                                                                                    class="form-control" id="phone"
                                                                                    path="phone"
                                                                                    placeholder="Nhập số điện thoại" />
                                                                              <form:errors path="phone" cssClass="error"
                                                                                    element="div" />
                                                                        </div>

                                                                        <div class="mb-3">
                                                                              <label for="fullName"
                                                                                    class="form-label">Họ và tên</label>
                                                                              <form:input type="text"
                                                                                    class="form-control" id="fullName"
                                                                                    path="fullName"
                                                                                    placeholder="Nhập họ và tên" />
                                                                              <form:errors path="fullName"
                                                                                    cssClass="error" element="div" />
                                                                        </div>

                                                                        <div class="mb-3">
                                                                              <label for="address"
                                                                                    class="form-label">Địa chỉ</label>
                                                                              <form:input type="text"
                                                                                    class="form-control" id="address"
                                                                                    path="address"
                                                                                    placeholder="Nhập địa chỉ" />
                                                                              <form:errors path="address"
                                                                                    cssClass="error" element="div" />
                                                                        </div>

                                                                        <div class="mb-3 row">

                                                                              <div class="col-md-6">
                                                                                    <label for="avatar"
                                                                                          class="form-label">Ảnh đại
                                                                                          diện</label>
                                                                                    <input class="form-control"
                                                                                          type="file" id="avatar"
                                                                                          name="avatarFile"
                                                                                          accept=".png, .jpg, .jpeg" />
                                                                              </div>
                                                                        </div>

                                                                        <div class="mb-3 text-center">
                                                                              <img src="/uploads/avatars/${Userupdate.avatar}"
                                                                                    alt="Avatar" class="avatar-preview"
                                                                                    id="avatarPreview"
                                                                                    style="max-width: 200px; max-height: 200px;" />
                                                                        </div>

                                                                        <button type="submit"
                                                                              class="btn btn-primary w-100 mt-3">Lưu
                                                                              thay đổi</button>
                                                                        <input type="hidden"
                                                                              name="${_csrf.parameterName}"
                                                                              value="${_csrf.token}" />
                                                                  </form:form>
                                                            </div>
                                                      </div>
                                                </div>
                                          </div>
                                    </main>
                              </div>

                              <jsp:include page="../layout/footer.jsp" />

                              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                              <script
                                    src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                              <script src="/client/lib/easing/easing.min.js"></script>
                              <script src="/client/lib/waypoints/waypoints.min.js"></script>
                              <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                              <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
                              <script src="/client/js/main.js"></script>
                              <script>
                                    $(document).ready(function () {
                                          $('#avatar').on('change', function () {
                                                const file = this.files[0];
                                                const $preview = $('#avatarPreview');

                                                if (file && file.type.startsWith('image/')) {
                                                      const reader = new FileReader();
                                                      reader.onload = function (e) {
                                                            $preview.attr('src', e.target.result).css('display', 'block');
                                                      };
                                                      reader.readAsDataURL(file);
                                                } else {
                                                      $preview.attr('src', '/Uploads/avatars/${updatedUser.avatar}').css('display', 'block');
                                                }
                                          });
                                    });
                              </script>
                        </body>

                        </html>