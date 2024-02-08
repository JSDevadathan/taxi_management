package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.TaxiRequest;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.service.TaxiService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping("/taxi")
    public TaxiResponse createTaxi(@Valid @RequestBody TaxiRequest taxiRequest) {
        return taxiService.createTaxi(taxiRequest);
    }
}
