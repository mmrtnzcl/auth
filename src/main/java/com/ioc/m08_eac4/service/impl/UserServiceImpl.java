package com.ioc.m08_eac4.service.impl;

import com.ioc.m08_eac4.auth.request.LoginRequest;
import com.ioc.m08_eac4.auth.request.RegisterRequest;
import com.ioc.m08_eac4.auth.response.AuthResponse;
import com.ioc.m08_eac4.domain.Role;
import com.ioc.m08_eac4.domain.User;
import com.ioc.m08_eac4.repository.UserRepository;
import com.ioc.m08_eac4.security.jwt.JwtService;
import com.ioc.m08_eac4.service.UserService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    @Override
    public AuthResponse createUser(RegisterRequest request) {

        User user = User.builder()
                .username(request.getUserName())
                .name(request.getName())
                .password(passwordEncoder.encode(request.getPassword()))
                .last_name(request.getLastName())
                .email(request.getEmail())
                .role(Role.USER)
                .status("active")
                .build();

        user.onCreate();
        user.onUpdate();

        userRepository.save(user);

        return AuthResponse.builder()
                .token(jwtService.getToken(user))
                .build();
    }

    @Override
    public AuthResponse login(LoginRequest request) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        UserDetails user = userRepository.findByUsername(request.getUserName()).orElseThrow();
        String token = jwtService.getToken(user);
        return AuthResponse.builder()
                .token(token)
                .build();
    }
}
