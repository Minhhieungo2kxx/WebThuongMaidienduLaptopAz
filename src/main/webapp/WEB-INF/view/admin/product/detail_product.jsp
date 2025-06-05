<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

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
                                <h1 class="mt-4">Dashboard</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active">Product/detail</li>
                                </ol>
                                <div class="row">
                                    <div class="container mt-5">
                                        <div class="row justify-content-center">

                                            <div class="col-md-8 col-lg-6">
                                                <form:form method="get" action="" modelAttribute="detailProduct">
                                                    <h3 class="mb-4 text-center">Product Detail</h3>
                                                    <div class="card shadow-sm">
                                                        <div class="card-header bg-primary text-white">
                                                            <h5 class="mb-0">Product Information</h5>
                                                        </div>
                                                        <div class="card-body">
                                                            <ul class="list-group list-group-flush">
                                                                <li class="list-group-item">
                                                                    <strong>ID:</strong>
                                                                    <form:input path="id" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Name:</strong>
                                                                    <form:input type="text" class="form-control"
                                                                        id="name" path="name" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Price:</strong>
                                                                    <form:input type="number" class="form-control"
                                                                        id="price" path="price" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Detail Description</strong>
                                                                    <form:textarea class="form-control" id="detailDesc"
                                                                        path="detailDesc" rows="5"
                                                                        placeholder="Enter detailed description"
                                                                        readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <label for="address"
                                                                        class="form-label">Quantity:</label>
                                                                    <form:input type="number" class="form-control"
                                                                        id="quantity" path="quantity" placeholder="" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Factory:</strong>
                                                                    <form:input type="text" path="factory"
                                                                        class="form-control" id="factory"
                                                                        readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Target:</strong>
                                                                    <form:input type="text" path="target"
                                                                        class="form-control" id="target"
                                                                        readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Avatar:</strong><br />
                                                                    <img src="/uploads/products/${detailProduct.image}"
                                                                        alt="User Avatar"
                                                                        style="max-width:120px; max-height: 120px;"
                                                                        id="avatar" />
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </form:form>


                                                <div class="text-center mt-4">
                                                    <a href="/admin/product" class="btn btn-secondary">
                                                        <i class="fas fa-arrow-left me-2"></i> Back to Product List
                                                    </a>
                                                </div>


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


            </body>

            </html>