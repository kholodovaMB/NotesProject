package com.HMB.NotesProject.security;

import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

import static java.rmi.server.LogStream.log;

@Slf4j
@Controller
public class LoginController {

    private final UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public ModelAndView showLoginForm() {
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("user", new User());
        LoginController.log.info("start");
        return mav;
    }

    @PostMapping("/login")
    public ModelAndView loginUser(@ModelAttribute("user") User user, HttpSession session) {
        log.info(user.getUsername());
        Optional<User> exUser = userService.findByUsername(user.getUsername());
        if (exUser.isPresent()) {
            User existingUser = exUser.get();
            if (existingUser != null && existingUser.getPassword().equals(user.getPassword())) {
                session.setAttribute("userId", existingUser.getId());
                return new ModelAndView("redirect:/notes");
            }
        }
        ModelAndView mav = new ModelAndView("login");
        mav.addObject("error", "Invalid username or password");
        return mav;
    }

    @GetMapping("/logout")
    public String logoutUser(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}



