package fr.gopartner.tregusto.common.exception.core;

import org.springframework.http.HttpStatus;

public class TechnicalException extends ApplicationException {
    public TechnicalException(String message, int customCode) {
        super(message, HttpStatus.INTERNAL_SERVER_ERROR, customCode);
    }
}