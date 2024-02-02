package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.LoginRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import com.example.Taxi.Booking.contract.response.LoginResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.expection.EntityNotFoundException;
import com.example.Taxi.Booking.expection.InvalidUserException;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.UserRepository;
import com.example.Taxi.Booking.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public SignUpResponse signup(SignupRequest signupRequest) {
        User user = User.builder()
                .email(signupRequest.getEmail())
                .password(passwordEncoder.encode(signupRequest.getPassword()))
                .name(signupRequest.getName())
                .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignUpResponse.class);
    }

    public LoginResponse userLogin(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail());
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidUserException("Login");
        }
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder()
                .token(jwtToken)
                .build();
    }

    public AccountBalanceResponse accountBalance(Long userId, AccountBalanceRequest accountBalanceRequest) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        user = User.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(accountBalanceRequest.getAccountBalance())
                .build();
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, AccountBalanceResponse.class);

    }
}
