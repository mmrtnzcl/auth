package com.ioc.m08_eac4.controller;

import com.ioc.m08_eac4.auth.request.RegisterRequest;
import com.ioc.m08_eac4.domain.User;
import com.ioc.m08_eac4.repository.UserRepository;
import com.ioc.m08_eac4.security.jwt.JwtService;
import com.ioc.m08_eac4.service.UserService;
import com.ioc.m08_eac4.auth.request.LoginRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/auth/")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @PostMapping("register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request)
    {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request)
    {
        Optional<User> userOptional = userRepository.findByUsername(request.getUserName());


        String passwordRequest = request.getPassword();
        String userPassword = userOptional.get().getPassword();
        if(!passwordEncoder.matches(passwordRequest,userPassword))
            return ResponseEntity.badRequest().body("Bad password");


        return ResponseEntity.ok(userService.login(request));
    }
}
