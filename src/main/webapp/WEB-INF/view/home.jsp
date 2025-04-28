<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% response.setCharacterEncoding("UTF-8"); %>
        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Facebook - Đăng nhập hoặc Đăng ký</title>

            <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

            <link rel="stylesheet" href="/css/home.css">

        </head>

        <body>
            <div class="container">
                <div class="left-section">
                    <%-- Lưu ý: Đặt ảnh trong thư mục static/images và tham chiếu tới nó --%>
                        <img src="https://www.wavetransit.com/wp-content/uploads/2021/08/Facebook-logo.png"
                            alt="Facebook" width="240" height="auto"> <%-- Đã sửa width="240px" thành width="240" để
                            chuẩn HTML hơn --%>

                            <h2>Facebook giúp bạn kết nối và chia sẻ với mọi người trong cuộc sống của bạn.</h2>
                </div>
                <div class="right-section">
                    <div class="login-form">
                        <input type="text" placeholder="Email hoặc số điện thoại">
                        <input type="password" placeholder="Mật khẩu">
                        <button class="login-button">Đăng nhập</button>
                        <a href="#" class="forgot-password">Quên mật khẩu?</a>
                        <div class="divider"></div>
                        <button class="create-account-button">Tạo tài khoản mới</button>
                    </div>
                    <p class="create-page-link"><a href="#">Tạo Trang</a> dành cho người nổi tiếng, thương hiệu hoặc
                        doanh nghiệp.</p>
                </div>
            </div>

            <footer>
                <div class="languages">
                    Tiếng Việt &nbsp; English (US) &nbsp; ...
                </div>
            </footer>
        </body>

        </html>