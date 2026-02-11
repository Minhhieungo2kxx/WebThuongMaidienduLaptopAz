<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%-- Import tag libraries --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
          <meta charset="utf-8" />
          <meta http-equiv="X-UA-Compatible" content="IE=edge" />
          <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
          <meta name="description" content="" />
          <meta name="author" content="" />
          <title>Dashboard Admin</title>

          <link href="/css/styles.css" rel="stylesheet" />
          <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
        </head>

        <body class="sb-nav-fixed">
          <jsp:include page="../layout/header.jsp" />
          <div id="layoutSidenav">
            <div id="layoutSidenav_nav">
              <jsp:include page="../layout/sidebar.jsp" />
            </div>
            <div id="layoutSidenav_content">
              <main>
                <div class="container-fluid px-4">
                  <h2 class="mt-4">Dashboard</h2>
                  <ol class="breadcrumb mb-4">
                    <li class="breadcrumb-item active">User/update</li>
                  </ol>
                  <div class="row">
                    <div class="container mt-5">
                      <div class="row justify-content-center">
                        <div class="col-md-6 col-12">
                          <h3 class="mb-4 text-center">Update User Details</h3>
                          <hr class="mb-4" />
                          <c:if test="${not empty successMessage}">
                            <div class="alert alert-success alert-dismissible fade show" role="alert">
                              <i class="fa-solid fa-circle-check me-2"></i>
                              ${successMessage}
                              <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                          </c:if>

                          <c:if test="${not empty errorMessage}">
                            <div class="alert alert-danger alert-dismissible fade show" role="alert">
                              <i class="fa-solid fa-circle-xmark me-2"></i>
                              ${errorMessage}
                              <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
                            </div>
                          </c:if>


                          <form:form method="post" action="/admin/user/update" modelAttribute="updatedUser"
                            enctype="multipart/form-data">

                            <form:input type="hidden" path="id" />

                            <div class="mb-3">
                              <label for="email" class="form-label">Email:</label>

                              <form:input type="email" class="form-control" id="email" path="email" readonly="true" />
                            </div>


                            <div class="mb-3">
                              <label for="password" class="form-label">New Password:</label>
                              <form:input type="password" class="form-control" id="password" path="password"
                                placeholder="Leave blank to keep current" />
                            </div>


                            <div class="mb-3">
                              <label for="phone" class="form-label">Phone number:</label>
                              <form:input type="text" class="form-control" id="phone" path="phone"
                                placeholder="Enter phone number" />
                            </div>

                            <div class="mb-3">
                              <label for="fullName" class="form-label">Full Name:</label>
                              <form:input type="text" class="form-control" id="fullName" path="fullName"
                                placeholder="Enter full name" />
                            </div>

                            <div class="mb-3">
                              <label for="address" class="form-label">Address:</label>
                              <form:input type="text" class="form-control" id="address" path="address"
                                placeholder="Enter address" />
                            </div>
                            <div class="mb-3 row">
                              <div class="col">
                                <label for="role_id" class="form-label">Role:</label>
                                <form:input type="text" class="form-control" id="role_id" path="role.name"
                                  placeholder="Enter address" readonly="true" />
                              </div>

                              <div class="col">
                                <label for="avatar" class="form-label">Avatar:</label>
                                <input class="form-control" type="file" id="avatar" name="avatarFile"
                                  accept=".png, .jpg, .jpeg" />
                              </div>
                            </div>
                            <!-- Ảnh preview căn giữa -->
                            <div class="mb-3">
                              <div class="text-center">
                                <img src="/uploads/avatars/${updatedUser.avatar}" alt="User Avatar"
                                  style="max-width:120px; max-height: 120px;" id="avatarPreview" />
                              </div>
                            </div>

                            <button type="submit" class="btn btn-primary w-100 mt-3">Save Changes</button>
                          </form:form>
                        </div>
                      </div>
                    </div>



                  </div>


                </div>
              </main>
              <jsp:include page="../layout/footer.jsp" />
            </div>
          </div>
          <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
            crossorigin="anonymous"></script>
          <script src="/js/scripts.js"></script>

          crossorigin="anonymous"></script>
          <script src="/js/chart-area-demo.js"></script>
          <script src="/js/chart-bar-demo.js"></script>


          <!-- Thư viện jQuery -->
          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
          <!-- Script hiển thị ảnh preview -->
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
                  $preview.attr('src', '#').css('display', 'none');
                }
              });
            });
          </script>
          <script>
            // Preview ảnh mới nếu người dùng chọn
            document.getElementById("avatar").addEventListener("change", function (event) {
              const [file] = event.target.files;
              if (file) {
                document.getElementById("avatarPreview").src = URL.createObjectURL(file);
              }
            });
          </script>
        </body>

        </html>