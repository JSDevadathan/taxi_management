# Taxi Booking Management System

#### The Taxi Booking Management System is a Spring Boot application. It is a platform for users to book taxi, view their bookings and also able to access the driver information.

## API Endpoints for UserController:

#### /v1/users/signup (POST): Sign up a new user.

#### /v1/users/login (POST): Log in an existing user.

#### /v1/{userId} (PUT): Update user account balance.

## API Endpoints for BookingController:

#### /v2/booking/ (POST): Booking taxi.

#### /v2/view/{id}/ (GET): View booking by userId.

#### /v2/cancel/{bookingId}/ (GET): Cancel booking.

#### /v2/location/ (GET): Finding Nearest Taxi Location.

## API Endpoints for TaxiController:

#### /v2/taxi/ (POST): Create a new Taxi.