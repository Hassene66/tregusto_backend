package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.TechnicalException;

public class FileSystemException extends TechnicalException {
    public FileSystemException(String message) {
        super(message, CustomErrorCodes.Common.FileProcessingException);
    }

    public FileSystemException() {
        super("A file system error occurred", CustomErrorCodes.Common.FileProcessingException);
    }
}
