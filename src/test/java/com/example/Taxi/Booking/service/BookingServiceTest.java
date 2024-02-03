package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.constant.Status;
import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.model.Booking;
import com.example.Taxi.Booking.model.Taxi;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.BookingRepository;
import com.example.Taxi.Booking.repository.TaxiRepository;
import com.example.Taxi.Booking.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {BookingService.class})
@ExtendWith(SpringExtension.class)
public class BookingServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @MockBean
    private ModelMapper modelMapper;

    @MockBean
    private TaxiRepository taxiRepository;

    @MockBean
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
        bookingRepository = Mockito.mock(BookingRepository.class);
        taxiRepository = Mockito.mock(TaxiRepository.class);
        userRepository = Mockito.mock(UserRepository.class);
        modelMapper = new ModelMapper();
        bookingService = new BookingService(bookingRepository, modelMapper, taxiRepository, userRepository);
    }

    @Test
    void testBook() {
        Taxi taxi = new Taxi(1L, "cc", null, "ds");
        User user = new User(1L, "vv", "das@gmail.com", "ds", 10.0);
        Optional<Taxi> ofResult = Optional.of(taxi);
        when(taxiRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userRepository.findById(Mockito.<Long>any())).thenThrow(new EntityNotFoundException("An error occurred"));

        assertThrows(EntityNotFoundException.class,
                () -> bookingService.book(1L, 1L, 1L, new BookingRequest("Pickup Location", "Drop Off Location")));

        verify(taxiRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }


    @Test
    public void testViewBookingNotFound() {
        Long id = 1L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.view(id));
    }
    @Test
    void testCancelBooking(){
        Long bookingId = 1L;
        Long taxiId = 1L;
        Long userId = 1L;
        Booking booking = new Booking();
        Taxi taxi = new Taxi();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.cancelBooking(bookingId, userId, taxiId));

        verify(bookingRepository).findById(bookingId);
        verify(userRepository).findById(userId);

    }

    @Test
    void testFindLocation() {
        Long userId = 1L;
        String location = "Pickup Location";
        User user = new User();

        when(taxiRepository.findAll()).thenReturn(new ArrayList<>());
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(EntityNotFoundException.class, () -> bookingService.findLocation(userId, location));

        verify(userRepository).findById(userId);
        verify(taxiRepository).findAll();
    }


}
