package com.gmail.dedmikash.market.service.exception;

public class DataBaseConnectionException extends RuntimeException {
    public DataBaseConnectionException(String message, Throwable cause) {
        super(message, cause);
    }
}
