package vn.ecornomere.ecornomereAZ.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import vn.ecornomere.ecornomereAZ.config.CustomOAuth2.OAuth2LoginFailureHandler;
import vn.ecornomere.ecornomereAZ.config.CustomOAuth2.OAuth2LoginSuccessHandler;
import vn.ecornomere.ecornomereAZ.service.CustomUserDetailsService;
import vn.ecornomere.ecornomereAZ.service.UserService;
import vn.ecornomere.ecornomereAZ.service.OAuth2.CustomOAuth2UserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)

public class SecurityConfig {
        @Autowired
        private CustomOAuth2UserService customOAuth2UserService;

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
                http

                                .authorizeHttpRequests(auth -> auth
                                                .dispatcherTypeMatchers(DispatcherType.FORWARD,
                                                                DispatcherType.INCLUDE)
                                                .permitAll()
                                                .requestMatchers("/", "/login", "/client/**", "/css/**", "/js/**",
                                                                "/product/**",
                                                                "/uploads/avatars/**",
                                                                "/forgot-password",
                                                                "/uploads/products/**",
                                                                "/oauth2/**")
                                                .permitAll()
                                                .requestMatchers("/admin/**").hasRole("ADMIN")
                                                .anyRequest().authenticated())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(
                                                                org.springframework.security.config.http.SessionCreationPolicy.ALWAYS)
                                                .invalidSessionUrl("/logout?expired")
                                                .maximumSessions(1)
                                                .maxSessionsPreventsLogin(false))

                                .logout(logout -> logout.deleteCookies("JSESSIONID").invalidateHttpSession(true))

                                .rememberMe(rememberMe -> rememberMe
                                                .rememberMeServices(rememberMeServices()))
                                .formLogin(form -> form
                                                .loginPage("/login")
                                                .failureUrl("/login?error")
                                                .successHandler(authenticationSuccessHandler())
                                                .permitAll())
                                // Thêm cấu hình OAuth2 Login
                                .oauth2Login(oauth2 -> oauth2
                                                .loginPage("/login")
                                                .userInfoEndpoint(userInfo -> userInfo
                                                                .userService(customOAuth2UserService))
                                                .successHandler(oAuth2AuthenticationSuccessHandler())
                                                .failureHandler(oAuth2AuthenticationFailureHandler()))
                                // cai nay cau hinh ngan chan tan cong web gia mao nhung cai nhu post,put,delete
                                // neu muon bat xoa cai nay di la xong .csrf(csrf -> csrf.disable()) va
                                // phai co(thường là _csrf hidden field)
                                .csrf(csrf -> csrf.disable())
                                .exceptionHandling(exception -> exception
                                                .accessDeniedPage("/denyaccess"))

                ;
                return http.build();
        }

        @Bean
        public AuthenticationSuccessHandler authenticationSuccessHandler() {
                return new CustomSuccessHandler();
        }

        @Bean
        public AuthenticationSuccessHandler oAuth2AuthenticationSuccessHandler() {
                return new OAuth2LoginSuccessHandler();
        }

        @Bean
        public AuthenticationFailureHandler oAuth2AuthenticationFailureHandler() {
                return new OAuth2LoginFailureHandler();
        }

        @Bean
        public UserDetailsService userDetailsService(UserService userService) {
                return new CustomUserDetailsService(userService);
        }

        @Bean
        public DaoAuthenticationProvider authManager(PasswordEncoder passwordEncoder,
                        UserDetailsService userDetailsService) throws Exception {
                DaoAuthenticationProvider authBuilder = new DaoAuthenticationProvider();
                authBuilder.setPasswordEncoder(passwordEncoder);
                authBuilder.setUserDetailsService(userDetailsService);
                authBuilder.setHideUserNotFoundExceptions(false);
                return authBuilder;
        }

        @Bean
        public SpringSessionRememberMeServices rememberMeServices() {
                SpringSessionRememberMeServices rememberMeServices = new SpringSessionRememberMeServices();
                rememberMeServices.setAlwaysRemember(true);
                return rememberMeServices;
        }

}
