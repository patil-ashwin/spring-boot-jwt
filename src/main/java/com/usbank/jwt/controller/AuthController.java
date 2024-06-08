package com.usbank.jwt.controller;

import com.usbank.jwt.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Slf4j
public class AuthController {

    private final String HARDCODED_USERNAME = "user";
    private final String HARDCODED_PASSWORD = "password";

    @Autowired
    private JwtUtil jwtUtil;

    @PostMapping("/generateToken")
    public ResponseEntity<?> createAuthenticationToken(@RequestHeader("username") String username,
                                                       @RequestHeader("password") String password) {
        if (HARDCODED_USERNAME.equals(username) && HARDCODED_PASSWORD.equals(password)) {
            final String token = jwtUtil.generateToken(username);
            return ResponseEntity.ok(new AuthenticationResponse(token));
        } else {
            return ResponseEntity.status(401).body("Invalid credentials");
        }
    }


    @GetMapping("/validateToken")
    public ResponseEntity<?> validateAuthenticationToken(@RequestHeader("Authorization") String token) {
        if (token.startsWith("Bearer ")) {
            String jwt = token.substring(7);
            String username = jwtUtil.extractUsername(jwt);
            if (jwtUtil.validateToken(jwt, username)) {
                return ResponseEntity.ok("Token is valid");
            } else {
                return ResponseEntity.status(401).body("Invalid token");
            }
        } else {
            return ResponseEntity.status(400).body("Authorization header must start with 'Bearer '");
        }
    }

    class AuthenticationResponse {
        private final String jwt;

        public AuthenticationResponse(String jwt) {
            this.jwt = jwt;
        }

        public String getJwt() {
            return jwt;
        }
    }
}
