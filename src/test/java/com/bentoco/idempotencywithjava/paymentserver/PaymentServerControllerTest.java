package com.bentoco.idempotencywithjava.paymentserver;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PaymentServerControllerTest {

    @Test
    public void testSendPayment() {

        // Mock dependencies
        PaymentServerService paymentServerService = mock(PaymentServerService.class);
        PaymentServerController paymentServerController = new PaymentServerController(paymentServerService);

        // Mock input data
        Payment payment = new Payment("Bento", 200.0);
        String idempotentId = "id123";

        // Mock service response
        long paymentId = 123L;
        when(paymentServerService.processTransaction(idempotentId, payment)).thenReturn(paymentId);

        // Call controller method
        ResponseEntity<String> responseEntity = paymentServerController.sendPayment(payment, idempotentId);

        // Verify response
        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(String.valueOf(paymentId), responseEntity.getBody());
    }
}
