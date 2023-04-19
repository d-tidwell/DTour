package com.nashss.se.musicplaylistservice.exceptions;

public class InvalidBirthdateException extends RuntimeException{
    private static final long serialVersionUID = -7534807710372458869L;

    /**
     * Exception with no message or cause.
     */
    public InvalidBirthdateException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public InvalidBirthdateException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidBirthdateException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidBirthdateException(String message, Throwable cause) {
        super(message, cause);
    }
}



