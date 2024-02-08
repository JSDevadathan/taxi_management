package com.example.Taxi.Booking.contract.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    @NotBlank(message = "PickupLocation cannot be empty")
    private String pickupLocation;

    @NotBlank(message = "DropOffLocation cannot be empty")
    private String dropOffLocation;
}
