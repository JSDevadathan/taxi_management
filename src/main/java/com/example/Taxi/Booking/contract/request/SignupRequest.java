package com.example.Taxi.Booking.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import jakarta.validation.constraints.Email;


@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @Email
    private String email;
    private String password;


}
