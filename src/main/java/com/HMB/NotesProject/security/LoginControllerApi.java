package com.HMB.NotesProject.security;

import com.HMB.NotesProject.users.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/api")
public class LoginControllerApi {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;

    public LoginControllerApi(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserRequest userRequest) {
        Optional<User> existingUser = userService.findByUsername(userRequest.getUsername());
        if (existingUser.isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("User already exists");
        }
        userService.saveUser(userRequest.toEntityUser());
        return ResponseEntity.status(HttpStatus.CREATED).body("Registered successfully");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserRequest userRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(userRequest.getUsername(), userRequest.getPassword())
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            MyUserDetails userDetails = (MyUserDetails) authentication.getPrincipal();
            Integer userId = userDetails.getId();
            log.info("User '{}' logged in successfully with ID: {}", userDetails.getUsername(), userId);

            return ResponseEntity.ok(Collections.singletonMap("userId", userId));
        } catch (BadCredentialsException ex) {
            log.warn("Login failed for username: {}", userRequest.getUsername());
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Collections.singletonMap("error", "Invalid username or password"));

        }
    }

    @RequestMapping(value="/logout", method = RequestMethod.GET)
    public ResponseEntity<?>  logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        log.info("User '{}' logged in successfully with ID: {}" + auth.isAuthenticated());
        if (auth != null) {
            SecurityContextHolder.clearContext();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not loginned");
    }

}
