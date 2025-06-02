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
                                    <li class="breadcrumb-item active">User/detail</li>
                                </ol>
                                <div class="row">
                                    <div class="container mt-5">
                                        <div class="row justify-content-center">

                                            <div class="col-md-8 col-lg-6">
                                                <form:form method="get" action="" modelAttribute="detailUser">
                                                    <h3 class="mb-4 text-center">User Detail</h3>
                                                    <div class="card shadow-sm">
                                                        <div class="card-header bg-primary text-white">
                                                            <h5 class="mb-0">User Information</h5>
                                                        </div>
                                                        <div class="card-body">
                                                            <ul class="list-group list-group-flush">
                                                                <li class="list-group-item">
                                                                    <strong>ID:</strong>
                                                                    <form:input path="id" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Email:</strong>
                                                                    <form:input type="email" class="form-control"
                                                                        id="email" path="email" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Full Name:</strong>
                                                                    <form:input type="text" class="form-control"
                                                                        id="fullName" path="fullName" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Address:</strong>
                                                                    <form:input type="text" class="form-control"
                                                                        id="address" path="address" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Number Phone:</strong>
                                                                    <form:input type="text" class="form-control"
                                                                        id="phone" path="phone" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Vai trò:</strong>
                                                                    <form:input path="role.name" class="form-control"
                                                                        id="role_id" readonly="true" />
                                                                </li>
                                                                <li class="list-group-item">
                                                                    <strong>Avatar:</strong><br />
                                                                    <img src="/uploads/avatars/${detailUser.avatar}"
                                                                        alt="User Avatar"
                                                                        style="max-width:120px; max-height: 120px;"
                                                                        id="avatar" />
                                                                </li>
                                                            </ul>
                                                        </div>
                                                    </div>
                                                </form:form>


                                                <div class="text-center mt-4">
                                                    <a href="/admin/list/user" class="btn btn-secondary">
                                                        <i class="fas fa-arrow-left me-2"></i> Back to User List
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