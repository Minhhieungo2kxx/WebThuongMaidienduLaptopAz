<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>


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
                                        <h1 class="mt-4">Dashboard</h1>
                                        <ol class="breadcrumb mb-4">
                                            <li class="breadcrumb-item active">Order</li>
                                        </ol>
                                        <div class="row">
                                            <div class="container mt-5">
                                                <div class="row justify-content-center">
                                                    <div class="col-12">
                                                        <div
                                                            class="d-flex justify-content-between align-items-center mb-4">

                                                            <h3>Table Order</h3> <%-- Tiêu đề rõ ràng hơn --%>

                                                        </div>
                                                        <hr class="mb-4" />



                                                        <div class="table-responsive"> <%-- Giúp bảng cuộn ngang trên
                                                                màn hình nhỏ --%>
                                                                <table
                                                                    class="table table-bordered table-hover align-middle">
                                                                    <%-- Thêm align-middle để căn giữa nội dung cell
                                                                        theo chiều dọc --%>
                                                                        <thead>
                                                                            <tr class="table-primary"> <%-- Thêm màu nền
                                                                                    cho header (tùy chọn) --%>
                                                                                    <th>ID</th>
                                                                                    <th>Tổng tiền(phí ship)</th>
                                                                                    <th>Tên khách hàng</th>
                                                                                    <th>Địa chỉ</th>
                                                                                    <th>Số điện thoại</th>
                                                                                    <th>Ngày thanh toán</th>
                                                                                    <th>Trạng thái đơn hàng:</th>
                                                                                    <th>Trạng thái thanh toán:</th>
                                                                                    <th>Loại thanh toán:</th>
                                                                                    <th>Actions</th> <%-- Đổi tên cột
                                                                                        --%>
                                                                            </tr>
                                                                        </thead>
                                                                        <tbody>
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



                                                                            <c:if test="${empty Listorder}">
                                                                                <div class="alert alert-info text-center mt-3"
                                                                                    role="alert">
                                                                                    Hiện tại hóa đơn đag rỗng, Không có
                                                                                    cái
                                                                                    nào
                                                                                    cả !
                                                                                </div>
                                                                            </c:if>
                                                                            <c:if test="${not empty Listorder}">
                                                                                <c:forEach items="${Listorder}"
                                                                                    var="order">
                                                                                    <tr>
                                                                                        <td>${order.id}</td>

                                                                                        <td>
                                                                                            <fmt:formatNumber
                                                                                                value="${order.totalPriceaddShip}"
                                                                                                type="number"
                                                                                                groupingUsed="true" />
                                                                                        </td>

                                                                                        <td>${order.receiverName}</td>
                                                                                        <td>${order.receiverAddress}
                                                                                        </td>
                                                                                        <td>${order.receiverPhone}</td>
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
                                                                                        <td>${order.status}</td>
                                                                                        <td>${order.paymentStatus}</td>
                                                                                        <td>${order.paymentMethod}</td>

                                                                                        <td>

                                                                                            <a href="/admin/order/detail/${order.id}"
                                                                                                class="btn btn-info btn-sm me-1"
                                                                                                title="View Details">
                                                                                                View
                                                                                            </a>
                                                                                            <a href="/admin/order/edit/${order.id}"
                                                                                                class="btn btn-warning btn-sm me-1"
                                                                                                title="Edit ">
                                                                                                Edit </a>

                                                                                            <a href="/admin/order/delete/${order.id}"
                                                                                                class="btn btn-danger btn-sm"
                                                                                                onclick="return confirm('Are you sure you want to delete Order with name Customer ${order.receiverName}?');"
                                                                                                title="Delete ">
                                                                                                Delete
                                                                                            </a>
                                                                                        </td>
                                                                                    </tr>
                                                                                </c:forEach>

                                                                            </c:if>


                                                                        </tbody>



                                                                        </tbody>
                                                                </table>
                                                        </div> <%-- End table-responsive --%>




                                                    </div> <%-- End col --%>
                                                </div> <%-- End row --%>
                                            </div> <%-- End container --%>



                                        </div>


                                    </div>
                                </main>

                                <nav aria-label="Page navigation">
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
                                                <a class="page-link" href="?page=${currentPage + 1}" aria-label="Next">
                                                    <span aria-hidden="true">»</span>
                                                </a>
                                            </li>
                                        </c:if>

                                    </ul>
                                </nav>
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