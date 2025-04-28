<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%-- Import tag libraries --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %> <%-- Thẻ form taglib không cần thiết ở
          đây nếu chỉ hiển thị dữ liệu --%>


          <!DOCTYPE html>
          <html lang="en">

          <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>User List</title> <%-- Tiêu đề rõ ràng hơn --%>

              <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">


              <!-- <%-- <link href="/css/demo.css" rel="stylesheet"> --%> -->


          </head>

          <body>
            <div class="container mt-5">
              <div class="row justify-content-center">
                <div class="col-12">

                  <div class="d-flex justify-content-between align-items-center mb-4"> <%-- Căn chỉnh tiêu đề và button,
                      thêm margin-bottom --%>
                      <h3>User List</h3> <%-- Tiêu đề rõ ràng hơn --%>
                        <a href="/admin/user/create" class="btn btn-primary">
                          <i class="fas fa-plus"></i> Create New User <%-- Thêm icon và text rõ ràng hơn (cần Font
                            Awesome) --%>
                        </a>
                  </div>

                  <hr class="mb-4" /> <%-- Thêm margin-bottom cho đường kẻ --%>

                    <%-- Check if users list is not empty --%>
                      <c:choose>
                        <c:when test="${not empty users}">
                          <div class="table-responsive"> <%-- Giúp bảng cuộn ngang trên màn hình nhỏ --%>
                              <table class="table table-bordered table-hover align-middle"> <%-- Thêm align-middle để
                                  căn giữa nội dung cell theo chiều dọc --%>
                                  <thead>
                                    <tr class="table-primary"> <%-- Thêm màu nền cho header (tùy chọn) --%>
                                        <th>ID</th>
                                        <th>Email</th>
                                        <th>Full Name</th>
                                        <th>Address</th>
                                        <th>Number phone</th>
                                        <th>Actions</th> <%-- Đổi tên cột --%>
                                    </tr>
                                  </thead>
                                  <tbody>

                                    <c:if test="${not empty successMessage}">
                                      <script>
                                        alert('${successMessage}');
                                      </script>
                                    </c:if>
                                    <%-- Iterate through the list of users --%>
                                      <c:forEach items="${users}" var="user">
                                        <tr>
                                          <td>${user.id}</td>
                                          <td>${user.email}</td>
                                          <td>${user.fullName}</td>
                                          <td>${user.address}</td>
                                          <td>${user.phone}</td>
                                          <td>
                                            <%-- Action buttons --%>
                                              <a href="/admin/user/detail/${user.id}" class="btn btn-info btn-sm me-1"
                                                title="View Details"> <%-- btn-sm để button nhỏ hơn, me-1 thêm margin
                                                  phải, title cho tooltip --%>
                                                  View
                                              </a>
                                              <a href="/admin/user/edit/${user.id}" class="btn btn-warning btn-sm me-1"
                                                title="Edit User"> <%-- btn-sm, me-1, title --%>
                                                  Edit
                                              </a>
                                              <%-- Delete button (often requires confirmation via JavaScript, but here
                                                is a direct link) --%>
                                                <a href="/admin/user/delete/${user.id}" class="btn btn-danger btn-sm"
                                                  onclick="return confirm('Are you sure you want to delete user ${user.email}?');"
                                                  title="Delete User">
                                                  Delete
                                                </a>
                                          </td>
                                        </tr>
                                      </c:forEach>
                                  </tbody>
                              </table>
                          </div> <%-- End table-responsive --%>
                        </c:when>
                        <c:otherwise>
                          <div class="alert alert-info" role="alert">
                            No users found.
                          </div>
                        </c:otherwise>
                      </c:choose>

                </div> <%-- End col --%>
              </div> <%-- End row --%>
            </div> <%-- End container --%>

              <%-- Latest compiled JavaScript (Bootstrap bundle includes Popper) --%>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
                <%-- jQuery (Include only if needed for other scripts, Bootstrap 5 doesn't require it) --%>
                  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                  <%-- Font Awesome for icons (Optional) --%>
                    <%-- <script src="https://kit.fontawesome.com/your-font-awesome-kit-id.js" crossorigin="anonymous">
                      </script> --%>


          </body>

          </html>