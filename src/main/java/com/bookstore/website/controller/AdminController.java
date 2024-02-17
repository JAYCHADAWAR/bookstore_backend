package com.bookstore.website.controller;
import com.bookstore.website.entity.Admin;
import com.bookstore.website.exception.UserNotFoundException;
import com.bookstore.website.model.AdminSignInRequest;
import com.bookstore.website.service.AdminService;
import com.bookstore.website.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class AdminController {

    @Autowired
    private AdminService adminService;
    @Autowired
    private JwtUtil jwtUtil;
    @PostMapping("/admin_signin")
    public ResponseEntity<?> adminSignIn(@RequestBody AdminSignInRequest adminsignInRequest) {
        try {
            Admin admin = adminService.signIn(adminsignInRequest.getEmail(), adminsignInRequest.getPassword());
            if (admin != null) {
                // Admin found, generate token
                String token = jwtUtil.generateToken(admin.getId().toString());
                return ResponseEntity.status(HttpStatus.CREATED).body(Map.of("message", "Admin signed in successfully", "token", token));
            } else {
                // Admin not found
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("message", "Invalid credentials of admin"));
            }
        }catch (UserNotFoundException e)
        {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Internal server error");
        }
    }


}
