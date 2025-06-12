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
                    <title>Login</title>
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
                                        <div class="col-lg-5">
                                            <div class="card shadow-lg border-0 rounded-lg mt-5">
                                                <div class="card-header">
                                                    <h3 class="text-center font-weight-light my-4">Login</h3>
                                                </div>
                                                <div class="card-body">

                                                    <c:if test="${param.error != null}">
                                                        <div class="alert alert-danger alert-dismissible fade show my-3"
                                                            role="alert">

                                                            <i class="fas fa-exclamation-triangle me-2"></i>
                                                            Invalid email or password.

                                                            <button type="button" class="btn-close"
                                                                data-bs-dismiss="alert" aria-label="Close"></button>
                                                        </div>
                                                    </c:if>


                                                    <c:if test="${param.logout != null}">
                                                        <div class="alert alert-info alert-dismissible fade show my-3"
                                                            role="alert">

                                                            <i class="fas fa-info-circle me-2"></i>
                                                            Logout successful!

                                                            <button type="button" class="btn-close"
                                                                data-bs-dismiss="alert" aria-label="Close"></button>
                                                        </div>
                                                    </c:if>
                                                    <!-- Hiển thị thông báo lỗi -->
                                                    <c:if test="${not empty successMessage}">
                                                        <div class="alert alert-danger" role="alert">
                                                            <i class="fas fa-exclamation-triangle me-2"></i>
                                                            ${successMessage}
                                                        </div>
                                                    </c:if>
                                                    <form method="post" action="/login">
                                                        <div class="form-floating mb-3">
                                                            <input class="form-control" id="inputEmail" type="text"
                                                                name="username""
                                                                placeholder=" name@example.com" />
                                                            <label for="inputEmail">Email address</label>
                                                        </div>
                                                        <div class="form-floating mb-3">
                                                            <input class="form-control" id="inputPassword"
                                                                name="password" type="password"
                                                                placeholder="Password" />
                                                            <label for="inputPassword">Password</label>
                                                        </div>
                                                        <div>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                value="${_csrf.token}" />
                                                        </div>

                                                        <div
                                                            class="d-flex align-items-center justify-content-between mt-4 mb-3">

                                                            <a class="small" href="/forgot-password">Forgot
                                                                Password?</a>

                                                            <button type="submit"
                                                                class="btn btn-primary mt-3">Login</button>
                                                        </div>


                                                        <div class="text-center my-3">
                                                            <p class="text-muted small mb-3">Or login with</p>
                                                            <div class="d-flex justify-content-center gap-3">
                                                                <a href="#" class="btn btn-social btn-google">
                                                                    <i class="fab fa-google fa-lg"></i>
                                                                </a>
                                                                <a href="#" class="btn btn-social btn-github">
                                                                    <i class="fab fa-github fa-lg"></i>
                                                                </a>
                                                            </div>
                                                        </div>
                                                    </form>
                                                </div>
                                                <div class="card-footer text-center py-3">
                                                    <div class="small"><a href="/register">Need an account? Sign
                                                            up!</a></div>
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