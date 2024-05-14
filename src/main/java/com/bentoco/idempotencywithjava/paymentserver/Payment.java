package com.bentoco.idempotencywithjava.paymentserver;

public record Payment(String to, Double amount) {
}
