# Idempotency with Java and Spring Boot

This project demonstrates how to implement idempotency in a Java Spring Boot application using an in-memory cache.

# Payment server

Process payments with control of idempotent keys

## Overview

The project consists of the following components:

- `PaymentServerController`: Controller class responsible for handling payment-related HTTP requests.
- `PaymentServerService`: Service class responsible for processing payment transactions.
- `PaymentServerRepository`: Repository class responsible for managing payments.

## Usage

To send a payment request, make a POST request to the `/payments` endpoint with the payment details in the request body and the idempotent ID in the `x-idempotent-id` header.

Example request:

```bash
curl -X POST \
  -H "Content-Type: application/json" \
  -H "x-idempotent-id: unique-id-123" \
  -d '{"amount": 100, "to": "Foo"}' \
  http://localhost:8080/sendPayment
```

# ETag Header


This project demonstrates how to implement ETag-based concurrency control in a Java Spring Boot application for updating customer records.

An ETag header (Entity Tag) is an HTTP header used for web cache validation and conditional requests. It is mainly used for  PUT requests, that only update resources if they haven't changed since the last check.
## Overview

The project consists of the following components:

- `ETagHeaderApproach`: REST controller responsible for handling PUT requests to update customer records using ETag-based concurrency control.
- `CustomerRepository`: Repository class responsible for managing customer records.

## Usage

To update a customer record, make a PUT request to the /customers/{id} endpoint with the customer ID in the path, the updated customer information in the request body, and the ETag of the existing customer record in the "If-Match" header.

Example request:

```bash
curl -X PUT \
-H "Content-Type: application/json" \
-H "If-Match: <existing-etag>" \
-d '{"name": "John Doe", "age": 30}' \
http://localhost:8080/customers/{id}
```

## Components

### ETagHeaderApproach

This class represents a REST controller that implements ETag-based concurrency control for updating customer records.

#### Methods

- `updateCustomers`: Handles PUT requests to update customer records.

### CustomerRepository

This class represents a repository for managing customer records.

#### Methods

- `save`: Saves a customer record to the repository.
- `findById`: Retrieves a customer record by ID from the repository.

# References

- https://www.freecodecamp.org/news/idempotency-in-http-methods/
- https://www.baeldung.com/cs/idempotent-operations
- https://www.javacodegeeks.com/2021/06/making-post-and-patch-requests-idempotent.html
- https://www.alexhyett.com/idempotency/