package com.example.Taxi.Booking.expection;

public class InvalidUserException extends RuntimeException {
    public InvalidUserException(String login) {
        super("Invalid");
    }
}
