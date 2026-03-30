package fr.gopartner.tregusto.common.exception.shared;

import fr.gopartner.tregusto.common.exception.core.CustomErrorCodes;
import fr.gopartner.tregusto.common.exception.core.FunctionalException;

public class InvalidCaptchaException extends FunctionalException {
    public InvalidCaptchaException(String message) {
        super(message, CustomErrorCodes.Common.INVALID_CAPTCHA);
    }

    public InvalidCaptchaException() {
        super("Invalid captcha", CustomErrorCodes.Common.INVALID_CAPTCHA);
    }
}
