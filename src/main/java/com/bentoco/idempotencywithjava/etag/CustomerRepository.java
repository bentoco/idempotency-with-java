package com.bentoco.idempotencywithjava.etag;

import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class CustomerRepository {

    private final ConcurrentHashMap<Long, Customer> storage;

    public CustomerRepository() {
        storage = new ConcurrentHashMap<>();
        initializeStorage();
    }

    private void initializeStorage() {
        storage.put(1L, new Customer(1L, "John", (short) 25, UUID.randomUUID().toString()));
        storage.put(2L, new Customer(2L, "Anne", (short) 34, UUID.randomUUID().toString()));
    }

    public void save(Customer customer) {
        storage.put(customer.id(), customer);
    }

    public Optional<Customer> findById(Long id) {
        return Optional.ofNullable(storage.get(id));
    }
}
