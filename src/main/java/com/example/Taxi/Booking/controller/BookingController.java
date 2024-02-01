package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.request.CancelBookingRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelBookingResponse;
import com.example.Taxi.Booking.service.BookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1")
public class BookingController {
    private final BookingService bookingService;

    @PostMapping("/booking")
    public BookingResponse book(@RequestBody BookingRequest bookingRequest){
        return bookingService.book(bookingRequest);
    }

    @GetMapping("/view")
    public List<BookingResponse> view(){
        return bookingService.view();
    }

    @PutMapping("/cancel/{bookingId}")
    public CancelBookingResponse cancel(@PathVariable Long bookingId, @RequestBody CancelBookingRequest cancelBookingRequest){
        return bookingService.cancel(bookingId, cancelBookingRequest);
    }
}
