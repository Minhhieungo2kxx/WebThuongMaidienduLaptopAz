<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

                        <html lang="en">

                        <head>
                              <meta charset="UTF-8">
                              <meta name="viewport" content="width=device-width, initial-scale=1.0">
                              <title>Ecornomere - Danh sách sản phẩm</title>

                              <!-- Google Web Fonts -->
                              <link rel="preconnect" href="https://fonts.googleapis.com">
                              <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                              <link href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                                    rel="stylesheet">

                              <!-- Icon Font Stylesheet -->
                              <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                              <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                                    rel="stylesheet">

                              <!-- Libraries Stylesheet -->
                              <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                              <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">

                              <!-- Customized Bootstrap Stylesheet -->
                              <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                              <!-- Template Stylesheet -->
                              <link href="/client/css/style.css" rel="stylesheet">

                              <style>
                                    /* Custom styles for improved design */
                                    .filter-sidebar {
                                          background: #f8f9fa;
                                          border-radius: 12px;
                                          box-shadow: 0 4px 15px rgba(0, 0, 0, 0.1);
                                          position: sticky;
                                          top: 20px;
                                          max-height: calc(100vh - 40px);
                                          overflow-y: auto;
                                    }

                                    .filter-section {
                                          border-bottom: 1px solid #e9ecef;
                                          padding: 1.5rem;
                                    }

                                    .filter-section:last-child {
                                          border-bottom: none;
                                    }

                                    .filter-title {
                                          font-weight: 600;
                                          color: #2c3e50;
                                          margin-bottom: 1rem;
                                          font-size: 1.1rem;
                                    }

                                    .filter-option {
                                          margin-bottom: 0.8rem;
                                    }

                                    .filter-option label {
                                          font-weight: 400;
                                          color: #495057;
                                          cursor: pointer;
                                          display: flex;
                                          align-items: center;
                                    }

                                    .filter-option input[type="checkbox"] {
                                          margin-right: 0.5rem;
                                          transform: scale(1.1);
                                    }

                                    .price-range-input {
                                          width: 100%;
                                          margin-bottom: 0.5rem;
                                    }

                                    .product-card {
                                          background: white;
                                          border-radius: 15px;
                                          box-shadow: 0 5px 20px rgba(0, 0, 0, 0.08);
                                          transition: all 0.3s ease;
                                          overflow: hidden;
                                          height: 100%;
                                    }

                                    .product-card:hover {
                                          transform: translateY(-5px);
                                          box-shadow: 0 10px 30px rgba(0, 0, 0, 0.15);
                                    }

                                    .product-img {
                                          position: relative;
                                          overflow: hidden;
                                          height: 200px;
                                    }

                                    .product-img img {
                                          width: 100%;
                                          height: 100%;
                                          object-fit: cover;
                                          transition: transform 0.3s ease;
                                    }

                                    .product-card:hover .product-img img {
                                          transform: scale(1.05);
                                    }

                                    .product-badge {
                                          position: absolute;
                                          top: 15px;
                                          left: 15px;
                                          background: linear-gradient(45deg, #ff6b6b, #ee5a24);
                                          color: white;
                                          padding: 0.4rem 0.8rem;
                                          border-radius: 20px;
                                          font-size: 0.8rem;
                                          font-weight: 600;
                                          box-shadow: 0 2px 10px rgba(255, 107, 107, 0.3);
                                    }

                                    .product-content {
                                          padding: 1.5rem;
                                          display: flex;
                                          flex-direction: column;
                                          height: calc(100% - 200px);
                                    }

                                    .product-title {
                                          font-size: 1.1rem;
                                          font-weight: 600;
                                          color: #2c3e50;
                                          margin-bottom: 0.8rem;
                                          text-decoration: none;
                                          line-height: 1.4;
                                    }

                                    .product-title:hover {
                                          color: #3498db;
                                          text-decoration: none;
                                    }

                                    .product-desc {
                                          color: #6c757d;
                                          font-size: 0.9rem;
                                          margin-bottom: 1rem;
                                          flex-grow: 1;
                                          line-height: 1.5;
                                    }

                                    .product-price {
                                          font-size: 1.3rem;
                                          font-weight: 700;
                                          color: #e74c3c;
                                          margin-bottom: 1rem;
                                    }

                                    .add-to-cart-btn {
                                          background: linear-gradient(45deg, #3498db, #2980b9);
                                          border: none;
                                          color: white;
                                          padding: 0.7rem 1.5rem;
                                          border-radius: 25px;
                                          font-weight: 600;
                                          transition: all 0.3s ease;
                                          width: 100%;
                                    }

                                    .add-to-cart-btn:hover {
                                          background: linear-gradient(45deg, #2980b9, #1f5f8b);
                                          transform: translateY(-1px);
                                          box-shadow: 0 5px 15px rgba(52, 152, 219, 0.3);
                                          color: white;
                                    }

                                    .filter-btn {
                                          background: linear-gradient(45deg, #27ae60, #229954);
                                          border: none;
                                          color: white;
                                          padding: 0.8rem 2rem;
                                          border-radius: 25px;
                                          font-weight: 600;
                                          width: 100%;
                                          transition: all 0.3s ease;
                                    }

                                    .filter-btn:hover {
                                          background: linear-gradient(45deg, #229954, #1e7e34);
                                          transform: translateY(-1px);
                                          box-shadow: 0 5px 15px rgba(39, 174, 96, 0.3);
                                    }

                                    .clear-filter-btn {
                                          background: transparent;
                                          border: 2px solid #e74c3c;
                                          color: #e74c3c;
                                          padding: 0.6rem 1.5rem;
                                          border-radius: 25px;
                                          font-weight: 600;
                                          width: 100%;
                                          margin-top: 0.5rem;
                                          transition: all 0.3s ease;
                                    }

                                    .clear-filter-btn:hover {
                                          background: #e74c3c;
                                          color: white;
                                    }

                                    .page-header {
                                          background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
                                          color: white;
                                          padding: 2rem 0;
                                          border-radius: 0 0 30px 30px;
                                          margin-bottom: 2rem;
                                    }

                                    .breadcrumb-custom {
                                          background: rgba(255, 255, 255, 0.1);
                                          padding: 0.8rem 1.5rem;
                                          border-radius: 25px;
                                          color: white;
                                          font-weight: 500;
                                    }

                                    .pagination {
                                          display: flex;
                                          flex-wrap: wrap;
                                          padding-left: 0;
                                          list-style: none;
                                          border-radius: 0.3rem;
                                    }

                                    .page-item .page-link {
                                          border: none;
                                          padding: 0.7rem 1rem;
                                          margin: 0 0.2rem;
                                          border-radius: 8px;
                                          color: #495057;
                                          background: #f8f9fa;
                                          transition: all 0.3s ease;
                                    }

                                    .page-item.active .page-link {
                                          background: linear-gradient(45deg, #3498db, #2980b9);
                                          color: white;
                                          box-shadow: 0 3px 10px rgba(52, 152, 219, 0.3);
                                    }

                                    .page-item .page-link:hover {
                                          background: #3498db;
                                          color: white;
                                    }

                                    .product-grid {
                                          min-height: 400px;
                                    }

                                    /* Responsive adjustments */
                                    @media (max-width: 768px) {
                                          .filter-sidebar {
                                                margin-bottom: 2rem;
                                                position: relative;
                                                top: auto;
                                                max-height: none;
                                          }

                                          .product-card {
                                                margin-bottom: 1.5rem;
                                          }
                                    }

                                    /* Loading spinner */
                                    #spinner {
                                          background: rgba(255, 255, 255, 0.9);
                                          backdrop-filter: blur(10px);
                                    }

                                    .spinner-grow {
                                          width: 3rem;
                                          height: 3rem;
                                    }
                              </style>
                        </head>

                        <body>
                              <!-- Spinner Start -->
                              <div id="spinner"
                                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50 d-flex align-items-center justify-content-center">
                                    <div class="spinner-grow text-primary" role="status"></div>
                              </div>
                              <!-- Spinner End -->

                              <jsp:include page="../layout/header.jsp" />

                              <!-- Page Header -->
                              <div class="page-header">
                                    <div class="container">
                                          <div class="breadcrumb-custom">
                                                <i class="fas fa-home me-2"></i>
                                                Trang chủ / Danh sách sản phẩm
                                          </div>
                                          <h1 class="mt-3 mb-0">Khám phá sản phẩm của chúng tôi</h1>
                                    </div>
                              </div>

                              <!-- Products Section Start -->
                              <div class="container-fluid py-5">
                                    <div class="container">
                                          <div class="row g-4">
                                                <!-- Filter Sidebar -->
                                                <div class="col-lg-3">
                                                      <form id="filterForm" method="get" action="/products">
                                                            <!-- các checkbox filter bạn đã viết ở trên giữ nguyên -->
                                                            <div class="filter-sidebar">
                                                                  <!-- Brand Filter -->
                                                                  <div class="filter-section">
                                                                        <h5 class="filter-title">
                                                                              Thương hiệu
                                                                        </h5>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox"
                                                                                          name="factory" value="Apple"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('Apple')}">checked
                                                                                    </c:if> >
                                                                                    Apple
                                                                                    <span class="text-muted"></span>


                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox"
                                                                                          name="factory" value="Dell"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('Dell')}">checked
                                                                                    </c:if> >
                                                                                    Dell <span
                                                                                          class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox"
                                                                                          name="factory" value="HP"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('HP')}">checked
                                                                                    </c:if> >

                                                                                    HP <span class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox"
                                                                                          name="factory" value="Lenovo"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('Lenovo')}">checked
                                                                                    </c:if> >
                                                                                    Lenovo <span
                                                                                          class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox"
                                                                                          name="factory" value="ASUS"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('ASUS')}">checked
                                                                                    </c:if> >
                                                                                    Asus <span
                                                                                          class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox"
                                                                                          name="factory" value="Samsung"
                                                                                          <c:if
                                                                                          test="${param.factory != null && param.factory.contains('Samsung')}">checked
                                                                                    </c:if> >
                                                                                    Samsung <span
                                                                                          class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox"
                                                                                          name="factory"
                                                                                          value="Microsoft" <c:if
                                                                                          test="${param.factory != null && param.factory.contains('Microsoft')}">checked
                                                                                    </c:if> >
                                                                                    Microsoft <span
                                                                                          class="text-muted"></span>
                                                                              </label>
                                                                        </div>
                                                                  </div>

                                                                  <div class="filter-section">
                                                                        <h5 class="filter-title">
                                                                              Mục đích
                                                                              sử dụng
                                                                        </h5>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="target"
                                                                                          value="Gaming" <c:if
                                                                                          test="${param.target != null && param.target.contains('Gaming')}">checked
                                                                                    </c:if> >
                                                                                    Gammming
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="target"
                                                                                          value="Van phong" <c:if
                                                                                          test="${param.target != null && param.target.contains('Van phong')}">checked
                                                                                    </c:if> >
                                                                                    Văn phòng
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox" name="target"
                                                                                          value="Thiet ke do hoa" <c:if
                                                                                          test="${param.target != null && param.target.contains('Thiet ke do hoa')}">checked
                                                                                    </c:if> >
                                                                                    Thiết kế đồ họa
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="target"
                                                                                          value="Mong nhe" <c:if
                                                                                          test="${param.target != null && param.target.contains('Mong nhe')}">checked
                                                                                    </c:if> >
                                                                                    Mỏng nhẹ
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="target"
                                                                                          value="Doanh nhan" <c:if
                                                                                          test="${param.target != null && param.target.contains('Doanh nhan')}">checked
                                                                                    </c:if> >
                                                                                    Doanh nhân
                                                                              </label>
                                                                        </div>
                                                                  </div>

                                                                  <!-- Price Range Filter -->
                                                                  <div class="filter-section">
                                                                        <h5 class="filter-title">
                                                                              Khoảng
                                                                              giá
                                                                        </h5>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="price"
                                                                                          value="0-15000000" <c:if
                                                                                          test="${param.price!= null && param.price.contains('0-15000000')}">checked
                                                                                    </c:if> >
                                                                                    Dưới 15 triệu
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="price"
                                                                                          value="15000000-25000000"
                                                                                          <c:if
                                                                                          test="${param.price!= null && param.price.contains('15000000-25000000')}">checked
                                                                                    </c:if> >
                                                                                    15 - 25 triệu
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox" name="price"
                                                                                          value="25000000-30000000"
                                                                                          <c:if
                                                                                          test="${param.price!= null && param.price.contains('25000000-30000000')}">checked
                                                                                    </c:if> >
                                                                                    25 - 30 triệu
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>
                                                                                    <input type="checkbox" name="price"
                                                                                          value="30000000-999999999"
                                                                                          <c:if
                                                                                          test="${param.price!= null && param.price.contains('30000000-999999999')}">checked
                                                                                    </c:if> >
                                                                                    Trên 30 triệu
                                                                              </label>
                                                                        </div>
                                                                  </div>

                                                                  <!-- Screen Size Filter -->
                                                                  <div class="filter-section">
                                                                        <h5 class="filter-title">

                                                                              </i>Sắp xếp

                                                                        </h5>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="sort"
                                                                                          value="price-asc" <c:if
                                                                                          test="${param.sort!= null && param.sort.contains('price-asc')}">checked
                                                                                    </c:if> >
                                                                                    Giá tăng dần
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="sort"
                                                                                          value="price-desc" <c:if
                                                                                          test="${param.sort!= null && param.sort.contains('price-desc')}">checked
                                                                                    </c:if> >
                                                                                    Giá giảm dần
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="sort"
                                                                                          value="name-asc" <c:if
                                                                                          test="${param.sort!= null && param.sort.contains('name-asc')}">checked
                                                                                    </c:if> >
                                                                                    Tên từ A->Z
                                                                              </label>
                                                                        </div>
                                                                        <div class="filter-option">
                                                                              <label>

                                                                                    <input type="checkbox" name="sort"
                                                                                          value="no-sort" <c:if
                                                                                          test="${param.sort!= null && param.sort.contains('no-sort')}">checked
                                                                                    </c:if> >
                                                                                    Không sắp xếp
                                                                              </label>
                                                                        </div>

                                                                  </div>



                                                                  <!-- Filter Buttons -->
                                                                  <div class="filter-section">
                                                                        <!-- <button type="button" class="filter-btn"
                                                                              onclick="applyFilters()">
                                                                              <i class="fas fa-filter me-2"></i>Lọc sản
                                                                              phẩm
                                                                        </button> -->
                                                                        <button type="submit" class="filter-btn">
                                                                              <i class="fas fa-filter me-2"></i>Lọc sản
                                                                              phẩm </button>
                                                                        <button type="button" class="clear-filter-btn"
                                                                              onclick="clearFilters()">
                                                                              <i class="fas fa-times me-2"></i>Xóa bộ
                                                                              lọc
                                                                        </button>
                                                                  </div>
                                                            </div>

                                                      </form>

                                                </div>

                                                <!-- Products Grid -->
                                                <div class="col-lg-9">
                                                      <!-- Sort and View Options -->
                                                      <div
                                                            class="d-flex justify-content-between align-items-center mb-4">
                                                            <div>
                                                                  <span class="text-muted">Hiển thị
                                                                        ${allProducts.size()} sản phẩm</span>
                                                            </div>
                                                            <!-- <div class="d-flex align-items-center">
                                                                  <label class="me-2">Sắp xếp:</label>
                                                                  <select class="form-select" style="width: auto;">

                                                                        <option>Giá thấp đến cao</option>
                                                                        <option>Giá cao đến thấp</option>
                                                                        <option>Tên A-Z</option>
                                                                  </select>
                                                            </div> -->

                                                            <form method="get" action="/products-sort"
                                                                  class="d-flex align-items-center mb-3">
                                                                  <label class="me-2">Sắp xếp:</label>
                                                                  <select class="form-select" name="sort"
                                                                        style="width: auto;"
                                                                        onchange="this.form.submit()">
                                                                        <option value="" ${sort=='' ? 'selected' : '' }>
                                                                              -- Không sắp xếp --</option>
                                                                        <option value="price-asc" ${sort=='price-asc'
                                                                              ? 'selected' : '' }>Giá thấp đến cao
                                                                        </option>
                                                                        <option value="price-desc" ${sort=='price-desc'
                                                                              ? 'selected' : '' }>Giá cao đến thấp
                                                                        </option>
                                                                        <option value="name-asc" ${sort=='name-asc'
                                                                              ? 'selected' : '' }>Tên A-Z</option>
                                                                  </select>
                                                            </form>

                                                      </div>
                                                      <c:if test="${empty allProducts}">
                                                            <div class="alert alert-warning text-center" role="alert">
                                                                  Không tìm thấy sản phẩm phù hợp với bộ
                                                                  lọc.
                                                            </div>
                                                      </c:if>

                                                      <!-- Products Grid -->
                                                      <div class="product-grid">
                                                            <div class="row g-4">
                                                                  <c:forEach var="product" items="${allProducts}">
                                                                        <div class="col-md-6 col-xl-4">
                                                                              <div class="product-card">
                                                                                    <div class="product-img">
                                                                                          <img src="/uploads/products/${product.image}"
                                                                                                alt="${product.name}" />
                                                                                          <div class="product-badge">
                                                                                                Laptop</div>
                                                                                    </div>
                                                                                    <div class="product-content">
                                                                                          <a href="/product/detail/${product.id}"
                                                                                                class="product-title">
                                                                                                ${product.name}
                                                                                          </a>
                                                                                          <p class="product-desc">
                                                                                                ${product.shortDesc}</p>
                                                                                          <p class="product-desc">
                                                                                                ${product.factory}</p>
                                                                                          <p class="product-desc">
                                                                                                ${product.target}</p>
                                                                                          <div class="mt-auto">
                                                                                                <div
                                                                                                      class="product-price mb-3">
                                                                                                      <fmt:formatNumber
                                                                                                            value="${product.price}"
                                                                                                            type="number"
                                                                                                            groupingUsed="true" />
                                                                                                      VNĐ
                                                                                                </div>
                                                                                                <form:form method="post"
                                                                                                      action="/add-cart/${product.id}">
                                                                                                      <button
                                                                                                            type="submit"
                                                                                                            class="add-to-cart-btn">
                                                                                                            <i
                                                                                                                  class="fas fa-shopping-cart me-2"></i>
                                                                                                            Thêm vào giỏ
                                                                                                            hàng
                                                                                                      </button>
                                                                                                      <input type="hidden"
                                                                                                            name="${_csrf.parameterName}"
                                                                                                            value="${_csrf.token}" />
                                                                                                </form:form>
                                                                                          </div>
                                                                                    </div>
                                                                              </div>
                                                                        </div>
                                                                  </c:forEach>
                                                            </div>
                                                      </div>

                                                      <!-- Pagination -->
                                                      <nav aria-label="Page navigation" class="mt-5">
                                                            <ul class="pagination justify-content-center">
                                                                  <!-- Previous Button -->
                                                                  <c:if test="${currentPage > 0}">
                                                                        <li class="page-item">
                                                                              <a class="page-link"
                                                                                    href="?page=${currentPage - 1}"
                                                                                    aria-label="Previous">
                                                                                    <i class="fas fa-chevron-left"></i>
                                                                              </a>
                                                                        </li>
                                                                  </c:if>

                                                                  <c:if test="${not empty allProducts}">
                                                                        <c:forEach var="i" begin="0"
                                                                              end="${totalPages - 1}">
                                                                              <!-- Xây dựng URL với các tham số lọc -->
                                                                              <c:set var="url"
                                                                                    value="${pageContext.request.contextPath}/products?page=${i}" />

                                                                              <c:forEach var="f"
                                                                                    items="${paramValues.factory}">
                                                                                    <c:set var="url"
                                                                                          value="${url}&factory=${f}" />
                                                                              </c:forEach>
                                                                              <c:forEach var="t"
                                                                                    items="${paramValues.target}">
                                                                                    <c:set var="url"
                                                                                          value="${url}&target=${t}" />
                                                                              </c:forEach>
                                                                              <c:forEach var="p"
                                                                                    items="${paramValues.price}">
                                                                                    <c:set var="url"
                                                                                          value="${url}&price=${p}" />
                                                                              </c:forEach>
                                                                              <c:forEach var="s"
                                                                                    items="${paramValues.sort}">
                                                                                    <c:set var="url"
                                                                                          value="${url}&sort=${s}" />
                                                                              </c:forEach>

                                                                              <!-- Hiển thị phân trang -->
                                                                              <li
                                                                                    class="page-item ${i == currentPage ? 'active' : ''}">
                                                                                    <a class="page-link"
                                                                                          href="${url}">${i +
                                                                                          1}</a>
                                                                              </li>
                                                                        </c:forEach>

                                                                  </c:if>



                                                                  <!-- Next Button -->
                                                                  <c:if test=" ${currentPage < totalPages - 1}">
                                                                        <li class="page-item">
                                                                              <a class="page-link"
                                                                                    href="?page=${currentPage + 1}"
                                                                                    aria-label="Next">
                                                                                    <i class="fas fa-chevron-right"></i>
                                                                              </a>
                                                                        </li>
                                                                  </c:if>
                                                            </ul>
                                                      </nav>
                                                </div>
                                          </div>
                                    </div>
                              </div>
                              <!-- Products Section End -->

                              <jsp:include page="../layout/footer.jsp" />

                              <!-- JavaScript Libraries -->
                              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                              <script
                                    src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                              <script src="/client/lib/easing/easing.min.js"></script>
                              <script src="/client/lib/waypoints/waypoints.min.js"></script>
                              <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                              <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>
                              <script src="/client/js/main.js"></script>

                              <script>
                                    // Filter functionality
                                    // function applyFilters() {
                                    //       const factory = [];
                                    //       const target = [];
                                    //       const price = [];
                                    //       const sort = [];

                                    //       // Collect selected filters
                                    //       document.querySelectorAll('input[name="factory"]:checked').forEach(el => {
                                    //             factory.push(el.value);
                                    //       });
                                    //       document.querySelectorAll('input[name="target"]:checked').forEach(el => {
                                    //             target.push(el.value);
                                    //       });
                                    //       document.querySelectorAll('input[name="price"]:checked').forEach(el => {
                                    //             price.push(el.value);
                                    //       });
                                    //       document.querySelectorAll('input[name="sort"]:checked').forEach(el => {
                                    //             sort.push(el.value);
                                    //       });

                                    //       // Build query string
                                    //       const params = new URLSearchParams();

                                    //       if (factory.length > 0) params.append('factory', factory.join(','));
                                    //       if (target.length > 0) params.append('target', target.join(','));
                                    //       if (price.length > 0) params.append('price', price.join(','));
                                    //       if (sort.length > 0) params.append('sort', sort.join(','));

                                    //       // Redirect with filters
                                    //       window.location.href = '?' + params.toString();
                                    // }

                                    // function clearFilters() {
                                    //       document.querySelectorAll('input[type="checkbox"]').forEach(el => {
                                    //             el.checked = false;
                                    //       });
                                    //       window.location.href = window.location.pathname;
                                    // }
                                    function clearFilters() {
                                          document.querySelectorAll('#filterForm input[type=checkbox]').forEach(cb => cb.checked = false);
                                          document.getElementById('filterForm').submit();
                                    }

                                    // Smooth scrolling for better UX
                                    document.addEventListener('DOMContentLoaded', function () {
                                          // Hide spinner
                                          setTimeout(() => {
                                                document.getElementById('spinner').classList.remove('show');
                                          }, 500);

                                          // Add smooth scroll behavior
                                          document.querySelectorAll('a[href^="#"]').forEach(anchor => {
                                                anchor.addEventListener('click', function (e) {
                                                      e.preventDefault();
                                                      document.querySelector(this.getAttribute('href')).scrollIntoView({
                                                            behavior: 'smooth'
                                                      });
                                                });
                                          });
                                    });

                                    // Add to cart animation
                                    document.querySelectorAll('.add-to-cart-btn').forEach(btn => {
                                          btn.addEventListener('click', function (e) {
                                                const icon = this.querySelector('i');
                                                icon.classList.add('fa-spin');
                                                setTimeout(() => {
                                                      icon.classList.remove('fa-spin');
                                                }, 1000);
                                          });
                                    });
                              </script>
                        </body>

                        </html>