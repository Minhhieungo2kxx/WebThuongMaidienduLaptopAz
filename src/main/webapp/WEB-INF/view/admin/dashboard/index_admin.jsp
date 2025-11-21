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
                                        <!-- Biểu đồ 1 -->
                                        <div class="col-xl-6 col-lg-6">
                                            <div class="card mb-4">
                                                <div class="card-header">
                                                    <i class="fas fa-chart-bar me-1"></i>
                                                    Biểu đồ thống kê hệ thống
                                                </div>
                                                <div class="card-body">
                                                    <canvas id="myBarChart" height="300"></canvas>
                                                </div>
                                                <div class="card-footer small text-muted">
                                                    Cập nhật ngày:
                                                    <fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />
                                                </div>
                                            </div>
                                        </div>

                                        <!-- Biểu đồ 2 -->
                                        <div class="col-xl-6 col-lg-6">
                                            <div class="card mb-4">
                                                <div class="card-header">
                                                    <i class="fas fa-chart-bar me-1"></i>
                                                    1️⃣ Biểu đồ doanh thu theo tháng
                                                </div>
                                                <div class="card-body">
                                                    <canvas id="monthlyRevenueChart" height="300"></canvas>
                                                </div>
                                                <div class="card-footer small text-muted">
                                                    Cập nhật ngày:
                                                    <fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!-- Hàng tiếp theo nếu muốn 2 biểu đồ khác -->
                                    <div class="row justify-content-center">
                                        <div class="col-xl-6 col-lg-6">
                                            <div class="card mb-4">
                                                <div class="card-header">
                                                    <i class="fas fa-chart-bar me-1"></i>
                                                    2️⃣ Tỷ lệ đơn hàng theo trạng thái
                                                </div>
                                                <div class="card-body">
                                                    <canvas id="orderStatusChart" height="300"></canvas>
                                                </div>
                                                <div class="card-footer small text-muted">
                                                    Cập nhật ngày:
                                                    <fmt:formatDate value="${currentDate}" pattern="dd/MM/yyyy" />
                                                </div>
                                            </div>
                                        </div>

                                        <div class="col-xl-6 col-lg-6">
                                            <div class="card mb-4">
                                                <div class="card-header">
                                                    <i class="fas fa-chart-bar me-1"></i>
                                                    3️⃣ Top 5 sản phẩm bán chạy (Bar chart)
                                                </div>
                                                <div class="card-body">
                                                    <canvas id="topProductsChart" height="300"></canvas>
                                                </div>
                                                <div class="card-footer small text-muted">
                                                    Cập nhật ngày:
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
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>


                    <!-- SCRIPT VẼ CHART -->
                    <!-- 1. Thêm Chart.js -->
                    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>

                    <!-- 2. Canvas -->
                    <canvas id="myBarChart" style="height:400px;"></canvas>

                    <script>
                        // Dữ liệu từ backend
                        var dataUsers = '${ Countuser }';
                        var dataProduct = ' ${ Countproduct }';
                        var dataOrder = '${ CountOrder }';
                        var dataMoney = '${ sumorder_money }';

                        console.log(dataUsers, dataProduct, dataOrder, dataMoney);

                        var ctx = document.getElementById("myBarChart").getContext('2d');

                        new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: ["Users", "Products", "Orders", "Doanh thu (VNĐ)"],
                                datasets: [
                                    {
                                        label: "Users / Products / Orders",
                                        data: [dataUsers, dataProduct, dataOrder, 0], // 0 cho Doanh thu
                                        backgroundColor: ["#007bff", "#ffc107", "#28a745", "#28a745"],
                                        yAxisID: 'ySmall',
                                        borderColor: "#333",
                                        borderWidth: 1
                                    },
                                    {
                                        label: "Doanh thu (VNĐ)",
                                        data: [0, 0, 0, dataMoney], // chỉ Doanh thu
                                        backgroundColor: "#ff5733",
                                        yAxisID: 'yLarge',
                                        borderColor: "#333",
                                        borderWidth: 1
                                    }
                                ]
                            },
                            options: {
                                responsive: true,
                                maintainAspectRatio: false,
                                scales: {
                                    ySmall: {
                                        beginAtZero: true,
                                        position: 'left',
                                        ticks: {
                                            stepSize: 10 // hoặc bỏ nếu muốn Chart.js tự chia
                                        },
                                        title: {
                                            display: true,
                                            text: 'Số lượng'
                                        }
                                    },
                                    yLarge: {
                                        beginAtZero: true,
                                        position: 'right',
                                        ticks: {
                                            callback: function (val) {
                                                return val.toLocaleString() + ' VNĐ';
                                            }
                                        },
                                        grid: {
                                            drawOnChartArea: false // tránh grid chồng lên cột nhỏ
                                        },
                                        title: {
                                            display: true,
                                            text: 'Doanh thu (VNĐ)'
                                        }
                                    }
                                },
                                plugins: {
                                    tooltip: {
                                        callbacks: {
                                            label: function (context) {
                                                let value = context.raw;
                                                if (context.dataset.yAxisID === 'yLarge') {
                                                    return value.toLocaleString() + ' VNĐ';
                                                }
                                                return value;
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    </script>


                    <canvas id="monthlyRevenueChart" width="400" height="200"></canvas>

                    <script>
                        var monthlyRevenue = JSON.parse('${monthlyRevenueJson}');
                        console.log("Monthly Revenue:", monthlyRevenue);

                        var ctx = document.getElementById('monthlyRevenueChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'bar', // 'line' nếu muốn line chart
                            data: {
                                labels: ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"],
                                datasets: [{
                                    label: 'Doanh thu theo tháng (VNĐ)',
                                    data: monthlyRevenue,
                                    backgroundColor: '#007bff',
                                    borderColor: '#0056b3',
                                    borderWidth: 1,
                                    fill: false
                                }]
                            },
                            options: {
                                responsive: true,
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        ticks: {
                                            callback: function (val) {
                                                return val.toLocaleString() + ' VNĐ';
                                            }
                                        },
                                        title: {
                                            display: true,
                                            text: 'Doanh thu (VNĐ)'
                                        }
                                    }
                                },
                                plugins: {
                                    tooltip: {
                                        callbacks: {
                                            label: function (context) {
                                                return context.raw.toLocaleString() + ' VNĐ';
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    </script>


                    <canvas id="orderStatusChart" width="400" height="200"></canvas>
                    <script>
                        var orderStatusData = {
                            pending: ${ countPending },
                            processing: ${ countProcessing },
                            completed: ${ countCompleted },
                            cancelled: ${ countCancelled }
};

                        var ctx = document.getElementById('orderStatusChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'doughnut',
                            data: {
                                labels: ["Pending", "Processing", "Completed", "Cancelled"],
                                datasets: [{
                                    data: [orderStatusData.pending, orderStatusData.processing, orderStatusData.completed, orderStatusData.cancelled],
                                    backgroundColor: ["#ffc107", "#17a2b8", "#28a745", "#dc3545"],
                                    borderColor: "#fff",
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                plugins: {
                                    tooltip: {
                                        callbacks: {
                                            label: function (context) {
                                                let total = context.dataset.data.reduce((a, b) => a + b, 0);
                                                let percent = ((context.raw / total) * 100).toFixed(2) + '%';
                                                return context.label + ': ' + context.raw + ' (' + percent + ')';
                                            }
                                        }
                                    }
                                }
                            }
                        });
                    </script>



                    <canvas id="topProductsChart" width="400" height="200"></canvas>

                    <script>
                        // Chuyển JSON string sang mảng JS
                        var topProducts = JSON.parse('${productNamesJson}');
                        var topProductsCount = JSON.parse('${productQuantitiesJson}');

                        console.log("Top Products:", topProducts);
                        console.log("Quantities:", topProductsCount);

                        var ctx = document.getElementById('topProductsChart').getContext('2d');
                        new Chart(ctx, {
                            type: 'bar',
                            data: {
                                labels: topProducts,
                                datasets: [{
                                    label: 'Số lượng bán ra',
                                    data: topProductsCount,
                                    backgroundColor: '#28a745',
                                    borderColor: '#1c7430',
                                    borderWidth: 1
                                }]
                            },
                            options: {
                                responsive: true,
                                scales: {
                                    y: {
                                        beginAtZero: true,
                                        title: {
                                            display: true,
                                            text: 'Số lượng'
                                        }
                                    }
                                }
                            }
                        });
                    </script>













                </body>

                </html>