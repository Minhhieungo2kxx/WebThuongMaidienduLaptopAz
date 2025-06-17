package vn.ecornomere.ecornomereAZ.config;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import vn.ecornomere.ecornomereAZ.model.Cart;
import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.UserService;

public class CustomSuccessHandler implements AuthenticationSuccessHandler {
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
            Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {

            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
        clearAuthenticationAttributes(request, authentication);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        Map<String, String> roleTargetUrlMap = new HashMap<>();
        roleTargetUrlMap.put("ROLE_USER", "/");
        roleTargetUrlMap.put("ROLE_ADMIN", "/admin");

        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        for (final GrantedAuthority grantedAuthority : authorities) {
            String authorityName = grantedAuthority.getAuthority().trim();

            if (roleTargetUrlMap.containsKey(authorityName)) {
                return roleTargetUrlMap.get(authorityName);
            }
        }

        return "/";
    }

    @SuppressWarnings("null")
    protected void clearAuthenticationAttributes(HttpServletRequest request, Authentication authentication) {
        if (request == null || authentication == null) {
            return;
        }

        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }

        String email = authentication.getName();
        if (email == null || email.trim().isEmpty()) {
            return;
        }

        User user = userService.getbyEmail(email);
        if (user != null) {
            String fullName = user.getEmail();
            if (fullName != null && !fullName.trim().isEmpty()) {
                session.setAttribute("email", fullName.trim());
            }

            String avatar = user.getAvatar();
            if (avatar != null && !avatar.trim().isEmpty()) {
                session.setAttribute("avatar", avatar.trim());
            }
        }
        Cart cart = user.getCart();
        int sumcart = 0; // mặc định là 0
        if (cart != null) {
            sumcart = cart.getSum(); // chỉ gọi getSum() nếu cart không null
        }
        session.setAttribute("sum", sumcart);

        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

}
