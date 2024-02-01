package com.example.Taxi.Booking.repository;

import com.example.Taxi.Booking.contract.request.BookingRequest;
import com.example.Taxi.Booking.model.Taxi;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaxiRepository extends JpaRepository<Taxi, Long> {
}
