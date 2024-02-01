package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.request.CancelBookingRequest;
import com.example.Taxi.Booking.contract.request.SignupRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelBookingResponse;
import com.example.Taxi.Booking.model.Booking;
import com.example.Taxi.Booking.model.Taxi;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.BookingRepository;
import com.example.Taxi.Booking.repository.TaxiRepository;
import com.example.Taxi.Booking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookRepository;
    private final ModelMapper modelMapper;
    private final TaxiRepository taxiRepository;
    private final UserRepository userRepository;

    public BookingResponse book(BookingRequest bookingRequest) {
        Taxi taxi = taxiRepository.findById(bookingRequest.getTaxiId()).orElseThrow(() -> new EntityNotFoundException("Taxi not found"));
        User user = userRepository.findById(bookingRequest.getUserId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        Booking booking = Booking.builder()
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .fare(bookingRequest.getFare())
                .taxiId(taxi.getTaxiId())
                .userId(user.getUserId())
                .status(bookingRequest.getStatus())
                .build();
        booking = bookRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }

    public List<BookingResponse> view() {
        List<Booking> bookings = bookRepository.findAll();
        return bookings.stream()
                .map(booking -> modelMapper.map(booking, BookingResponse.class))
                .collect(Collectors.toList());
    }

    public CancelBookingResponse cancel(Long bookingId, CancelBookingRequest cancelBookingRequest) {
        Booking booking = Booking.builder()
                .status(cancelBookingRequest.getStatus())
                .build();
        booking = bookRepository.save(booking);
        return modelMapper.map(booking, CancelBookingResponse.class);
    }
}
