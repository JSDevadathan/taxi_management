package com.example.Taxi.Booking.expection;

public class FailedToGenerateException extends RuntimeException {
    private final String entity;
    private final int id;

    public FailedToGenerateException(String entity) {
        super("failed to generate" + entity);
        this.entity = entity;
        this.id = 0;
    }
}
