<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <title>Create User</title>

                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <style>

                </style>
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
                                    <li class="breadcrumb-item active">User/Create</li>
                                </ol>

                                <div class="container mt-5">
                                    <div class="row justify-content-center">
                                        <div class="col-md-6 col-12">
                                            <h3 class="mb-4 text-center">Create a New User</h3>
                                            <hr class="mb-4" />


                                            <!-- ✅ Form bắt đầu -->
                                            <form:form method="post" action="/admin/user/create"
                                                modelAttribute="newuser" enctype="multipart/form-data">

                                                <div class="mb-3">
                                                    <label for="email" class="form-label">Email:</label>
                                                    <form:input type="email" class="form-control" id="email"
                                                        path="email" placeholder="Enter email" />
                                                    <form:errors path="email" cssClass="error" element="div" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="password" class="form-label">Password:</label>
                                                    <form:input type="password" class="form-control" id="password"
                                                        path="password" placeholder="Enter password" />
                                                    <form:errors path="password" cssClass="error" element="div" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="phone" class="form-label">Phone number:</label>
                                                    <form:input type="text" class="form-control" id="phone" path="phone"
                                                        placeholder="Enter phone number" />
                                                    <form:errors path="phone" cssClass="error" element="div" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="fullName" class="form-label">Full Name:</label>
                                                    <form:input type="text" class="form-control" id="fullName"
                                                        path="fullName" placeholder="Enter full name" />
                                                    <form:errors path="fullName" cssClass="error" element="div" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="address" class="form-label">Address:</label>
                                                    <form:input type="text" class="form-control" id="address"
                                                        path="address" placeholder="Enter address" />
                                                    <form:errors path="address" cssClass="error" element="div" />
                                                </div>

                                                <div class="mb-3 row">
                                                    <div class="col">
                                                        <label for="role" class="form-label">Role:</label>
                                                        <select class="form-select" id="role_id" name="role_id">
                                                            <option value="1">Admin</option>
                                                            <option value="2">User</option>
                                                        </select>
                                                    </div>

                                                    <div class="col">
                                                        <label for="avatar" class="form-label">Avatar:</label>
                                                        <input class="form-control" type="file" id="avatar"
                                                            name="avatarFile" accept=".png, .jpg, .jpeg" />
                                                    </div>
                                                </div>

                                                <!-- Ảnh preview căn giữa -->
                                                <div class="mb-3">
                                                    <div class="text-center">
                                                        <img id="avatarPreview" class="img-fluid d-block mx-auto"
                                                            style="max-width: 180px; display: none;"
                                                            alt="Avatar preview" />
                                                    </div>
                                                </div>

                                                <div class="text-center">
                                                    <button type="submit" class="btn btn-primary mt-3">Create
                                                        User</button>
                                                </div>

                                            </form:form>
                                            <!-- ✅ Form kết thúc -->
                                        </div>
                                    </div>
                                </div>

                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>

                <!-- Scripts -->
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>



                <!-- Thư viện jQuery -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <!-- Script hiển thị ảnh preview -->
                <script>
                    $(document).ready(function () {
                        $('#avatar').on('change', function () {
                            const file = this.files[0];
                            const $preview = $('#avatarPreview');

                            if (file && file.type.startsWith('image/')) {
                                const reader = new FileReader();
                                reader.onload = function (e) {
                                    $preview.attr('src', e.target.result).css('display', 'block');
                                };
                                reader.readAsDataURL(file);
                            } else {
                                $preview.attr('src', '#').css('display', 'none');
                            }
                        });
                    });
                </script>




            </body>

            </html>