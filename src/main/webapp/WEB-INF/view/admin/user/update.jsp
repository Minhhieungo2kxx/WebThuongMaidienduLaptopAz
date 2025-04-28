<%@page contentType="text/html" pageEncoding="UTF-8" %>
  <%-- Import tag libraries --%>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
      <%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

        <!DOCTYPE html>
        <html lang="en">

        <head>
          <meta charset="UTF-8">
          <meta name="viewport" content="width=device-width, initial-scale=1.0">
          <title>Update User</title>

          <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">

          <!-- <link rel="stylesheet" href="./css/demo.css"> -->


        </head>

        <body>

          <div class="container mt-5">
            <div class="row justify-content-center">
              <div class="col-md-6 col-12">
                <h3 class="mb-4 text-center">Update User Details</h3>
                <hr class="mb-4" />

                <form:form method="post" action="/admin/list/user" modelAttribute="updatedUser">

                  <form:input type="hidden" path="id" />

                  <div class="mb-3">
                    <label for="email" class="form-label">Email:</label>

                    <form:input type="email" class="form-control" id="email" path="email" />
                  </div>


                  <div class="mb-3">
                    <label for="password" class="form-label">New Password:</label>
                    <form:input type="password" class="form-control" id="password" path="password"
                      placeholder="Leave blank to keep current" />
                  </div>



                  <div class="mb-3">
                    <label for="phone" class="form-label">Phone number:</label>
                    <form:input type="text" class="form-control" id="phone" path="phone"
                      placeholder="Enter phone number" />
                  </div>

                  <div class="mb-3">
                    <label for="fullName" class="form-label">Full Name:</label>
                    <form:input type="text" class="form-control" id="fullName" path="fullName"
                      placeholder="Enter full name" />
                  </div>

                  <div class="mb-3">
                    <label for="address" class="form-label">Address:</label>
                    <form:input type="text" class="form-control" id="address" path="address"
                      placeholder="Enter address" />
                  </div>

                  <button type="submit" class="btn btn-primary w-100 mt-3">Save Changes</button>
                </form:form>
              </div>
            </div>
          </div>


          <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

          <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>

        </body>

        </html>