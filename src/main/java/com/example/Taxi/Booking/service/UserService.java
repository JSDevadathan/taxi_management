package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.LoginRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import com.example.Taxi.Booking.contract.response.LoginResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.expection.EmailNotFoundException;
import com.example.Taxi.Booking.expection.EntityNotFoundException;
import com.example.Taxi.Booking.expection.InvalidUserException;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.UserRepository;
import com.example.Taxi.Booking.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    public SignUpResponse signup(SignupRequest signupRequest) {
        if(userRepository.existsByEmail(signupRequest.getEmail())) {
    throw new EmailNotFoundException("Login");
        }
        User user =
                User.builder()
                        .email(signupRequest.getEmail())
                        .password(passwordEncoder.encode(signupRequest.getPassword()))
                        .name(signupRequest.getName())
                        .build();
        user = userRepository.save(user);
        return modelMapper.map(user, SignUpResponse.class);
    }

    public LoginResponse userLogin(LoginRequest request) {
        User user =
                userRepository
                        .findByEmail(request.getEmail()).orElseThrow(() -> new InvalidUserException("Login"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidUserException("Login");
        }
        String jwtToken = jwtService.generateToken(user);
        return LoginResponse.builder().token(jwtToken).build();
    }

    public AccountBalanceResponse accountBalance(
            Long userId, AccountBalanceRequest accountBalanceRequest) {
        User user =
                userRepository
                        .findById(userId)
                        .orElseThrow(() -> new EntityNotFoundException("User", userId));
        user =
                User.builder()
                        .userId(user.getUserId())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .accountBalance(accountBalanceRequest.getAccountBalance() + user.getAccountBalance())
                        .build();
        User updatedUser = userRepository.save(user);
        return modelMapper.map(updatedUser, AccountBalanceResponse.class);
    }
}
