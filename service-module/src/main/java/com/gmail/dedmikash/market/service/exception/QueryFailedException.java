package com.gmail.dedmikash.market.service.exception;

public class QueryFailedException extends RuntimeException {
    public QueryFailedException(String message, Throwable cause) {
        super(message, cause);
    }
}
