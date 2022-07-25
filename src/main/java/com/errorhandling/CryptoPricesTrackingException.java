package com.errorhandling;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CryptoPricesTrackingException extends RuntimeException {
    private final String message;

    public CryptoPricesTrackingException(String message) {
        super(message);
        this.message = message;
    }
}
