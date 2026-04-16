package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.FunctionalException;

public class ResourceAlreadyExistsException extends FunctionalException {

    public ResourceAlreadyExistsException(String message) {
        super(message, CustomErrorCodes.Common.RESOURCE_ALREADY_EXISTS);
    }
}
