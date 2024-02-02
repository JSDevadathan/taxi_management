package com.example.Taxi.Booking.contract.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String pickupLocation;
    private String dropOffLocation;
}
