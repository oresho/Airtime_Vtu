# Airtime_Vtu
API service that authenticates users and provide airtime payments using XpressPay API.

## Overview

This repository contains the implementation of an API service for a payment company. The primary functionalities include user authentication using JSON Web Tokens (JWT) and facilitating airtime payments through the Virtual Top-Up (VTU) API.

# Configuration

Before running the application, ensure that you fill in the required information in the application.properties file. Below are the necessary configurations:properties
# Database Configuration
spring.datasource.url=your_database_url
spring.datasource.username=your_database_username
spring.datasource.password=your_database_password

# JWT Configuration
jwt.secret=your_secret_key
jwt.expiration-time=3600000 # Set your preferred expiration time in milliseconds

# VTU API Configuration
vtu.api.url=https://vtu-api.example.com
vtu.api.username=your_vtu_api_username
vtu.api.password=your_vtu_api_password
## Authentication API

The authentication API is implemented using JWT for secure and efficient user authentication. It ensures that only authorized users can access the payment services.

### Endpoints

- `/api/v1/auth/signup` - Allows users to create an account.
- `/api/v1/auth/login` - Validates user credentials and issues a JWT upon successful authentication.

### Usage

To create an account, make a `POST` request to `/api/v1/auth/signup` with the required user information.
```
{
  "firstname": "string",
  "lastname": "string",
  "email": "string",
  "phoneNo": "string",
  "password": "string"
}
```
For authentication, send a `POST` request to `/api/v1/auth/login` with the user's credentials. The response will include a JWT token, which should be included in the headers of subsequent requests for authorization.
```
{
  "email": "string",
  "password": "string"
}
```

## Airtime Payment API

The airtime payment functionality is achieved by consuming the BILLER SERVICES API BY XPRESS PAYMENTS. Before using this feature, users are required to create an account on the Biller Service Test environment portal. Please refer to the [documentation](https://docsbillerservices.xpresspayments.com) for the necessary information.

### Endpoint

- `/api/v1/topup` - Initiates an airtime payment transaction.

### Usage

To make an airtime payment, send a `POST` request to `/api/v1/topup`, pass destination phone number and amount in requesst body and pass the airtime type in the query parameters. Ensure that you include the JWT token obtained during authentication in the request headers for authorization.
```
{
  "phoneNumber": "08033333333",
  "amount": 100
}
```
## Unit Testing

Unit tests have been implemented to ensure the reliability of the codebase. The tests cover at least 50% of the code to verify the correctness of the authentication and airtime payment functionalities.

### Running Tests

Execute the following command to run the unit tests:

```bash
mvn compile test
```



