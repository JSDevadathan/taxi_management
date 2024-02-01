package com.example.Taxi.Booking.expection;

public class EntityNotFoundException extends RuntimeException {

    private final String entity;
    private final Long id;
    public EntityNotFoundException(String entity) {
        super("No entity " + entity + " found ");
        this.entity = entity;
        this.id = 0L;
    }
}
