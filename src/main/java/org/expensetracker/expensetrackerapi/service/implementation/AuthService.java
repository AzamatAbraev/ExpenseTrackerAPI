package org.expensetracker.expensetrackerapi.service.implementation;

import lombok.extern.slf4j.Slf4j;
import org.expensetracker.expensetrackerapi.exception.CustomBadRequestException;
import org.expensetracker.expensetrackerapi.model.auth.AuthResponse;
import org.expensetracker.expensetrackerapi.model.auth.LoginRequest;
import org.expensetracker.expensetrackerapi.model.auth.RegisterRequest;
import org.expensetracker.expensetrackerapi.model.user.Role;
import org.expensetracker.expensetrackerapi.model.user.User;
import org.expensetracker.expensetrackerapi.repository.UserRepository;
import org.expensetracker.expensetrackerapi.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AuthService(UserRepository userRepository, JwtUtil jwtUtil, AuthenticationManager authenticationManager, UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponse register(RegisterRequest request) {
        if (request.getEmail() == null) {
            throw new CustomBadRequestException("Email is required");
        }

        if (request.getPassword() == null) {
            throw new CustomBadRequestException("Password is required");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setName(request.getName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole(Role.USER);

        userRepository.save(user);

        String token = jwtUtil.generateToken(user);

        return new AuthResponse(token);
    }

    public AuthResponse login(LoginRequest request) {
        if (request.getEmail() == null) {
            throw new CustomBadRequestException("Email is required");
        }

        if (request.getPassword() == null) {
            throw new CustomBadRequestException("Password is required");
        }

        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword()));
        UserDetails user =userDetailsService.loadUserByUsername(request.getEmail());
        String token = jwtUtil.generateToken(user);
        return new AuthResponse(token);
    }
}
