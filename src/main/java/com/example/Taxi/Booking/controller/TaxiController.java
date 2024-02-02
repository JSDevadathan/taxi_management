package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.TaxiRequest;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.service.TaxiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class TaxiController {

    private final TaxiService taxiService;

    @PostMapping("/taxi")
    public TaxiResponse createTaxi(@RequestBody TaxiRequest taxiRequest){
        return taxiService.createTaxi(taxiRequest);
    }



}
