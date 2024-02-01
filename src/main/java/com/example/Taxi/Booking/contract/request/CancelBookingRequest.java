package com.example.Taxi.Booking.contract.request;

import com.example.Taxi.Booking.constant.Status;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CancelBookingRequest {
    @Enumerated(EnumType.STRING)
    private Status status;
}
