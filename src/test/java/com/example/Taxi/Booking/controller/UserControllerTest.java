package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;

import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

//    @SneakyThrows
//    @Test
//    void testAccountBalance() {
//        Long id = 1L;
//        AccountBalanceRequest accountBalanceRequest = new AccountBalanceRequest(null);
//        AccountBalanceResponse accountBalanceResponse = new AccountBalanceResponse(null, null);
//        when(userService.accountBalance(id, accountBalanceRequest)).thenReturn(accountBalanceResponse);
//
//        mockMvc.perform(
//                        put("/v1/users" +id)
//                                .contentType(MediaType.APPLICATION_JSON)
//                                .content(new ObjectMapper().writeValueAsString(accountBalanceRequest)))
//                .andExpect(status().isOk())
//                .andExpect(content().json(new ObjectMapper().writeValueAsString(accountBalanceResponse)));
//
//        verify(userService, times(1)).accountBalance(eq(id), any(AccountBalanceRequest.class));
//    }

    @Test
    void testSignUp() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        SignUpResponse signUpResponse = new SignUpResponse();
        when(userService.signup(signupRequest))
                .thenReturn(signUpResponse);
    }
}
