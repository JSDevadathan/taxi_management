package com.example.Taxi.Booking.service;

import com.example.Taxi.Booking.contract.request.TaxiRequest;
import com.example.Taxi.Booking.contract.response.TaxiResponse;
import com.example.Taxi.Booking.model.Taxi;
import com.example.Taxi.Booking.repository.TaxiRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import static org.mockito.Mockito.*;

@SpringBootTest
public class TaxiServiceTest {

    @InjectMocks
    private TaxiService taxiService;

    @Mock
    private TaxiRepository taxiRepository;

    @Mock
    private ModelMapper modelMapper;

    private TaxiRequest taxiRequest;
    private Taxi taxi;
    private TaxiResponse taxiResponse;

    @BeforeEach
    public void init() {
        taxiRequest = new TaxiRequest();
        taxi = new Taxi();
        taxiResponse = TaxiResponse.builder()
                .currentLocation("Current Location")
                .driverName("Driver Name")
                .licenseNumber(1L)
                .taxiId(1L)
                .build();
    }

    @Test
    public void testCreateTaxi() {
        when(taxiRepository.save(any(Taxi.class))).thenReturn(taxi);
        when(modelMapper.map(any(), eq(TaxiResponse.class))).thenReturn(taxiResponse);

        taxiService.createTaxi(taxiRequest);

        verify(modelMapper).map(any(), eq(TaxiResponse.class));
        verify(taxiRepository).save(any(Taxi.class));
    }
}
