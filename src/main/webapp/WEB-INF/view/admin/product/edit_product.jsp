<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>


            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <title>Create Product</title>

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
                                    <li class="breadcrumb-item active">Product/Update</li>
                                </ol>

                                <div class="container mt-5">
                                    <div class="row justify-content-center">
                                        <div class="col-md-6 col-12">
                                            <h3 class="mb-4 text-center">Update Product</h3>
                                            <hr class="mb-4" />



                                            <!-- ✅ Form bắt đầu -->
                                            <form:form method="post" action="/admin/product/edit"
                                                modelAttribute="updateProduct" enctype="multipart/form-data">

                                                <form:input type="hidden" path="id" />

                                                <div class="mb-3">
                                                    <label for="email" class="form-label">Name:</label>
                                                    <form:input type="text" class="form-control" id="name" path="name"
                                                        placeholder="Name" />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="price" class="form-label">Price:</label>
                                                    <form:input type="number" step="any" class="form-control" id="price"
                                                        path="price" placeholder="Enter price" />
                                                </div>


                                                <div class="mb-3">
                                                    <label for="description" class="form-label">Detail
                                                        Description</label>
                                                    <form:textarea class="form-control" id="detailDesc"
                                                        path="detailDesc" rows="5"
                                                        placeholder="Enter detailed description" />
                                                </div>


                                                <div class="mb-3">
                                                    <label for="fullName" class="form-label">Short Description</label>
                                                    <form:input type="text" class="form-control" id="shortDesc"
                                                        path="shortDesc" placeholder="Enter Short Description " />
                                                </div>

                                                <div class="mb-3">
                                                    <label for="address" class="form-label">Quantity:</label>
                                                    <form:input type="number" class="form-control" id="quantity"
                                                        path="quantity" placeholder="" />
                                                </div>

                                                <div class="mb-3 row">
                                                    <div class="col">
                                                        <label for="role" class="form-label">Factory:</label>
                                                        <select class="form-select" id="factory" name="factory">
                                                            <option value="Apple">Apple (Macbook)</option>
                                                            <option value="Asus">Asus</option>
                                                            <option value="Lenovo">Lenovo</option>
                                                            <option value="Dell">Dell</option>
                                                            <option value="LG">LG</option>
                                                            <option value="Acer">Acer</option>
                                                            <option value="HP">HP</option>
                                                        </select>
                                                    </div>
                                                    <div class="col">
                                                        <label for="role" class="form-label">Target:</label>
                                                        <select class="form-select" id="target" name="target">
                                                            <option value="Gaming">Gamming</option>
                                                            <option value="Van phong">Sinh viên- Văn phòng</option>
                                                            <option value="Thiet ke do hoa">Thiết kế đồ họa</option>
                                                            <option value="Mong nhe">Mỏng nhẹ</option>
                                                            <option value="Doanh nhan">Doanh nhân</option>

                                                        </select>
                                                    </div>


                                                </div>
                                                <div class="mb-3 row">
                                                    <div class="col">
                                                        <label for="image" class="form-label">Avatar:</label>
                                                        <input class="form-control" type="file" id="image"
                                                            name="avatarFile" accept=".png, .jpg, .jpeg" />
                                                    </div>
                                                </div>

                                                <!-- Ảnh preview -->
                                                <div class="mb-3">
                                                    <div class="text-center">
                                                        <img src="/uploads/products/${updateProduct.image}"
                                                            alt="User Avatar"
                                                            style="max-width:120px; max-height: 120px;"
                                                            id="avatarPreview" />
                                                    </div>
                                                </div>


                                                <div class="text-center">
                                                    <button type="submit" class="btn btn-primary mt-3">Update
                                                        Product</button>
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
                        $('#image').on('change', function () {
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

                <script>
                    // Preview ảnh mới nếu người dùng chọn
                    document.getElementById("image").addEventListener("change", function (event) {
                        const [file] = event.target.files;
                        if (file) {
                            document.getElementById("avatarPreview").src = URL.createObjectURL(file);
                        }
                    });
                </script>




            </body>

            </html>