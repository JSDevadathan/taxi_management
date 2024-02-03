package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.contract.response.BookingResponse;
import com.example.Taxi.Booking.contract.response.CancelResponse;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.service.BookingService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class BookingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookingService bookingService;

    @InjectMocks
    private BookingController bookingController;

    @Test
    void testBooking() {
        BookingRequest bookingRequest = new BookingRequest(null,null);
        BookingResponse bookingResponse = new BookingResponse(1L, 1L, 1L, null, null, null, null, null);
        when(bookingService.book(1L, 1L ,1L, bookingRequest))
                .thenReturn(bookingResponse);
    }

    @Test
    void testViewBookingById() throws Exception {
        Long id = 1L;
        BookingResponse bookingResponse =
                new BookingResponse();

        when(bookingService.view(id)).thenReturn(bookingResponse);

        mockMvc.perform(get("/v1/view/" + id))
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

        mockMvc.perform(put("/v1/cancel/" + bookingId)
                        .param("userId", String.valueOf(userId))
                        .param("taxiId", String.valueOf(taxiId)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(cancelResponse)));
    }


    @Test
    void testFindLocation() throws Exception {
        Long userId = 1L;
        String pickupLocation = "foo";
        List<TaxiResponse> taxiResponses = new ArrayList<>();

        when(bookingService.findLocation(userId, pickupLocation)).thenReturn(taxiResponses);

        mockMvc.perform(get("/v1/location")
                        .param("userId", String.valueOf(userId))
                        .param("pickupLocation", pickupLocation))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(new ObjectMapper().writeValueAsString(taxiResponses)));
    }



}
