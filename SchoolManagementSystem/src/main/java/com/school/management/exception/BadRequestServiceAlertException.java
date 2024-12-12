package com.school.management.exception;

public class BadRequestServiceAlertException extends RuntimeException
{
    public BadRequestServiceAlertException(final String message) {
        super(message);
    }
}
