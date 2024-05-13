package com.bentoco.idempotencywithjava.etag;

public record Customer(Long id, String name, Short age, String eTag) {
}
