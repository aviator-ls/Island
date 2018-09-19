package com.aviator.island.exception;

/**
 * Created by aviator_ls on 2018/8/20.
 */
public class HelloException extends RuntimeException {
    public HelloException() {
    }

    public HelloException(String message) {
        super(message);
    }

    public HelloException(Throwable cause) {
        super(cause);
    }

    public HelloException(String message, Throwable cause) {
        super(message, cause);
    }
}
