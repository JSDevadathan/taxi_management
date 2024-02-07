# UserService.Class

1. In **signup()** method, It's good to include an exception for the case where the email ID already exists.
2. In line no.29 in the **userLogin()** method, here exception is not thrown in the case of wrong email ID.
3. In line no.47 in the **accountBalance()** method, here exception is not thrown in the case of wrong email ID.
4. In line no.53 in the accountBalance() method, the amount is not being incremented. If a user wants to add some amount to their existing account balance, it does not increment. It only shows the first amount that is passed in the request.

# BookingService.class

1. In line no.48 in the book() method, the thrown exception is not valid. Thrown exception should be for insufficient balance not EntityNotFoundException(.)
2. In line no.114 in the findLocation() method, variable user is not used.

# UserController.class

1. In line no. 37, **accountBalance()** method, It doesn't require user name in request. If the name is empty or any other name is given, still returns the user name of user ID passed. 

# BookingController.class

1. In line no.47 in the **findLocation** method, It's better to give userId as path variable instead of request parameter.
2. In line no.47 in the **findLocation** method, userId is not needed. Because the taxis that are available in a specific location is same for every user.


#  TaxiRequest.class

1. Validation is not added for driverName, licenseName, currentLocation.
2. It's good to have license number as string.

#  BookingRequest.class

1. Empty validation is not added for pickupLocation, dropOffLocation.

#  AccountBalanceRequest.class

1. Validation is not added for accountBalance.

#  JwtServiceTest.class

1. JwtServiceTest.class should be in separate package.

#  UserServiceTest.class

1. In testAccountBalance() method EntityNotFoundException() is not tested.







