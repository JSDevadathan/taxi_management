package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.LoginRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.LoginResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/users")
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public SignUpResponse signup(@RequestBody SignupRequest signupRequest) {
        return userService.signup(signupRequest);
    }

    @PostMapping("/login")
    public LoginResponse authenticate(@RequestBody LoginRequest loginRequest) {
        return userService.authenticate(loginRequest);
    }
}
