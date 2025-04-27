<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
    <% response.setCharacterEncoding("UTF-8"); %>


        <!DOCTYPE html>
        <html lang="vi">

        <head>
            <meta charset="UTF-8">
            <meta name="viewport" content="width=device-width, initial-scale=1.0">
            <title>Facebook - Đăng nhập hoặc Đăng ký</title>

            <!-- Thêm Google Font hỗ trợ tiếng Việt -->
            <link href="https://fonts.googleapis.com/css2?family=Noto+Sans&display=swap" rel="stylesheet">

            <style>
                body {
                    margin: 0;
                    font-family: 'Noto Sans', 'Segoe UI', 'Roboto', 'Arial', sans-serif;
                    background-color: #f0f2f5;
                    display: flex;
                    justify-content: center;
                    align-items: center;
                    min-height: 100vh;
                    flex-direction: column;
                }

                .container {
                    display: flex;
                    align-items: center;
                    justify-content: center;
                    width: 80%;
                    max-width: 1000px;
                    margin: 50px auto;
                    flex-wrap: wrap;
                }

                .left-section {
                    flex: 1;
                    margin-right: 50px;
                    max-width: 500px;
                }

                .facebook-logo {
                    width: 300px;
                    margin-bottom: 20px;
                }

                .left-section h2 {
                    font-size: 24px;
                    font-weight: normal;
                    color: #1c1e21;
                }

                .right-section {
                    flex: 1;
                    max-width: 400px;
                }

                .login-form {
                    background-color: #fff;
                    padding: 20px;
                    border-radius: 8px;
                    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1), 0 8px 16px rgba(0, 0, 0, 0.1);
                    display: flex;
                    flex-direction: column;
                    align-items: center;
                }

                .login-form input {
                    width: calc(100% - 20px);
                    padding: 12px 10px;
                    margin-bottom: 15px;
                    border: 1px solid #dddfe2;
                    border-radius: 6px;
                    font-size: 17px;
                }

                .login-button {
                    width: 100%;
                    padding: 12px 0;
                    background-color: #1877f2;
                    color: #fff;
                    border: none;
                    border-radius: 6px;
                    /* Đã sửa từ 6-px thành 6px */
                    font-size: 18px;
                    font-weight: bold;
                    cursor: pointer;
                    transition: background-color 0.2s ease;
                }

                .login-button:hover {
                    background-color: #166fe5;
                }

                .forgot-password {
                    display: block;
                    text-align: center;
                    margin-top: 15px;
                    color: #1877f2;
                    text-decoration: none;
                    font-size: 14px;
                }

                .forgot-password:hover {
                    text-decoration: underline;
                }

                .divider {
                    height: 1px;
                    background-color: #dadde1;
                    margin: 20px 0;
                    width: 100%;
                }

                .create-account-button {
                    width: 70%;
                    padding: 12px 0;
                    background-color: #42b72a;
                    color: #fff;
                    border: none;
                    border-radius: 6px;
                    font-size: 17px;
                    font-weight: bold;
                    cursor: pointer;
                    transition: background-color 0.2s ease;
                }

                .create-account-button:hover {
                    background-color: #36a420;
                }

                .create-page-link {
                    text-align: center;
                    margin-top: 20px;
                    font-size: 14px;
                }

                .create-page-link a {
                    color: #1c1e21;
                    font-weight: bold;
                    text-decoration: none;
                }

                .create-page-link a:hover {
                    text-decoration: underline;
                }

                footer {
                    margin-top: 50px;
                    width: 100%;
                    text-align: center;
                    color: #737373;
                    font-size: 12px;
                }

                .languages {
                    margin-bottom: 10px;
                }

                /* Responsive adjustments */
                @media (max-width: 850px) {
                    .container {
                        flex-direction: column;
                    }

                    .left-section {
                        margin-right: 0;
                        margin-bottom: 40px;
                        text-align: center;
                    }

                    .facebook-logo {
                        margin: 0 auto 20px auto;
                    }

                    .left-section h2 {
                        padding: 0 20px;
                    }

                    .right-section {
                        width: 90%;
                        max-width: 400px;
                    }

                    .login-form {
                        padding: 15px;
                    }
                }
            </style>
        </head>

        <body>
            <div class="container">
                <div class="left-section">
                    <img src="https://static.xx.fbcdn.net/rsrc.php/y1/r/4lCu2zih0ca.svg" alt="Facebook">

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