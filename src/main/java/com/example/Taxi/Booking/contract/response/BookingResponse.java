package com.example.Taxi.Booking.contract.response;

import com.example.Taxi.Booking.constant.Status;
import com.example.Taxi.Booking.model.Taxi;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookingResponse {
    private Long bookingId;
    private Long userId;
    private Long taxiId;
    private String pickupLocation;
    private String dropOffLocation;
    private Double fare;
    private LocalDateTime bookingTime;

    @Enumerated(EnumType.STRING)
    private Status status;
}
