package com.example.Taxi.Booking.contract.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaxiRequest {
    @NotBlank(message = "DriverName must not be blank")
    private String driverName;

    @NotNull(message = "LicenseNumber must not be blank")
    private Long licenseNumber;

    @NotBlank(message = "CurrentLocation must not be blank")
    private String currentLocation;
}
