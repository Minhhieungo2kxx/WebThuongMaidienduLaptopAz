package vn.ecornomere.ecornomereAZ.config.CustomOAuth2;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.RoleService;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Component
public class OAuth2LoginSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UserService userService;
    @Autowired
    RoleService roleService;

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @SuppressWarnings("null")
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {

        OAuth2AuthenticationToken oAuth2AuthenticationToken = (OAuth2AuthenticationToken) authentication;

        if ("google".equals(oAuth2AuthenticationToken.getAuthorizedClientRegistrationId())) {
            OAuth2User oAuth2User = oAuth2AuthenticationToken.getPrincipal();

            String email = oAuth2User.getAttribute("email");
            String name = oAuth2User.getAttribute("name");
            String picture = oAuth2User.getAttribute("picture");
            // Set thông tin vào session
            HttpSession session = request.getSession();

            // Kiểm tra user đã tồn tại chưa
            User existingUser = userService.getbyEmail(email);

            if (existingUser == null) {
                User newuser = userService.createOAuth2User(email, name, picture);
                session.setAttribute("avatar", newuser.getAvatar().trim());
            } else {
                // Cập nhật thông tin nếu cần
                User updateuser = userService.updateOAuth2User(existingUser, name, picture);
                session.setAttribute("avatar", updateuser.getAvatar().trim());
            }
            session.setAttribute("fullName", name);
            session.setAttribute("email", email);

            Cart cart = existingUser.getCart();
            int sumcart = 0; // mặc định là 0
            if (cart != null) {
                sumcart = cart.getSum(); // chỉ gọi getSum() nếu cart không null
            }
            session.setAttribute("sum", sumcart);

        }

        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {
        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority();
            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        return "/"; // Default redirect
    }
}
