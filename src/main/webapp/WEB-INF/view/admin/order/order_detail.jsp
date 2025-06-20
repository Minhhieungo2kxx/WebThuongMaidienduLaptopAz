<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                  <%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

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
                                                            <li class="breadcrumb-item active">Orderdetail/list</li>
                                                      </ol>
                                                      <div class="row">
                                                            <div class="container mt-5">
                                                                  <div class="row justify-content-center">
                                                                        <div class="col-12">
                                                                              <div
                                                                                    class="d-flex justify-content-between align-items-center mb-4">

                                                                                    <h3>Table OrderDetail with
                                                                                          ${orderId}</h3>

                                                                              </div>
                                                                              <hr class="mb-4" />


                                                                              <div class="table-responsive">
                                                                                    <table
                                                                                          class="table table-bordered table-hover align-middle">

                                                                                          <thead>
                                                                                                <tr
                                                                                                      class="table-primary">

                                                                                                      <th>Tên
                                                                                                      </th>
                                                                                                      <th>Giá
                                                                                                            cả
                                                                                                      </th>
                                                                                                      <th>Số lượng
                                                                                                      </th>
                                                                                                      <th>Tổng
                                                                                                            tiền
                                                                                                      </th>

                                                                                                      <th>Ảnh
                                                                                                            sản
                                                                                                            phẩm
                                                                                                      </th>

                                                                                                </tr>
                                                                                          </thead>
                                                                                          <tbody>


                                                                                                <c:if
                                                                                                      test="${empty listOrderDetail}">
                                                                                                      <div class="alert alert-info text-center mt-3"
                                                                                                            role="alert">
                                                                                                            Hiện
                                                                                                            tại
                                                                                                            hóa
                                                                                                            đơn
                                                                                                            đag
                                                                                                            rỗng,
                                                                                                            Không
                                                                                                            có
                                                                                                            cái
                                                                                                            nào
                                                                                                            cả
                                                                                                            !
                                                                                                      </div>
                                                                                                </c:if>
                                                                                                <c:if
                                                                                                      test="${not empty listOrderDetail}">
                                                                                                      <c:forEach
                                                                                                            items="${listOrderDetail}"
                                                                                                            var="orderDetail">
                                                                                                            <tr>
                                                                                                                  <td>${orderDetail.product.name}
                                                                                                                  </td>

                                                                                                                  <td>
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${orderDetail.price}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />
                                                                                                                  </td>

                                                                                                                  <td>${orderDetail.quantity}
                                                                                                                  </td>
                                                                                                                  <td>
                                                                                                                        <fmt:formatNumber
                                                                                                                              value="${orderDetail.totalPrice}"
                                                                                                                              type="number"
                                                                                                                              groupingUsed="true" />

                                                                                                                  </td>

                                                                                                                  <td><img src="/uploads/products/${orderDetail.product.image}"
                                                                                                                              alt="Product Avatar"
                                                                                                                              style="max-width:120px; max-height: 120px;" />
                                                                                                                  </td>



                                                                                                            </tr>
                                                                                                      </c:forEach>

                                                                                                </c:if>


                                                                                          </tbody>




                                                                                    </table>
                                                                              </div> <%-- End table-responsive --%>




                                                                        </div> <%-- End col --%>
                                                                  </div> <%-- End row --%>
                                                            </div> <%-- End container --%>
                                                                  <div class="text-center mt-4">
                                                                        <a href="/admin/order"
                                                                              class="btn btn-secondary">
                                                                              <i class="fas fa-arrow-left me-2"></i>
                                                                              Back to Product List
                                                                        </a>
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


                        </body>

                        </html>