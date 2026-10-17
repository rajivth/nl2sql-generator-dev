package com.nl2sql.exception;

public class ResourceNotFoundException extends RuntimeException {
    String message;
    Throwable cause;

    public ResourceNotFoundException(String message, Throwable cause) {
      this.message = message;
      this. cause = cause;
    }
}
