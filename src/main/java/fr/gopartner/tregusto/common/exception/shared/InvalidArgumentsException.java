package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.FunctionalException;

public class InvalidArgumentsException extends FunctionalException {
    public InvalidArgumentsException(String message) {
        super(message, CustomErrorCodes.Common.INVALID_ARGUMENTS);
    }

    public InvalidArgumentsException() {
        super("Invalid arguments", CustomErrorCodes.Common.INVALID_ARGUMENTS);
    }

}
