package com.example.Taxi.Booking.contract.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SignupRequest {
    @NotBlank(message = "Please enter name")
    private String name;

    @Email
    @NotBlank(message = "Please enter email address")
    private String email;

    @NotBlank(message = "Please enter password")
    private String password;
}
