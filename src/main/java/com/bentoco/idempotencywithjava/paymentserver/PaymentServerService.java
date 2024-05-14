package com.bentoco.idempotencywithjava.paymentserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

/**
 * Service class responsible for processing payment transactions.
 */
@Service
public class PaymentServerService {

    private final ConcurrentHashMap<String, Long> processedIds = new ConcurrentHashMap<>();
    private final PaymentServerRepository paymentServerRepository;

    /**
     * Constructor to initialize PaymentServerService with PaymentServerRepository dependency.
     * @param paymentServerRepository The PaymentServerRepository instance.
     */
    @Autowired
    public PaymentServerService(PaymentServerRepository paymentServerRepository) {
        this.paymentServerRepository = paymentServerRepository;
    }

    /**
     * Process a payment transaction.
     * @param idempotentId The idempotent ID for the request.
     * @param payment The payment details.
     * @return The payment ID.
     */
    public Long processTransaction(String idempotentId, Payment payment) {
        if (isIdempotent(idempotentId)) {
            return processedIds.get(idempotentId);
        } else {
            Long id = paymentServerRepository.save(payment);
            persistIdempotentId(idempotentId, id);
            return id;
        }
    }

    /**
     * Persist the idempotent ID after successful transaction completion.
     * @param idempotentId The idempotent ID for the request.
     * @param id The payment ID.
     */
    private void persistIdempotentId(String idempotentId, Long id) {
        processedIds.put(idempotentId, id);
    }

    /**
     * Check if the idempotent ID has been processed.
     * @param idempotentId The idempotent ID for the request.
     * @return True if idempotent ID has been processed, false otherwise.
     */
    private boolean isIdempotent(String idempotentId) {
        return processedIds.containsKey(idempotentId);
    }
}


