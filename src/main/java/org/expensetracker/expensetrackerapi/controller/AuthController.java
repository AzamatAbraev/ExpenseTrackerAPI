package org.expensetracker.expensetrackerapi.controller;

import org.expensetracker.expensetrackerapi.model.api.ApiResponse;
import org.expensetracker.expensetrackerapi.model.auth.AuthResponse;
import org.expensetracker.expensetrackerapi.model.auth.LoginRequest;
import org.expensetracker.expensetrackerapi.model.auth.RegisterRequest;
import org.expensetracker.expensetrackerapi.service.implementation.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/auth")
@CrossOrigin(origins = "*")
public class AuthController {
    private final AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(@RequestBody RegisterRequest request) {
        AuthResponse response = authService.register(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponse("User registered successfully", response));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse> login(@RequestBody LoginRequest request) {
        AuthResponse response = authService.login(request);
        return ResponseEntity.status(HttpStatus.OK).body(new ApiResponse("User logged in successfull", response));
    }
}
