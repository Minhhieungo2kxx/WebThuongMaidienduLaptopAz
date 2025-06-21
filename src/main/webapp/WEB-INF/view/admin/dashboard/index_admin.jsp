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
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
                </head>

                <body class="sb-nav-fixed">
                    <jsp:include page="../layout/header.jsp" />
                    <div id="layoutSidenav">
                        <div id="layoutSidenav_nav">
                            <jsp:include page="../layout/sidebar.jsp" />
                        </div>
                        <div id="layoutSidenav_content">
                            <main class="py-4">
                                <div class="container-fluid px-4">
                                    <h1 class="mt-4">Dashboard</h1>
                                    <ol class="breadcrumb mb-4">
                                        <h4 style="color: brown;">Thống kê</h4>
                                    </ol>

                                    <div class="row">
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card bg-primary text-white h-100">
                                                <div class="card-body">
                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div>
                                                            <div class="text-white-75 small">
                                                                <h4>Tổng doanh thu hệ thống FPT
                                                                    SHOP</h4>
                                                            </div>
                                                            <div class="text-lg fw-bold">

                                                                <h5 style="color:white;">
                                                                    <fmt:formatNumber value="${sumorder_money}"
                                                                        type="number" groupingUsed="true" />
                                                                </h5>

                                                            </div>
                                                        </div>
                                                        <p>VND</p>
                                                    </div>
                                                </div>
                                                <div
                                                    class="card-footer d-flex align-items-center justify-content-between">
                                                    <a class="small text-white stretched-link" href="/admin/order">Xem
                                                        chi tiết</a>
                                                    <div class="small text-white"><i class="fas fa-angle-right"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card bg-warning text-white h-100">
                                                <div class="card-body">
                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div>
                                                            <div class="text-white-75 small">
                                                                <h4>Orders</h4>
                                                            </div>
                                                            <div class="text-lg fw-bold">
                                                                <h5>${CountOrder}</h5>
                                                            </div>
                                                        </div>
                                                        <i class="fas fa-receipt fa-2x"></i>
                                                    </div>
                                                </div>
                                                <div
                                                    class="card-footer d-flex align-items-center justify-content-between">
                                                    <a class="small text-white stretched-link" href="/admin/order">Xem
                                                        chi tiết</a>
                                                    <div class="small text-white"><i class="fas fa-angle-right"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card bg-success text-white h-100">
                                                <div class="card-body">
                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div>
                                                            <div class="text-white-75 small">
                                                                <h4>Users</h4>
                                                            </div>
                                                            <div class="text-lg fw-bold">
                                                                <h5>${Countuser}</h5>
                                                            </div>
                                                        </div>
                                                        <i class="fas fa-users fa-2x"></i>
                                                    </div>
                                                </div>
                                                <div
                                                    class="card-footer d-flex align-items-center justify-content-between">
                                                    <a class="small text-white stretched-link"
                                                        href="/admin/list/user">Xem chi tiết</a>
                                                    <div class="small text-white"><i class="fas fa-angle-right"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="col-xl-3 col-md-6 mb-4">
                                            <div class="card bg-danger text-white h-100">
                                                <div class="card-body">
                                                    <div class="d-flex align-items-center justify-content-between">
                                                        <div>
                                                            <div class="text-white-75 small">
                                                                <h4>Products</h4>
                                                            </div>
                                                            <div class="text-lg fw-bold">
                                                                <h5>${Countproduct}</h5>
                                                            </div>
                                                        </div>
                                                        <i class="fas fa-box-open fa-2x"></i>
                                                    </div>
                                                </div>
                                                <div
                                                    class="card-footer d-flex align-items-center justify-content-between">
                                                    <a class="small text-white stretched-link" href="/admin/product">Xem
                                                        chi tiết</a>
                                                    <div class="small text-white"><i class="fas fa-angle-right"></i>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <div class="row justify-content-center">
                                        <div class="col-xl-6 col-lg-8">
                                            <div class="card mb-4">
                                                <div class="card-header">
                                                    <i class="fas fa-chart-pie me-1"></i>
                                                    Biểu đồ Thống kê các thành phần Hệ thống
                                                </div>
                                                <div class="card-body">
                                                    <canvas id="myPieChart" style="height: 300px;"></canvas>
                                                </div>
                                                <div class="card-footer small text-muted">
                                                    Tổng doanh thu (gồm phí ship):
                                                    <strong style="color: green;">
                                                        <fmt:formatNumber value="${sumorder_money}" type="currency"
                                                            currencySymbol="₫" groupingUsed="true" />
                                                    </strong>
                                                </div>
                                                <div class="card-footer small text-muted">Cập nhật ngày:
                                                    <fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />

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

                    <!-- SCRIPT VẼ CHART -->
                    <script>
                        var ctx = document.getElementById("myPieChart");
                        if (ctx) {
                            var myPieChart = new Chart(ctx, {
                                type: 'pie',
                                data: {
                                    labels: ["Người dùng", "Sản phẩm", "Đơn hàng"], // Tên hiển thị
                                    datasets: [{
                                        data: [
                                            ${ Countuser },     // dữ liệu từ controller
                                            ${ Countproduct },
                                            ${ CountOrder }
                                        ],
                                        backgroundColor: ['#007bff', '#ffc107', '#28a745'], // màu sắc
                                    }],
                                },
                                options: {
                                    responsive: true,
                                    maintainAspectRatio: false,
                                    plugins: {
                                        legend: {
                                            position: 'bottom'
                                        }
                                    }
                                }
                            });
                        }
                    </script>




                </body>

                </html>