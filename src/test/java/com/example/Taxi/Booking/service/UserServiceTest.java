package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.expection.EntityNotFoundException;
import com.example.Taxi.Booking.expection.InvalidUserException;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.UserRepository;
import com.example.Taxi.Booking.security.JwtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @InjectMocks
    JwtService jwtService;

    @Mock
    UserRepository userRepository;
    @Mock
    PasswordEncoder passwordEncoder;

    @Mock
    ModelMapper modelMapper;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testSignup() {
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());

        SignUpResponse buildResult = SignUpResponse.builder().name("Name").userId(1L).build();

        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any())).thenReturn(buildResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn("secret");

        userService.signup(new SignupRequest("Name", "jane.doe@example.org", "iloveyou"));

        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    @Test
    void testAccountBalance() {
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());

        Optional<User> ofResult = Optional.of(new User());
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);

        AccountBalanceResponse buildResult = AccountBalanceResponse.builder().accountBalance(10.0d).name("Name").build();

        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<AccountBalanceResponse>>any())).thenReturn(buildResult);
        userService.accountBalance(1L, new AccountBalanceRequest());

        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<AccountBalanceResponse>>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
    }

}
