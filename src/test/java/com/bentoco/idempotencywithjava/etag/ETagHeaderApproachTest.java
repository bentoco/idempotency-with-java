package com.bentoco.idempotencywithjava.etag;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class ETagHeaderApproachTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private ETagHeaderApproach controller;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateCustomers_ExistingCustomer_DoesNotMatchETag_ReturnsConflict() {
        // Arrange
        long customerId = 1L;
        String existingETag = UUID.randomUUID().toString();
        CustomerInput customerInput = new CustomerInput("John", (short) 30);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer(customerId, "John", (short) 30, UUID.randomUUID().toString())));

        // Act
        ResponseEntity<CustomerOutput> response = controller.updateCustomers(customerId, existingETag, customerInput);

        // Assert
        assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
    }

    @Test
    void updateCustomers_ExistingCustomer_MatchesETag_ReturnsOk() {
        // Arrange
        long customerId = 1L;
        String existingETag = UUID.randomUUID().toString();
        CustomerInput customerInput = new CustomerInput("John", (short) 30);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(new Customer(customerId, "John", (short) 30, existingETag)));

        // Act
        ResponseEntity<CustomerOutput> response = controller.updateCustomers(customerId, existingETag, customerInput);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void updateCustomers_NonExistingCustomer_ReturnsNotFound() {
        // Arrange
        long nonExistingCustomerId = 100L;
        CustomerInput customerInput = new CustomerInput("John", (short) 30);
        when(customerRepository.findById(nonExistingCustomerId)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<CustomerOutput> response = controller.updateCustomers(nonExistingCustomerId, UUID.randomUUID().toString(), customerInput);

        // Assert
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }
}
