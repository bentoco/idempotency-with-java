package com.bentoco.idempotencywithjava.paymentserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.HttpStatus.CREATED;

/**
 * Controller class responsible for handling payment-related HTTP requests.
 */
@RestController
public class PaymentServerController {

    private final PaymentServerService paymentServerService;

    /**
     * Constructor to initialize PaymentServerController with PaymentServerService dependency.
     * @param paymentServerService The PaymentServerService instance.
     */
    @Autowired
    public PaymentServerController(PaymentServerService paymentServerService) {
        this.paymentServerService = paymentServerService;
    }

    /**
     * Endpoint to send a payment request.
     * @param input The payment details.
     * @param idempotentId The idempotent ID for the request.
     * @return ResponseEntity with HTTP status and payment ID.
     */
    @PostMapping
    public ResponseEntity<String> sendPayment(
            @RequestBody Payment input,
            @RequestHeader("x-idempotent-id") String idempotentId
    ) {
        Long id = paymentServerService.processTransaction(idempotentId, input);
        return ResponseEntity.status(CREATED).body(String.valueOf(id));
    }
}

