package com.example.Taxi.Booking.contract.response;

import com.example.Taxi.Booking.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
