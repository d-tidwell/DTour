package com.nashss.se.musicplaylistservice.exceptions;

public class EventTimeIsInvalidException extends RuntimeException {
        private static final long serialVersionUID = -7534807710372458848L;

        /**
         * Exception with no message or cause.
         */
    public EventTimeIsInvalidException() {
            super();
        }

        /**
         * Exception with a message, but no cause.
         * @param message A descriptive message for this exception.
         */
    public EventTimeIsInvalidException(String message) {
            super(message);
        }

        /**
         * Exception with no message, but with a cause.
         * @param cause The original throwable resulting in this exception.
         */
    public EventTimeIsInvalidException(Throwable cause) {
            super(cause);
        }

        /**
         * Exception with message and cause.
         * @param message A descriptive message for this exception.
         * @param cause The original throwable resulting in this exception.
         */
    public EventTimeIsInvalidException(String message, Throwable cause) {
            super(message, cause);
        }
    }


