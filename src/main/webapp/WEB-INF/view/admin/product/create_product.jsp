<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <!DOCTYPE html>
            <html>

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
                                    <li class="breadcrumb-item active">Product/Create</li>
                                </ol>
                                <div class="card">
                                    <h3 class="mb-4 text-center">Create a New Product</h3>
                                    <hr class="mb-4" />
                                    <form:form id="productForm" method="post" action="/admin/product/create"
                                        modelAttribute="NewProduct" enctype="multipart/form-data">
                                        <div class="mb-3">
                                            <label for="name" class="form-label">Name:</label>
                                            <form:input type="text" class="form-control" id="name" path="name"
                                                placeholder="Enter product name" />
                                            <form:errors path="name" cssClass="error" element="div" />
                                        </div>
                                        <div class="mb-3">
                                            <label for="price" class="form-label">Price:</label>
                                            <form:input type="number" step="any" class="form-control" id="price"
                                                path="price" placeholder="Enter price" />
                                            <form:errors path="price" cssClass="error" element="div" />
                                        </div>
                                        <div class="mb-3">
                                            <label for="detailDesc" class="form-label">Detail Description:</label>
                                            <form:textarea class="form-control" id="detailDesc" path="detailDesc"
                                                rows="5" placeholder="Enter detailed description" />
                                            <form:errors path="detailDesc" cssClass="error" element="div" />
                                        </div>
                                        <div class="mb-3">
                                            <label for="shortDesc" class="form-label">Short Description:</label>
                                            <form:input type="text" class="form-control" id="shortDesc" path="shortDesc"
                                                placeholder="Enter short description" />
                                            <form:errors path="shortDesc" cssClass="error" element="div" />
                                        </div>
                                        <div class="mb-3">
                                            <label for="quantity" class="form-label">Quantity:</label>
                                            <form:input type="number" class="form-control" id="quantity" path="quantity"
                                                placeholder="Enter quantity" />
                                            <form:errors path="quantity" cssClass="error" element="div" />
                                        </div>
                                        <div class="mb-3 row">
                                            <div class="col">
                                                <label for="factory" class="form-label">Factory:</label>
                                                <select class="form-select" id="factory" name="factory">
                                                    <option value="Apple">Apple (Macbook)</option>
                                                    <option value="Asus">Asus</option>
                                                    <option value="Lenovo">Lenovo</option>
                                                    <option value="Dell">Dell</option>
                                                    <option value="LG">LG</option>
                                                    <option value="Acer">Acer</option>
                                                    <option value="HP">HP</option>
                                                </select>
                                                <form:errors path="factory" cssClass="error" element="div" />
                                            </div>
                                            <div class="col">
                                                <label for="target" class="form-label">Target:</label>
                                                <select class="form-select" id="target" name="target">
                                                    <option value="Gaming">Gaming</option>
                                                    <option value="Van phong">Sinh viên - Văn phòng</option>
                                                    <option value="Thiet ke do hoa">Thiết kế đồ họa</option>
                                                    <option value="Mong nhe">Mỏng nhẹ</option>
                                                    <option value="Doanh nhan">Doanh nhân</option>
                                                </select>
                                                <form:errors path="target" cssClass="error" element="div" />
                                            </div>
                                        </div>
                                        <div class="mb-3">
                                            <label for="image" class="form-label">Avatar:</label>
                                            <input class="form-control" type="file" id="image" name="avatarFile"
                                                accept=".png, .jpg, .jpeg" />
                                        </div>
                                        <form:hidden path="image" id="imageUrl" />
                                        <form:hidden path="publicId" id="imagePublicId" />
                                        <form:hidden path="resourceType" id="imageResourceType" />
                                        <div class="mb-3 text-center">
                                            <img id="avatarPreview" class="img-fluid"
                                                style="max-width: 180px; display: none;" alt="Avatar preview" />
                                        </div>
                                        <div class="text-center">
                                            <button type="submit" class="btn btn-primary mt-3">Create Product</button>
                                        </div>
                                    </form:form>
                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

                <script>
                    $(document).ready(function () {

                        $('#image').on('change', async function () {

                            const file = this.files[0];
                            const $preview = $('#avatarPreview');

                            if (!file) {
                                return;
                            }

                            if (!file.type.startsWith('image/')) {
                                alert('Vui lòng chọn file ảnh');
                                $(this).val('');
                                return;
                            }

                            try {

                                // Preview ảnh ngay
                                const reader = new FileReader();

                                reader.onload = function (e) {
                                    $preview.attr('src', e.target.result)
                                        .css('display', 'block');
                                };

                                reader.readAsDataURL(file);

                                // Upload Cloudinary
                                const formData = new FormData();
                                formData.append("file", file);
                                formData.append("folder", "products");

                                const response = await fetch('/api/v1/file/cloudinary', {
                                    method: 'POST',
                                    body: formData
                                });

                                if (!response.ok) {
                                    throw new Error("Upload thất bại");
                                }

                                const result = await response.json();

                                const data = result.data;

                                // Lưu metadata vào hidden fields
                                $('#imageUrl').val(data.fileName);
                                $('#imagePublicId').val(data.public_id);
                                $('#imageResourceType').val(data.resourceType);

                                console.log('Upload Cloudinary thành công');

                            } catch (error) {

                                console.error(error);

                                $('#imageUrl').val('');
                                $('#imagePublicId').val('');
                                $('#imageResourceType').val('');

                                alert('Upload ảnh thất bại');
                            }

                        });

                    });

                    $('#productForm').on('submit', function (e) {

                        if (!$('#imageUrl').val()) {

                            e.preventDefault();

                            Swal.fire({
                                icon: 'warning',
                                title: 'Ảnh sản phẩm chưa được upload'
                            });

                            return false;
                        }

                    });
                </script>
            </body>

            </html>