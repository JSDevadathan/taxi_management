package com.example.Taxi.Booking.expection;

public class InvalidUserException extends RuntimeException {
        private String entity;
        private Long id;

        public InvalidUserException(String entity) {
            super("Invalid " + entity);
        }
}
