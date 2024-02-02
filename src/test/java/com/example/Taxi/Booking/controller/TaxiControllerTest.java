package com.example.Taxi.Booking.controller;

import com.example.Taxi.Booking.contract.request.TaxiRequest;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.service.TaxiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
public class TaxiControllerTest {

    @MockBean
    private TaxiService taxiService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void testCreateTaxi(){
        TaxiRequest taxiRequest = new TaxiRequest(null, null, null);
        TaxiResponse taxiResponse = new TaxiResponse(1L, null, null, null);
        when(taxiService.createTaxi(taxiRequest))
                .thenReturn(taxiResponse);
    }
}
