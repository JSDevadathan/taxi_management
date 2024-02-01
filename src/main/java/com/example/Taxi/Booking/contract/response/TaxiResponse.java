package com.example.Taxi.Booking.contract.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiResponse {
    private Long taxiId;
    private String driverName;
    private Long licenseNumber;
    private String currentLocation;
}
