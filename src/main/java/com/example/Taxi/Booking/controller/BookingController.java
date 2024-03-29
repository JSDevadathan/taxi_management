package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelResponse;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.service.BookingService;
import java.util.List;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse book(@Valid
            @RequestParam Long userId,
            @RequestParam Long taxiId,
            @RequestParam Long distance,
            @RequestBody BookingRequest bookingRequest) {
        return bookingService.book(userId, taxiId, distance, bookingRequest);
    }

    @GetMapping("/view/{id}")
    public BookingResponse view(@PathVariable Long id) {
        return bookingService.view(id);
    }

    @PutMapping("/cancel")
    public CancelResponse cancelBooking(
            @RequestParam Long bookingId, @RequestParam Long userId, @RequestParam Long taxiId) {
        return bookingService.cancelBooking(bookingId, userId, taxiId);
    }

    @GetMapping("/location")
    public List<TaxiResponse> findLocation(@RequestParam String pickupLocation) {
        return bookingService.findLocation(pickupLocation);
    }
}
