package com.example.exam.security.auth;

import com.example.exam.repository.RoleRepository;
import com.example.exam.repository.UserRepository;
import com.example.exam.security.JwtService;
import com.example.exam.service.enums.TokenType;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AuthService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder, AuthenticationManager authenticationManager, JwtService jwtService) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
    }


    public AuthResponse login(LoginRequest req) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(req.getEmail(), req.getPassword())
            );
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }
        var user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));

        List<String> roles = user.getRoles().stream().map(r -> r.getName()).toList();
//        List<String> permissionList = user.getRoles().stream()
//                .flatMap(role -> role.getPermissions().stream()
//                        .map(permission -> permission.getName()))
//                .collect(Collectors.toList());

        String access = jwtService.generateToken(user.getEmail(), roles, TokenType.ACCESS);
        String refresh = jwtService.generateToken(user.getEmail(), List.of(), TokenType.REFRESH);
        return new AuthResponse(access, refresh, "Bearer", roles);
    }
}
