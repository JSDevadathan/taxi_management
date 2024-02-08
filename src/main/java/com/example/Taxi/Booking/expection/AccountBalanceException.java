package com.example.Taxi.Booking.expection;

public class AccountBalanceException extends RuntimeException{
    private final String entity;
    public AccountBalanceException(String entity) {
        super("Insufficient balance");
        this.entity = entity;
    }
}
