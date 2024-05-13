package com.bentoco.idempotencywithjava.etag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
public class ETagHeaderApproach {

    private final CustomerRepository customerRepository;

    @Autowired
    public ETagHeaderApproach(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @PutMapping("/customers/{id}")
    public ResponseEntity<CustomerOutput> updateCustomers(
            @PathVariable Long id,
            @RequestHeader("If-Match") String matcherHeader,
            @RequestBody CustomerInput customerInput
    ) {
        // getting the existing customer and its ETag
        Optional<Customer> optionalCustomer = customerRepository.findById(id);
        if (optionalCustomer.isPresent()) {
            Customer existingCustomer = optionalCustomer.get();
            String eTag = existingCustomer.eTag();

            // checking for a match
            if (!eTag.equals(matcherHeader)) {
                return ResponseEntity.status(CONFLICT).build();
            }

            //updating the customer content and generating a new ETag
            String newETag = UUID.randomUUID().toString();
            Customer updatedCustomer = new Customer(id, customerInput.name(), customerInput.age(), newETag);
            customerRepository.save(updatedCustomer);

            // returning the success response with the new ETag:
            return ResponseEntity.ok(new CustomerOutput(newETag));
        }
        return ResponseEntity.status(NOT_FOUND).build();
    }
}
