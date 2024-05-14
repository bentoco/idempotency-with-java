#!/bin/bash

# Define variables
PAYLOAD='{"amount": 100, "to": "Bento"}'
IDEMPOTENT_ID='unique-id-123'
URL='http://localhost:8080/payments'
RESPONSE_FILE='response.txt'

# Call the controller endpoint using curl
curl -X POST \
  -H "Content-Type: application/json" \
  -H "x-idempotent-id: $IDEMPOTENT_ID" \
  -d "$PAYLOAD" \
  $URL \
  -o $RESPONSE_FILE
