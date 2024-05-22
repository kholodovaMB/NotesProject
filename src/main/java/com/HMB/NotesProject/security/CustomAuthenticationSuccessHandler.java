package com.HMB.NotesProject.security;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
@Slf4j
@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
        Integer userId = userDetails.getId();
        log.info("User ID after authentication: " + userId);
        HttpSession session = request.getSession();
        session.setAttribute("userId", userId);

        response.sendRedirect("/notes"); // Редірект після успішного логіну
    }

}

