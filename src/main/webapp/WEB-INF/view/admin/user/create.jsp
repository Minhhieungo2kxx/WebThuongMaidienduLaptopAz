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
                                <h2 class="mt-4">Dashboard</h2>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item active">User/create</li>
                                </ol>
                                <div class="row">
                                    <div class="container mt-5">
                                        <div class="row justify-content-center">
                                            <div class="col-md-6 col-12 ">
                                                <h3 class="mb-4 text-center">Create a New User</h3>
                                                <hr class="mb-4" />
                                                <form:form method="post" action="/admin/user/create"
                                                    modelAttribute="newuser">
                                                    <div class="mb-3">
                                                        <label for="email" class="form-label">Email:</label>
                                                        <form:input type="email" class="form-control" id="email"
                                                            path="email" placeholder="Enter email" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label for="password" class="form-label">Password:</label>
                                                        <form:input type="password" class="form-control" id="password"
                                                            path="password" placeholder="Enter password" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label for="phone" class="form-label">Phone number:</label>
                                                        <form:input type="text" class="form-control" id="phone"
                                                            path="phone" placeholder="Enter phone number" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label for="fullName" class="form-label">Full Name:</label>
                                                        <form:input type="text" class="form-control" id="fullName"
                                                            path="fullName" placeholder="Enter full name" />
                                                    </div>

                                                    <div class="mb-3">
                                                        <label for="address" class="form-label">Address:</label>
                                                        <form:input type="text" class="form-control" id="address"
                                                            path="address" placeholder="Enter address" />
                                                    </div>

                                                    <button type="submit" class="btn btn-primary w-100 mt-3">Create
                                                        User</button>
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
                <script src="/assets/demo/chart-area-demo.js"></script>
                <script src="/assets/demo/chart-bar-demo.js"></script>


            </body>

            </html>