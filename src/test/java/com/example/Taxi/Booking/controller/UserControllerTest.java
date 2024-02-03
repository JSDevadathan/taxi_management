package com.example.Taxi.Booking.controller;

import static org.mockito.Mockito.when;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import com.example.Taxi.Booking.contract.response.SignUpResponse;
import com.example.Taxi.Booking.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;


    @Test
    void testSignUp() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        SignUpResponse signUpResponse = new SignUpResponse();
        when(userService.signup(signupRequest))
                .thenReturn(signUpResponse);
    }

    @Test
    void testAccountBalance() throws Exception {
        AccountBalanceResponse buildResult = AccountBalanceResponse.builder().accountBalance(10.0d).name("Name").build();
        when(userService.accountBalance(Mockito.<Long>any(), Mockito.<AccountBalanceRequest>any())).thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult = MockMvcRequestBuilders.put("/v1/users/{userId}", 1L)
                .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder = contentTypeResult
                .content(objectMapper.writeValueAsString(new AccountBalanceRequest()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(MockMvcResultMatchers.content().string("{\"accountBalance\":10.0,\"name\":\"Name\"}"));
    }
}
