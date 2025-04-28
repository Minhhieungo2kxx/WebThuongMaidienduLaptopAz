<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>User Detail</title>
                <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

            </head>

            <body>

                <div class="container mt-5">
                    <div class="row justify-content-center">


                        <div class="col-md-8 col-lg-6">
                            <form:form method="get" action="" modelAttribute="detailUser">


                                <h3 class="mb-4 text-center">User Detail

                                </h3>
                                <div class="card shadow-sm">
                                    <div class="card-header bg-primary text-white">
                                        <h5 class="mb-0">User Information</h5>
                                    </div>
                                    <div class="card-body">

                                        <ul class="list-group list-group-flush">
                                            <li class="list-group-item">
                                                <strong>ID:
                                                    <form:input type="id" path="id" readonly="true" />
                                                </strong>
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Email:</strong>
                                                <form:input type="email" class="form-control" id="email" path="email"
                                                    readonly="true" />
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Full Name:</strong>
                                                <form:input type="text" class="form-control" id="fullName"
                                                    path="fullName" readonly="true" />

                                            </li>
                                            <li class="list-group-item">
                                                <strong>Address:</strong>
                                                <form:input type="text" class="form-control" id="address" path="address"
                                                    readonly="true" />
                                            </li>
                                            <li class="list-group-item">
                                                <strong>Number Phone:</strong>
                                                <form:input type="text" class="form-control" id="address" path="phone"
                                                    readonly="true" />
                                            </li>

                                        </ul>
                                    </div>


                                </div>


                                <div class="text-center mt-4">
                                    <a href="/admin/list/user" class="btn btn-secondary">
                                        <i class="fas fa-arrow-left me-2"></i> Back to User List
                                    </a>
                                </div>
                            </form:form>

                        </div>


                    </div>
                </div>

                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

            </body>

            </html>