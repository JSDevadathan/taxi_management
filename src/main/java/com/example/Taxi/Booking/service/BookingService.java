package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.constant.Status;
import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelResponse;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
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

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository bookRepository;
    private final ModelMapper modelMapper;
    private final TaxiRepository taxiRepository;
    private final UserRepository userRepository;

    public BookingResponse book(Long userId, Long taxiId, Long distance, BookingRequest bookingRequest) {
        Taxi taxi = taxiRepository.findById(taxiId).orElseThrow(() -> new EntityNotFoundException("Taxi not found"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Double minimumCharge = 10.00;
        Double fare = distance * minimumCharge;

        if(fare > user.getAccountBalance()) {
            throw new EntityNotFoundException("Insufficient balance");
        }

        Booking booking = Booking.builder()
                .pickupLocation(bookingRequest.getPickupLocation())
                .dropOffLocation(bookingRequest.getDropOffLocation())
                .fare(fare)
                .taxiId(taxi.getTaxiId())
                .userId(user.getUserId())
                .bookingTime(LocalDateTime.now())
                .status(Status.BOOKED)
                .build();

        User savings = User.builder()
                .userId(user.getUserId())
                .name(user.getName())
                .email(user.getEmail())
                .password(user.getPassword())
                .accountBalance(user.getAccountBalance() - booking.getFare())
                .build();

        savings = userRepository.save(savings);
        booking = bookRepository.save(booking);
        return modelMapper.map(booking, BookingResponse.class);
    }

    public BookingResponse view(Long id) {
        Booking booking = bookRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Booking not found"));
        return modelMapper.map(booking, BookingResponse.class);

    }

    public CancelResponse cancelBooking(Long bookingId, Long userId, Long taxiId) {
        Booking booking = bookRepository.findById(bookingId).orElseThrow(() -> new EntityNotFoundException("Booking"));
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User"));
        Taxi taxi = taxiRepository.findById(taxiId).orElseThrow(() -> new EntityNotFoundException("Taxi"));
        Booking bookings = Booking.builder()
                .bookingId(bookingId)
                .user(user)
                .taxi(taxi)
                .pickupLocation(booking.getPickupLocation())
                .dropOffLocation(booking.getDropOffLocation())
                .bookingTime(LocalDateTime.parse(LocalDateTime.now().toString()))
                .fare(booking.getFare())
                .status(Status.CANCELLED)
                .build();
        bookRepository.save(bookings);
        return CancelResponse.builder()
                .cancel("Booking Cancelled")
                .build();
    }

    public List<TaxiResponse> findLocation(Long userId, String pickupLocation) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Taxi> taxi = taxiRepository.findAll();
        List<Taxi> taxiAvailable = new ArrayList<>();
        for(Taxi taxis: taxi){
            if(taxis.getCurrentLocation().equals(pickupLocation)){
                taxiAvailable.add(taxis);
            }
        }
        if (taxiAvailable.isEmpty()){
            throw new EntityNotFoundException("No taxi available");
        }
        else {
            return taxiAvailable.stream().map(taxi1 -> modelMapper.map(taxi1, TaxiResponse.class))
                    .collect(Collectors.toList());
        }
    }


}
