package com.HMB.NotesProject.security;
import com.HMB.NotesProject.users.User;
import com.HMB.NotesProject.users.UserDTO;
import com.HMB.NotesProject.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.Optional;

@Slf4j
@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public ModelAndView showRegistrationForm() {
        ModelAndView mav = new ModelAndView("register");
        mav.addObject("user", new UserDTO());
        return mav;
    }

    @PostMapping("/register")
    public ModelAndView registerUser(@ModelAttribute("user") User user) {
        log.info(user.getUsername());
        ModelAndView mav = new ModelAndView();
        Optional<User> exUser = userService.findByUsername(user.getUsername());
        if (exUser.isPresent()) {
            mav.setViewName("register");
            mav.addObject("error", "Username already exists!");
            return mav;
        }

        userService.saveUser(user);
        mav.setViewName("redirect:/login");
        return mav;
    }
}






