package fr.gopartner.tregusto.common.exception.core;

import fr.gopartner.tregusto.common.exception.dto.ApiError;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

@Getter
public abstract class ApplicationException extends RuntimeException {

    private final HttpStatus status;
    private final int customCode;
    private final OffsetDateTime timestamp;


    protected ApplicationException(String message, HttpStatus status, int customCode) {
        super(message);
        this.status = status;
        this.customCode = customCode;
        this.timestamp = OffsetDateTime.now();
    }

    public ApiError toApiError() {
        return new ApiError(getMessage(), status, customCode, timestamp);
    }
}
