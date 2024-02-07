package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.AccountBalanceRequest;
import com.example.Taxi.Booking.contract.request.LoginRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.AccountBalanceResponse;
import com.example.Taxi.Booking.contract.response.LoginResponse;
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
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    private UserController userController;

    @MockBean
    private UserService userService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testSignUp() throws Exception {
        SignupRequest signupRequest = new SignupRequest();
        SignUpResponse signUpResponse = new SignUpResponse();
        when(userService.signup(signupRequest)).thenReturn(signUpResponse);
    }

    @Test
    public void testSignup_Success() throws Exception {
        SignupRequest signupRequest = SignupRequest.builder()
                .email("testuser@example.com")
                .password("password")
                .name("Test User")
                .build();

        SignUpResponse signUpResponse = SignUpResponse.builder()
                .name("Test User")
                .build();

        when(userService.signup(any(SignupRequest.class))).thenReturn(signUpResponse);

        mockMvc.perform(post("/v1/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(signupRequest)))
                .andExpect(status().isOk());
    }


    @Test
    void testLogin() throws Exception {
        LoginRequest loginRequest = new LoginRequest("js@gmail.com", "js");
        LoginResponse expectedResponse =
                new LoginResponse(
                        "eyJhbGciOiJIUzI1NiJ9.eyJuYW1lIjoiY2MiLCJpZCI6NCwic3ViIjoiZGV2QGdtYWlsLmNvbSIsImlhdCI6MTcwNjk1OTA3NCwiZXhwIjoxNzA3MDQ1NDc0fQ.oGqlDAn6yXblAUPG-GXL__A5k78nYkfT95tL08y_gxo");
        when(userService.userLogin(any(LoginRequest.class))).thenReturn(expectedResponse);

        mockMvc.perform(
                        MockMvcRequestBuilders.post("/v1/users/login")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(new ObjectMapper().writeValueAsString(loginRequest)))
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(expectedResponse)));
    }

    @Test
    void testAccountBalance() throws Exception {
        AccountBalanceResponse buildResult =
                AccountBalanceResponse.builder().accountBalance(10.0d).name("Name").build();
        when(userService.accountBalance(Mockito.<Long>any(), Mockito.<AccountBalanceRequest>any()))
                .thenReturn(buildResult);
        MockHttpServletRequestBuilder contentTypeResult =
                MockMvcRequestBuilders.put("/v1/{userId}", 1L)
                        .contentType(MediaType.APPLICATION_JSON);

        ObjectMapper objectMapper = new ObjectMapper();
        MockHttpServletRequestBuilder requestBuilder =
                contentTypeResult.content(
                        objectMapper.writeValueAsString(new AccountBalanceRequest()));
        MockMvcBuilders.standaloneSetup(userController)
                .build()
                .perform(requestBuilder)
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.content().contentType("application/json"))
                .andExpect(
                        MockMvcResultMatchers.content()
                                .string("{\"accountBalance\":10.0,\"name\":\"Name\"}"));
    }
}
