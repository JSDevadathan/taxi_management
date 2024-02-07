## Review By Devanand

### Model:

1. Make the user model simple, put most of the additional information in somewhere else



### test:

1. EntityNotFoundException should be tested for view(), cancelBooking(),  findLocation() in booking service test and  accountBalance() in userService.

### Exception:
1. FailedToGenerateException is never used.

### Service :
line no. 71 , savings is not used / not returned.