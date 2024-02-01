package com.example.Taxi.Booking.contract.request;

import com.example.Taxi.Booking.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingRequest {
    private String pickupLocation;
    private String dropOffLocation;
    private Double fare;
    private Long taxiId;
    private Long userId;
    @Enumerated(EnumType.STRING)
    private Status status;
}
