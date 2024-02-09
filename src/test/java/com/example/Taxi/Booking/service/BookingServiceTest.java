package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelResponse;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.expection.AccountBalanceException;
import com.example.Taxi.Booking.expection.EntityNotFoundException;
import com.example.Taxi.Booking.model.Booking;
import com.example.Taxi.Booking.model.Taxi;
import com.example.Taxi.Booking.model.User;
import com.example.Taxi.Booking.repository.BookingRepository;
import com.example.Taxi.Booking.repository.TaxiRepository;
import com.example.Taxi.Booking.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.example.Taxi.Booking.constant.Status.BOOKED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = {BookingService.class})
@ExtendWith(SpringExtension.class)
public class BookingServiceTest {
    @MockBean
    private BookingRepository bookingRepository;

    @InjectMocks
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
        bookingService =
                new BookingService(bookingRepository, modelMapper, taxiRepository, userRepository);
    }


    @Test
    void testBook_InsufficientBalance() {
        Taxi taxi = new Taxi(1L, "Ram", 12L, "UK");
        User user = new User(1L, "Tom", "tom@gmail.com", "tom123", 10.0);
        BookingRequest bookingRequest = new BookingRequest("UK", "US");
        Optional<Taxi> ofResult = Optional.of(taxi);
        Optional<User> ofUser = Optional.of(user);

        when(taxiRepository.findById(Mockito.<Long>any())).thenReturn(ofResult);
        when(userRepository.findById(Mockito.<Long>any())).thenReturn(ofUser);

        assertThrows(
                AccountBalanceException.class,
                () -> bookingService.book(1L, 1L, 20L, bookingRequest));

        verify(taxiRepository).findById(Mockito.<Long>any());
        verify(userRepository).findById(Mockito.<Long>any());
    }

    @Test
    public void testFindLocationAddsTaxisInPickupLocation() {
        Long userId = 1L;
        String pickupLocation = "UK";
        Taxi taxiInLocation = Taxi.builder()
                .currentLocation(pickupLocation)
                .build();
        List<Taxi> allTaxis = Collections.singletonList(taxiInLocation);
        when(taxiRepository.findAll()).thenReturn(allTaxis);

        User user = User.builder().build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        List<TaxiResponse> result = bookingService.findLocation(pickupLocation);

        assertEquals(1, result.size());
        verify(taxiRepository).findAll();
    }

    @Test
    public void testBookThrowsExceptionWhenFareExceedsBalance() {
        Long userId = 1L;
        Long taxiId = 1L;
        Long distance = 100L;
        Double minimumCharge = 10.00;
        Double fare = distance * minimumCharge;

        User user = User.builder()
                .userId(userId)
                .accountBalance(fare - 1)
                .build();
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Taxi taxi = Taxi.builder()
                .taxiId(taxiId)
                .build();
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));

        BookingRequest bookingRequest = new BookingRequest();

        assertThrows(AccountBalanceException.class, () -> {
            bookingService.book(userId, taxiId, distance, bookingRequest);
        }, "Insufficient balance");
    }


    @Test
    public void testCancelBooking_Success() {
        Long bookingId = 1L;
        Long userId = 1L;
        Long taxiId = 1L;

        User user = User.builder().userId(userId).build();
        Taxi taxi = Taxi.builder().taxiId(taxiId).build();
        Booking booking = Booking.builder()
                .bookingId(bookingId)
                .user(user)
                .taxi(taxi)
                .pickupLocation("UK")
                .dropOffLocation("US")
                .bookingTime(LocalDateTime.now())
                .fare(100.00)
                .status(BOOKED)
                .build();

        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));
        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));

        CancelResponse cancelResponse = bookingService.cancelBooking(bookingId, userId, taxiId);

        assertNotNull(cancelResponse);
        assertEquals("Booking Cancelled", cancelResponse.getCancel());
    }


    @Test
    public void testBook_TaxiNotFound() {
        Long userId = 1L;
        Long taxiId = 2L;
        Long distance = 10L;
        BookingRequest bookingRequest = new BookingRequest();

        when(taxiRepository.findById(taxiId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookingService.book(userId, taxiId, distance, bookingRequest);
        });
    }

    @Test
    public void testBook_UserNotFound() {
        Long userId = 1L;
        Long taxiId = 2L;
        Long distance = 10L;
        BookingRequest bookingRequest = new BookingRequest();

        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(new Taxi()));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> {
            bookingService.book(userId, taxiId, distance, bookingRequest);
        });
    }


    @Test
    public void testViewBookingNotFound() {
        Long id = 1L;

        when(bookingRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bookingService.view(id));
    }


    @Test
    void testCancelBooking() {
        Long bookingId = 1L;
        Long taxiId = 1L;
        Long userId = 1L;
        Booking booking = new Booking();
        Taxi taxi = new Taxi();

        when(bookingRepository.findById(bookingId)).thenReturn(Optional.of(booking));
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        assertThrows(
                EntityNotFoundException.class,
                () -> bookingService.cancelBooking(bookingId, userId, taxiId));

        verify(bookingRepository).findById(bookingId);
        verify(userRepository).findById(userId);
    }

    @Test
    void testFindLocation() {
        String location = "Pickup Location";
        User user = new User();
        when(taxiRepository.findAll()).thenReturn(new ArrayList<>());
        assertThrows(
                EntityNotFoundException.class, () -> bookingService.findLocation(location));
        verify(taxiRepository).findAll();
    }

    @Test
    void testBookTaxi() {
        User user = new User(1L, "js", "js@gmail.com", "Helloworld", 1000.0);
        Taxi taxi = new Taxi(1L, "Siuuu", 12L, "US");
        BookingRequest request = new BookingRequest("US", "Uk");
        Long taxiId = 1L;
        Long distance = 80L;
        Double minimumCharge = 10.00;
        Double fare = distance * minimumCharge;
        Booking expectedBooking =
                Booking.builder()
                        .user(user)
                        .taxi(taxi)
                        .pickupLocation(request.getPickupLocation())
                        .dropOffLocation(request.getDropOffLocation())
                        .bookingTime(LocalDateTime.parse(LocalDateTime.now().toString()))
                        .fare(fare)
                        .status(BOOKED)
                        .build();
        User updatedUser =
                User.builder()
                        .userId(user.getUserId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .accountBalance(user.getAccountBalance() - expectedBooking.getFare())
                        .build();

        BookingResponse expectedResponse = modelMapper.map(expectedBooking, BookingResponse.class);

        Authentication auth = Mockito.mock(Authentication.class);
        SecurityContext secCont = Mockito.mock(SecurityContext.class);
        Mockito.when(secCont.getAuthentication()).thenReturn(auth);
        SecurityContextHolder.setContext(secCont);
        when(SecurityContextHolder.getContext().getAuthentication().getPrincipal())
                .thenReturn(user);


        when(userRepository.findById(user.getUserId())).thenReturn(Optional.of(user));
        when(taxiRepository.findById(taxiId)).thenReturn(Optional.of(taxi));


        when(bookingRepository.save(any())).thenReturn(expectedBooking);
        when(userRepository.save(any())).thenReturn(updatedUser);

        BookingResponse actualResponse = bookingService.book(user.getUserId(), taxiId, distance, request);
        assertEquals(expectedResponse, actualResponse);
    }

}
