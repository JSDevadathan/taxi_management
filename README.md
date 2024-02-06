# Taxi Booking Management System

#### The Taxi Booking Management System is a Spring Boot application. It is a platform for users to book taxi, view their bookings and also able to access the driver information.

## API Endpoints for UserController:

#### /v1/users/signup (POST): Sign up a new user.

#### /v1/users/login (POST): Log in an existing user.

#### /v1/users/{userId} (PUT): Update user account balance.

## API Endpoints for BookingController:

#### /v1/booking/ (POST): Booking taxi.

#### /v1/view/{id}/ (GET): View booking by userId.

#### /v1/cancel/{bookingId}/ (GET): Cancel booking.

#### /v1/location/ (GET): Finding Nearest Taxi Location.

## API Endpoints for TaxiController:

#### /v1/taxi/ (POST): Create a new Taxi.