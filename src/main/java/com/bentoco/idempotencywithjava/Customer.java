package com.bentoco.idempotencywithjava;

public record Customer(Long id, String name, Short age, String eTag) {
}
