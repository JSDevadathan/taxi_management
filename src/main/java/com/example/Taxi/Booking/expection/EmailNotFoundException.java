package com.example.Taxi.Booking.expection;

public class EmailNotFoundException extends RuntimeException{
    private final String entity;

    public EmailNotFoundException(String entity) {
        super("Invalid" + entity);
        this.entity = entity;
    }
}
