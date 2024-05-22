package com.HMB.NotesProject.security;

import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;
    @Autowired
    private final PasswordEncoder passwordEncoder;

    public LoginController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        log.info("Login form displayed");
        return mav;
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user) {
        log.info("Attempting to login user: {}", user.getUsername());
        return "redirect:/notes";
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
//        log.info("User logged out");
        return "redirect:/login";
    }
}




