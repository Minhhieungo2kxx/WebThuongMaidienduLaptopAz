<%@page contentType="text/html" pageEncoding="UTF-8" %>
      <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
            <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
                  <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


                        <html lang="en">

                        <head>
                              <meta charset="UTF-8">
                              <meta name="viewport" content="width=device-width, initial-scale=1.0">
                              <title>Ecornomere</title>

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
                                    /* Đảm bảo các mục phân trang nằm trên một hàng */
                                    .pagination {
                                          display: flex;
                                          /* Sử dụng flexbox */
                                          flex-wrap: wrap;
                                          /* Cho phép xuống dòng nếu quá dài */
                                          padding-left: 0;
                                          list-style: none;
                                          border-radius: 0.3rem;
                                          padding-top: 1rem;
                                          /* Theo Bootstrap */
                                    }

                                    .page-item {
                                          display: list-item;
                                          /* Mặc định của li, nhưng để đảm bảo */
                                    }

                                    /* Đảm bảo link và span trong page-item hiển thị đúng */
                                    .page-link {
                                          display: block;
                                          /* Hoặc inline-block nếu cần */
                                    }

                                    .search-display-box {
                                          position: fixed;
                                          top: 100px;
                                          /* Dưới phần header */
                                          right: 20px;
                                          z-index: 1050;
                                          background-color: rgba(255, 255, 255, 0.95);
                                          padding: 12px 18px;
                                          border-radius: 8px;
                                          box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
                                          display: flex;
                                          align-items: center;
                                          max-width: 300px;
                                    }

                                    .search-display-box strong {
                                          color: #007bff;
                                    }

                                    .search-display-box .close-btn {
                                          margin-left: auto;
                                          cursor: pointer;
                                          font-size: 18px;
                                          color: #888;
                                    }

                                    .search-display-box .close-btn:hover {
                                          color: #dc3545;
                                    }

                                    .py-5 {
                                          padding-top: 5rem !important;
                                          padding-bottom: 2rem !important;
                                    }

                                    .mt-5 {
                                          margin-top: 1rem !important;
                                    }
                              </style>

                        </head>

                        <body>

                              <!-- Spinner Start -->
                              <div id="spinner"
                                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                                    <div class="spinner-grow text-primary" role="status"></div>
                              </div>
                              <!-- Spinner End -->


                              <jsp:include page="../layout/header.jsp" />
                              <c:if test="${not empty searchTerm}">
                                    <div class="search-display-box" id="searchBox">
                                          <span>Đang tìm kiếm với từ khóa: <strong>"${searchTerm}"</strong></span>
                                          <span class="close-btn"
                                                onclick="document.getElementById('searchBox').style.display='none';">&times;</span>
                                    </div>
                              </c:if>


                              <!-- Search Overlay -->
                              <div id="search-overlay" class="search-overlay d-none">
                                    <div class="search-box">
                                          <form action="/product/search" method="get">
                                                <input type="text" name="searchTerm" placeholder="Tìm kiếm sản phẩm..."
                                                      class="form-control form-control-lg" autofocus />
                                                <button type="submit" class="btn btn-primary mt-3">Tìm kiếm</button>
                                          </form>
                                    </div>
                              </div>

                              <!-- Fruits Shop Start-->
                              <div class="container-fluid fruite py-5">
                                    <!-- Giữ nguyên class fruite để tận dụng CSS hiện có nếu cần -->
                                    <div class="container py-5">


                                          <h2 class="mb-4">
                                                <c:choose>
                                                      <c:when test="${not empty allProducts}">
                                                            Tìm thấy <strong>${totalResults}</strong> sản phẩm
                                                      </c:when>
                                                      <c:otherwise>
                                                            Không tìm thấy sản phẩm phù hợp!
                                                      </c:otherwise>
                                                </c:choose>
                                          </h2>

                                          <c:if test="${not empty allProducts}">
                                                <div class="row g-4">
                                                      <div class="col-lg-12">
                                                            <div class="row g-4">
                                                                  <c:forEach var="product" items="${allProducts}">
                                                                        <div class="col-md-6 col-lg-4 col-xl-3">
                                                                              <div
                                                                                    class="rounded position-relative laptop-item">
                                                                                    <div class="laptop-img">
                                                                                          <img src="/uploads/products/${product.image}"
                                                                                                class="img-fluid w-100 rounded-top"
                                                                                                alt="${product.name}" />
                                                                                    </div>
                                                                                    <div class="text-white bg-secondary px-3 py-1 rounded position-absolute"
                                                                                          style="top: 10px; left: 10px;">
                                                                                          Laptop
                                                                                    </div>
                                                                                    <div
                                                                                          class="p-4 border border-secondary border-top-0 rounded-bottom">
                                                                                          <a href="/product/detail/${product.id}"
                                                                                                title="Xem chi tiết">
                                                                                                <h4>${product.name}</h4>
                                                                                          </a>
                                                                                          <p
                                                                                                style="font-size: 16px; color: #555;">
                                                                                                Số lượng còn:
                                                                                                ${product.quantity}</p>
                                                                                          <p
                                                                                                style="font-size: 16px; color: #555;">
                                                                                                Hãng sản xuất:
                                                                                                ${product.factory}</p>
                                                                                          <p
                                                                                                style="font-size: 16px; color: #555;">
                                                                                                Đối tượng phù hợp:
                                                                                                ${product.target}</p>

                                                                                          <p
                                                                                                style="font-size: 14px; color: #888; margin-top: 5px;">
                                                                                                ${product.shortDesc}
                                                                                          </p>
                                                                                          <div
                                                                                                class="d-flex justify-content-center align-items-center flex-lg-wrap">
                                                                                                <p
                                                                                                      class="text-dark fs-5 fw-bold mb-0 me-3">
                                                                                                      <fmt:formatNumber
                                                                                                            value="${product.price}"
                                                                                                            type="number"
                                                                                                            groupingUsed="true" />
                                                                                                      VNĐ
                                                                                                </p>

                                                                                                <c:choose>
                                                                                                      <c:when
                                                                                                            test="${product.quantity > 0}">
                                                                                                            <form:form
                                                                                                                  method="post"
                                                                                                                  action="/add-cart/${product.id}">
                                                                                                                  <button
                                                                                                                        type="submit"
                                                                                                                        class="btn border border-secondary rounded-pill px-3 text-primary">
                                                                                                                        <i
                                                                                                                              class="fa fa-shopping-cart me-2 text-primary"></i>
                                                                                                                        Thêm
                                                                                                                        vào
                                                                                                                        giỏ
                                                                                                                        hàng
                                                                                                                  </button>
                                                                                                                  <input type="hidden"
                                                                                                                        name="${_csrf.parameterName}"
                                                                                                                        value="${_csrf.token}" />
                                                                                                            </form:form>
                                                                                                      </c:when>
                                                                                                      <c:otherwise>
                                                                                                            <span
                                                                                                                  class="text-danger fw-bold">Hết
                                                                                                                  hàng</span>
                                                                                                      </c:otherwise>
                                                                                                </c:choose>
                                                                                          </div>
                                                                                    </div>
                                                                              </div>
                                                                        </div>
                                                                  </c:forEach>
                                                            </div>
                                                      </div>
                                                </div>

                                                <!-- Phân trang -->
                                                <nav aria-label="Page navigation" class="mt-4 justify-content-center">
                                                      <ul class="pagination justify-content-center mt-4">
                                                            <c:if test="${currentPage > 0}">
                                                                  <li class="page-item">
                                                                        <a class="page-link"
                                                                              href="?searchTerm=${searchTerm}&page=${currentPage - 1}"
                                                                              aria-label="Previous">
                                                                              <span aria-hidden="true">«</span>
                                                                        </a>
                                                                  </li>
                                                            </c:if>

                                                            <c:forEach var="i" begin="0" end="${totalPages - 1}">
                                                                  <li
                                                                        class="page-item ${i == currentPage ? 'active' : ''}">
                                                                        <a class="page-link"
                                                                              href="?searchTerm=${searchTerm}&page=${i}">${i
                                                                              + 1}</a>
                                                                  </li>
                                                            </c:forEach>

                                                            <c:if test="${currentPage < totalPages - 1}">
                                                                  <li class="page-item">
                                                                        <a class="page-link"
                                                                              href="?searchTerm=${searchTerm}&page=${currentPage + 1}"
                                                                              aria-label="Next">
                                                                              <span aria-hidden="true">»</span>
                                                                        </a>
                                                                  </li>
                                                            </c:if>
                                                      </ul>
                                                </nav>
                                          </c:if>



                                    </div>


                              </div>
                              <!-- Fruits Shop End-->



                              <jsp:include page="../layout/footer.jsp" />

                              <!-- JavaScript Libraries -->
                              <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                              <script
                                    src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                              <script src="/client/lib/easing/easing.min.js"></script>
                              <script src="/client/lib/waypoints/waypoints.min.js"></script>
                              <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                              <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                              <!-- Template Javascript -->
                              <script src="/client/js/main.js"></script>


                              <script>
                                    const searchIcon = document.getElementById("search-icon");
                                    const searchOverlay = document.getElementById("search-overlay");

                                    // Hiện overlay khi nhấn icon
                                    searchIcon.addEventListener("click", function () {
                                          searchOverlay.classList.remove("d-none");
                                          // Tự focus vào ô input
                                          const input = searchOverlay.querySelector("input");
                                          if (input) {
                                                setTimeout(() => input.focus(), 100);
                                          }
                                    });

                                    // Ẩn overlay khi nhấn ngoài vùng hoặc ESC
                                    searchOverlay.addEventListener("click", function (e) {
                                          if (e.target === this) {
                                                searchOverlay.classList.add("d-none");
                                          }
                                    });

                                    document.addEventListener("keydown", function (e) {
                                          if (e.key === "Escape") {
                                                searchOverlay.classList.add("d-none");
                                          }
                                    });
                              </script>

                        </body>

                        </html>