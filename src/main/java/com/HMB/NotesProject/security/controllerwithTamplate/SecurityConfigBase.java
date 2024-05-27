package com.HMB.NotesProject.security.controllerwithTamplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
//
//@Slf4j
//@Configuration
//@EnableWebSecurity
//public class SecurityConfigBase {
//
//    @Autowired
//    private CustomAuthenticationSuccessHandler customAuthenticationSuccessHandler;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http, CustomAuthenticationProvider customAuthenticationProvider) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable)
//                .csrf(csrf -> csrf.ignoringRequestMatchers("/api/**"))
//                .authenticationProvider(customAuthenticationProvider)
//                .authorizeRequests(authorize -> authorize
//                        .requestMatchers("/register", "/api/login", "/api/register", "/api/notes/**", "/login", "/css/**", "/js/**").permitAll()
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .loginPage("/login")
//                        .successHandler(customAuthenticationSuccessHandler)
//                        .failureHandler(authenticationFailureHandler())
//                        .permitAll()
//                )
//                .logout(logout -> logout
//                        .permitAll()
//                );
//
//        return http.build();
//    }
//
//    @Bean
//    public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
//        return authConfig.getAuthenticationManager();
//    }
//    @Bean
//    public AuthenticationFailureHandler authenticationFailureHandler() {
//        return (request, response, exception) -> {
//            log.info("Login failed: " + exception.getMessage());
//            response.sendRedirect("/login?error");
//        };
//    }
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//}
