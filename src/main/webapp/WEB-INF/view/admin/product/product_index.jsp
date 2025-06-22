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
                                        <li class="breadcrumb-item active">Product/list</li>
                                    </ol>
                                    <div class="row">
                                        <div class="container mt-5">
                                            <div class="row justify-content-center">
                                                <div class="col-12">
                                                    <div class="d-flex justify-content-between align-items-center mb-4">

                                                        <h3>Table Product</h3> <%-- Tiêu đề rõ ràng hơn --%>
                                                            <a href="/admin/product/create" class="btn btn-primary">
                                                                <i class="fas fa-plus"></i> Create New Product
                                                            </a>
                                                    </div>
                                                    <hr class="mb-4" />



                                                    <div class="table-responsive"> <%-- Giúp bảng cuộn ngang trên màn
                                                            hình nhỏ --%>
                                                            <table
                                                                class="table table-bordered table-hover align-middle">
                                                                <%-- Thêm align-middle để căn giữa nội dung cell theo
                                                                    chiều dọc --%>
                                                                    <thead>
                                                                        <tr class="table-primary"> <%-- Thêm màu nền cho
                                                                                header (tùy chọn) --%>
                                                                                <th>ID</th>
                                                                                <th>Name</th>
                                                                                <th>Price</th>
                                                                                <th>Quantity</th>
                                                                                <th>Short Description</th>
                                                                                <th>Target</th>
                                                                                <th>Image product</th>
                                                                                <th>Actions</th> <%-- Đổi tên cột --%>
                                                                        </tr>
                                                                    </thead>
                                                                    <tbody>

                                                                        <c:if test="${not empty successMessage}">
                                                                            <div class="alert alert-success alert-dismissible fade show"
                                                                                role="alert">
                                                                                ${successMessage}
                                                                                <button type="button" class="btn-close"
                                                                                    data-bs-dismiss="alert"
                                                                                    aria-label="Close"></button>
                                                                            </div>
                                                                        </c:if>
                                                                        <c:if test="${not empty successMessage}">
                                                                            <script>
                                                                                alert('${successMessage}');
                                                                            </script>
                                                                        </c:if>

                                                                        <%-- Iterate through the list of users --%>
                                                                            <c:forEach items="${Listproduct}"
                                                                                var="product">
                                                                                <tr>
                                                                                    <td>${product.id}</td>
                                                                                    <td>${product.name}</td>
                                                                                    <td>
                                                                                        <fmt:formatNumber
                                                                                            value="${product.price}"
                                                                                            type="number"
                                                                                            groupingUsed="true" />
                                                                                    </td>

                                                                                    <td>${product.quantity}</td>
                                                                                    <td>${product.shortDesc}</td>
                                                                                    <td>${product.target}</td>
                                                                                    <td><img src="/uploads/products/${product.image}"
                                                                                            alt="Product Avatar"
                                                                                            style="max-width:120px; max-height: 120px;" />
                                                                                    </td>

                                                                                    <td>

                                                                                        <a href="/admin/product/detail/${product.id}"
                                                                                            class="btn btn-info btn-sm me-1"
                                                                                            title="View Details"> View
                                                                                        </a>
                                                                                        <a href="/admin/product/edit/${product.id}"
                                                                                            class="btn btn-warning btn-sm me-1"
                                                                                            title="Edit ">
                                                                                            Edit </a>

                                                                                        <a href="/admin/product/delete/${product.id}"
                                                                                            class="btn btn-danger btn-sm"
                                                                                            onclick="return confirm('Are you sure you want to delete Product ${product.name}?');"
                                                                                            title="Delete ">
                                                                                            Delete
                                                                                        </a>
                                                                                    </td>
                                                                                </tr>
                                                                            </c:forEach>
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
                                            <a class="page-link" href="?page=${currentPage - 1}" aria-label="Previous">
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