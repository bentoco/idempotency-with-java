package com.bentoco.idempotencywithjava.paymentserver;

import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Repository class responsible for managing payments.
 */
@Repository
public class PaymentServerRepository {

    private final ConcurrentHashMap<Long, Payment> storage;

    /**
     * Constructor to initialize PaymentServerRepository.
     */
    public PaymentServerRepository() {
        storage = new ConcurrentHashMap<>();
    }

    /**
     * Save a payment.
     * @param payment The payment to be saved.
     * @return The ID of the saved payment.
     */
    public Long save(Payment payment) {
        Long id = storage.isEmpty() ? 1L : storage.keys().nextElement() + 1;
        storage.put(id, payment);
        return id;
    }
}

