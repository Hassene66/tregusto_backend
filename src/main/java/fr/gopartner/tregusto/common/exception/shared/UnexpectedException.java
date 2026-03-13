package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.TechnicalException;

public class UnexpectedException extends TechnicalException {
    public UnexpectedException(String message) {
        super(message, CustomErrorCodes.Common.SOMETHING_WENT_WRONG);
    }
}
