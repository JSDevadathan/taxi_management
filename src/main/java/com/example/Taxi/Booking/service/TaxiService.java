package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.TaxiRequest;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.model.Taxi;
import com.example.Taxi.Booking.repository.TaxiRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaxiService {
    private final TaxiRepository taxiRepository;
    private final ModelMapper modelMapper;
    public TaxiResponse createTaxi(TaxiRequest taxiRequest) {
        Taxi taxi = Taxi.builder()
                .currentLocation(taxiRequest.getCurrentLocation())
                .driverName(taxiRequest.getDriverName())
                .licenseNumber(taxiRequest.getLicenseNumber())
                .build();
        taxi = taxiRepository.save(taxi);
        return modelMapper.map(taxi, TaxiResponse.class);
    }
}
