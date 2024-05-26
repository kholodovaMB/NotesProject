package com.HMB.NotesProject.security;

import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

//@Slf4j
//@Controller
//public class LoginController {
//
//    private final UserService userService;
//    @Autowired
//    private final PasswordEncoder passwordEncoder;
//
//    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
//        this.userService = userService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//    @GetMapping("/login")
//    public String showLoginForm(Model model) {
//        model.addAttribute("user", new User());
//        log.info("Login form displayed");
//        return "login";
//    }
////    @PostMapping("/login")
////    public String processLogin(@ModelAttribute("user") User user, BindingResult result, HttpServletRequest request) {
////        log.info("Attempting to login user: {}", user.getUsername());
////        if (result.hasErrors()) {
////            return "login";
////        }
////
////        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
////        try {
////            CustomAuthenticationProvider authenticationManager = new CustomAuthenticationProvider();
////            authentication = authenticationManager.authenticate(authentication);
////            SecurityContextHolder.getContext().setAuthentication(authentication);
////            return "redirect:/api/notes";
////        } catch (AuthenticationException e) {
////            result.rejectValue("password", "error.user", "Invalid username or password");
////            return "login";
////        }
////    }
//
//
////    @PostMapping("/login")
////    public String loginUser(@ModelAttribute("user") User user) {
////        log.info("Attempting to login user: {}", user.getUsername());
////        return "redirect:/notes";
////    }
//
//    @GetMapping("/logout")
//    public String logoutUser(HttpSession session) {
//        session.invalidate();
////        log.info("User logged out");
//        return "redirect:/login";
//    }
//}




