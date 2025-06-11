package vn.ecornomere.ecornomereAZ.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.authentication.dao.DaoAuthenticationProvider;

import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;

import vn.ecornomere.ecornomereAZ.service.CustomUserDetailsService;
import vn.ecornomere.ecornomereAZ.service.UserService;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.session.security.web.authentication.SpringSessionRememberMeServices;

import jakarta.servlet.DispatcherType;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)

public class SecurityConfig {

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
                                                                "/uploads/products/**")
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
        public PasswordEncoder passwordEncoder() {
                return new BCryptPasswordEncoder();
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
