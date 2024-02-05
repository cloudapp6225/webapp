package com.cloudnativewebapp.webapp.Exception;

public class InvalidEmailAddressException extends Exception {

    public InvalidEmailAddressException() {
        super();
    }

    public InvalidEmailAddressException(String message) {
        super(message);
    }

    public InvalidEmailAddressException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidEmailAddressException(Throwable cause) {
        super(cause);
    }

    protected InvalidEmailAddressException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
