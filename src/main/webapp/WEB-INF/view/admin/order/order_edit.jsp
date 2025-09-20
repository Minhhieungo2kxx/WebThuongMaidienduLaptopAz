<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>


                        <head>
                              <meta charset="utf-8" />
                              <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                              <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                              <title>Edit Order</title>

                              <link href="/css/styles.css" rel="stylesheet" />
                              <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                                    crossorigin="anonymous"></script>
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
                                                            <li class="breadcrumb-item active">Order/Update</li>
                                                      </ol>

                                                      <div class="container mt-5">
                                                            <div class="row justify-content-center">
                                                                  <div class="col-md-6 col-12">
                                                                        <h3 class="mb-4 text-center">Update Order with
                                                                              ${order.id}</h3>
                                                                        <hr class="mb-4" />



                                                                        <!-- ✅ Form bắt đầu -->
                                                                        <form:form method="post"
                                                                              action="/admin/order-edit"
                                                                              modelAttribute="order">

                                                                              <form:input type="hidden" path="id" />

                                                                              <div class="mb-3">
                                                                                    <label for="email"
                                                                                          class="form-label">Full
                                                                                          Name</label>
                                                                                    <!-- <form:input type="text"
                                                                                          class="form-control" id="name"
                                                                                          path="receiverName"
                                                                                          placeholder="Name"
                                                                                          readonly="true" /> -->
                                                                                    <input type="text"
                                                                                          class="form-control" id="name"
                                                                                          name="receiverName"
                                                                                          value="${order.receiverName}"
                                                                                          readonly="true" />
                                                                              </div>

                                                                              <div class="mb-3">
                                                                                    <label for="price"
                                                                                          class="form-label">Tổng
                                                                                          tiền(phí ship): </label>

                                                                                    <span>
                                                                                          <fmt:formatNumber
                                                                                                value="${order.totalPriceaddShip}"
                                                                                                type="number"
                                                                                                groupingUsed="true" />
                                                                                    </span>
                                                                              </div>
                                                                              <div class="mb-3">
                                                                                    <label for="fullName"
                                                                                          class="form-label">Phone:</label>
                                                                                    <form:input type="text"
                                                                                          class="form-control"
                                                                                          id="shortDesc"
                                                                                          path="receiverPhone"
                                                                                          placeholder="Enter Short Description "
                                                                                          readonly="true" />
                                                                              </div>


                                                                              <div class="mb-3">
                                                                                    <label for="status"
                                                                                          class="form-label">Trạng thái
                                                                                          đơn hàng:</label>
                                                                                    <!-- truyen tu controller data sang view -->
                                                                                    <form:select path="status"
                                                                                          class="form-select"
                                                                                          id="status">
                                                                                          <form:options
                                                                                                items="${statusList}" />
                                                                                    </form:select>

                                                                              </div>
                                                                              <div class="mb-3">
                                                                                    <label for="status"
                                                                                          class="form-label">Trạng thái
                                                                                          Thanh toán:</label>
                                                                                    <!-- truyen tu controller data sang view -->
                                                                                    <form:select path="paymentStatus"
                                                                                          class="form-select"
                                                                                          id="status">
                                                                                          <form:options
                                                                                                items="${statuspaymentList}" />
                                                                                    </form:select>

                                                                              </div>

                                                                              <div class="text-center">
                                                                                    <button type="submit"
                                                                                          class="btn btn-primary mt-3">Update
                                                                                          Order</button>
                                                                              </div>
                                                                              <input type="hidden"
                                                                                    name="${_csrf.parameterName}"
                                                                                    value="${_csrf.token}" />

                                                                        </form:form>
                                                                        <!-- ✅ Form kết thúc -->
                                                                  </div>
                                                            </div>
                                                      </div>

                                                </div>
                                          </main>
                                          <jsp:include page="../layout/footer.jsp" />
                                    </div>
                              </div>

                              <!-- Scripts -->
                              <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                                    crossorigin="anonymous"></script>
                              <script src="/js/scripts.js"></script>



                              <!-- Thư viện jQuery -->
                              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                              <!-- Script hiển thị ảnh preview -->
                              <script>
                                    $(document).ready(function () {
                                          $('#image').on('change', function () {
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
                                    document.getElementById("image").addEventListener("change", function (event) {
                                          const [file] = event.target.files;
                                          if (file) {
                                                document.getElementById("avatarPreview").src = URL.createObjectURL(file);
                                          }
                                    });
                              </script>




                        </body>

                        </html>