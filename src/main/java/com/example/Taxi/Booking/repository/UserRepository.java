package com.example.Taxi.Booking.repository;

import com.example.Taxi.Booking.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
   
    
    boolean existsByEmail(String email);

    Optional<User> findByEmail(String email);
}
