package fr.gopartner.tregusto.common.exception.core;

import org.springframework.http.HttpStatus;

public class FunctionalException extends ApplicationException {
    public FunctionalException(String message, int customCode) {
        super(message, HttpStatus.BAD_REQUEST, customCode);
    }
}
