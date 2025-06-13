package vn.ecornomere.ecornomereAZ.service.OAuth2;

import java.util.Collection;
import java.util.Collections;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import vn.ecornomere.ecornomereAZ.model.User;
import vn.ecornomere.ecornomereAZ.service.UserService;

@Service
public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {

    @Autowired
    private UserService userService;

    private OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();

    @Override
    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = delegate.loadUser(userRequest);

        String registrationId = userRequest.getClientRegistration().getRegistrationId();

        if ("google".equals(registrationId)) {
            return processGoogleUser(oAuth2User);
        }

        return oAuth2User;
    }

    private OAuth2User processGoogleUser(OAuth2User oAuth2User) {
        String email = oAuth2User.getAttribute("email");

        // Tìm user trong database
        User user = userService.getbyEmail(email);

        if (user != null) {
            // Tạo authorities từ role của user trong database
            Collection<GrantedAuthority> authorities = Collections.singletonList(
                    new SimpleGrantedAuthority("ROLE_" + user.getRole().getName().trim()));

            // Trả về OAuth2User với authorities từ database
            return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
        }

        // Nếu user chưa tồn tại, trả về với role USER mặc định
        Collection<GrantedAuthority> authorities = Collections.singletonList(
                new SimpleGrantedAuthority("ROLE_USER"));

        return new DefaultOAuth2User(authorities, oAuth2User.getAttributes(), "email");
    }
}
