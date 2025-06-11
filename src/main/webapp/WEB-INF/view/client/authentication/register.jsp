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
                    <title>Register</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
                </head>

                <body class="bg-primary">
                    <div id="layoutAuthentication">
                        <div id="layoutAuthentication_content">
                            <main>
                                <div class="container">
                                    <div class="row justify-content-center">
                                        <div class="col-lg-7">
                                            <div class="card shadow-lg border-0 rounded-lg mt-5">
                                                <div class="card-header">
                                                    <h3 class="text-center font-weight-light my-4">Create Account</h3>
                                                </div>
                                                <div class="card-body">

                                                    <form:form method="post" action="/register/create"
                                                        modelAttribute="newRegister">
                                                        <div class="row mb-3">
                                                            <div class="col-md-6">
                                                                <div class="form-floating mb-3 mb-md-0">


                                                                    <form:input type="text" class="form-control"
                                                                        id="firstName" path="firstName"
                                                                        placeholder="Enter firstname" />
                                                                    <label for="inputFirstName">First name</label>
                                                                    <form:errors path="firstName" cssClass="error"
                                                                        element="div" />

                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-floating">
                                                                    <form:input type="text" class="form-control"
                                                                        id="lastName" path="lastName"
                                                                        placeholder="Enter LastName" />
                                                                    <label for="inputLastName">Last name</label>
                                                                    <form:errors path="lastName" cssClass="error"
                                                                        element="div" />
                                                                </div>
                                                            </div>
                                                        </div>
                                                        <div class="form-floating mb-3">

                                                            <form:input type="email" class="form-control" id="email"
                                                                path="email" placeholder="name@example.com" />
                                                            <label for="inputEmail">Email address</label>
                                                            <form:errors path="email" cssClass="error" element="div" />
                                                        </div>
                                                        <div class="row mb-3">
                                                            <div class="col-md-6">
                                                                <div class="form-floating mb-3 mb-md-0">

                                                                    <form:input type="password" class="form-control"
                                                                        id="password" path="password"
                                                                        placeholder="Create a password" />
                                                                    <label for="inputPassword">Password</label>
                                                                    <form:errors path="password" cssClass="error"
                                                                        element="div" />
                                                                </div>
                                                            </div>
                                                            <div class="col-md-6">
                                                                <div class="form-floating mb-3 mb-md-0">

                                                                    <form:input type="password" class="form-control"
                                                                        id="confirmpassword" path="confirmpassword"
                                                                        placeholder="Confirm password" />
                                                                    <label for="inputPasswordConfirm">Confirm
                                                                        Password</label>
                                                                    <form:errors path="confirmpassword" cssClass="error"
                                                                        element="div" />
                                                                </div>
                                                            </div>
                                                        </div>

                                                        <div class="mt-4 mb-0">
                                                            <div class="d-grid">
                                                                <button type="submit"
                                                                    class="btn btn-primary mt-3">Create Account</button>
                                                            </div>
                                                        </div>

                                                    </form:form>
                                                </div>
                                                <div class="card-footer text-center py-3">
                                                    <div class="small"><a href="/login">Have an account? Go to
                                                            login</a></div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </main>
                        </div>

                    </div>
                    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                        crossorigin="anonymous"></script>
                    <script src="/js/scripts.js"></script>
                </body>

                </html>