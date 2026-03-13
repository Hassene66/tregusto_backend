package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.FunctionalException;

public class ResourceNotFoundException extends FunctionalException {

    public ResourceNotFoundException(String message) {
        super(message, CustomErrorCodes.Common.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException() {
        super("Resource not found", CustomErrorCodes.Common.RESOURCE_NOT_FOUND);
    }
}
