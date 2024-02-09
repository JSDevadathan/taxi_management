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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
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

        SignUpResponse buildResult = SignUpResponse.builder().name(null).userId(1L).build();

        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any()))
                .thenReturn(buildResult);
        when(passwordEncoder.encode(Mockito.<CharSequence>any())).thenReturn(null);

        userService.signup(new SignupRequest(null, null, null));

        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<SignUpResponse>>any());
        verify(userRepository).save(Mockito.<User>any());
        verify(passwordEncoder).encode(Mockito.<CharSequence>any());
    }

    @Test
    void testUserLogin() {
        when(userRepository.findByEmail(Mockito.<String>any())).thenReturn(Optional.of(new User()));
        when(passwordEncoder.matches(Mockito.<CharSequence>any(), Mockito.<String>any()))
                .thenReturn(false);
        assertThrows(
                InvalidUserException.class,
                () -> userService.userLogin(new LoginRequest(null, null)));
        verify(userRepository).findByEmail(Mockito.<String>any());
        verify(passwordEncoder).matches(Mockito.<CharSequence>any(), Mockito.<String>any());
    }

    @Test
    public void testSignupEmailNotFoundException() {
        SignupRequest signupRequest = SignupRequest.builder()
                .email("js@gmail.com")
                .build();

        when(userRepository.existsByEmail(signupRequest.getEmail())).thenReturn(true);
        assertThrows(EmailNotFoundException.class, () -> userService.signup(signupRequest));
    }


    @Test
    void testAccountBalance() {
        when(userRepository.save(Mockito.<User>any())).thenReturn(new User());
        User buildResult = User.builder()
                .accountBalance(10.0d)
                .email("js@gmail.com")
                .name("Name")
                .password("hy")
                .userId(1L)
                .build();
        Optional<User> ofResult = Optional.of(buildResult);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(modelMapper.map(Mockito.<Object>any(), Mockito.<Class<AccountBalanceResponse>>any()))
                .thenThrow(new InvalidUserException("Entity"));
        AccountBalanceRequest accountBalanceRequest = AccountBalanceRequest.builder().accountBalance(10.0d).build();
        assertThrows(InvalidUserException.class, () -> userService.accountBalance(1L, accountBalanceRequest));
        verify(modelMapper).map(Mockito.<Object>any(), Mockito.<Class<AccountBalanceResponse>>any());
        verify(userRepository).findById(Mockito.<Long>any());
        verify(userRepository).save(Mockito.<User>any());
    }
    @Test
    public void testAccountBalanceNotFound() {
        Long id = 1L;

        when(userRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.accountBalance(id, null));
    }
}
