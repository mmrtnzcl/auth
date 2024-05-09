package com.ioc.m08_eac4.service;


import com.ioc.m08_eac4.auth.request.LoginRequest;
import com.ioc.m08_eac4.auth.request.RegisterRequest;
import com.ioc.m08_eac4.auth.response.AuthResponse;
import com.ioc.m08_eac4.domain.User;
import org.springframework.http.ResponseEntity;

public interface UserService {
    AuthResponse createUser(RegisterRequest request);

    AuthResponse login(LoginRequest request);

}