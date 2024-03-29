package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.constant.Status;
import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelResponse;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.repository.BookingRepository;
import com.example.Taxi.Booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;
    @Mock
    private BookingRepository bookingRepository;
    @Mock
    private ModelMapper modelMapper;


    @Test
    void testBooking() {
        BookingRequest bookingRequest = new BookingRequest("Kochi", "Vyttila");
        BookingResponse bookingResponse =
                new BookingResponse(1L, 1L, 1L, "Kochi", "Vyttila", 120D, LocalDateTime.now(), Status.BOOKED);
        when(bookingService.book(1L, 1L, 1L, bookingRequest)).thenReturn(bookingResponse);
    }


    @Test
    void testViewBookingById() throws Exception {
        Long id = 1L;
        BookingResponse bookingResponse = new BookingResponse();

        when(bookingService.view(id)).thenReturn(bookingResponse);

        mockMvc.perform(get("/v2/view/" + id))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(bookingResponse)));
    }

    @Test
    void testCancelBooking() throws Exception {
        Long bookingId = 1L;
        Long userId = 1L;
        Long taxiId = 1L;
        CancelResponse cancelResponse = CancelResponse.builder().cancel("Cancel").build();

        when(bookingService.cancelBooking(bookingId, userId, taxiId)).thenReturn(cancelResponse);

        mockMvc.perform(
                        put("/v2/cancel")
                                .param("bookingId", String.valueOf(bookingId))
                                .param("userId", String.valueOf(userId))
                                .param("taxiId", String.valueOf(taxiId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cancelResponse)));
    }

    @Test
    void testFindLocation() throws Exception {
        String pickupLocation = "bbb";
        List<TaxiResponse> taxiResponses = new ArrayList<>();

        when(bookingService.findLocation(pickupLocation)).thenReturn(taxiResponses);

        mockMvc.perform(
                        get("/v2/location")
                                .param("pickupLocation", pickupLocation))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(taxiResponses)));
    }
}
