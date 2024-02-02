package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.model.Booking;
import com.example.Taxi.Booking.repository.BookingRepository;
import com.example.Taxi.Booking.repository.TaxiRepository;
import com.example.Taxi.Booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class BookingServiceTest {
    private ModelMapper modelMapper;
    private BookingService bookingService;
    private BookingRepository bookingRepository;
    private TaxiRepository taxiRepository;
    private UserRepository userRepository;

    @BeforeEach
    public void init(){
        MockitoAnnotations.openMocks(this);
        bookingRepository = Mockito.mock(BookingRepository.class);
        modelMapper=new ModelMapper();
        bookingService=new BookingService(bookingRepository,modelMapper,taxiRepository,userRepository);
    }

}
