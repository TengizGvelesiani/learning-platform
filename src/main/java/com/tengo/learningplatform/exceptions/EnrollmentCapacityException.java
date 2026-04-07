package com.tengo.learningplatform.exceptions;


public class EnrollmentCapacityException extends Exception {

    public EnrollmentCapacityException(String message) {
        super(message);
    }

    public EnrollmentCapacityException(String message, Throwable cause) {
        super(message, cause);
    }
}
