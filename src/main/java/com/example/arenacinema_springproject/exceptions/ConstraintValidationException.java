package com.example.arenacinema_springproject.exceptions;

import javax.validation.ValidationException;

public class ConstraintValidationException extends RuntimeException {
    public ConstraintValidationException(String msg) {
        super(msg);
    }
}
