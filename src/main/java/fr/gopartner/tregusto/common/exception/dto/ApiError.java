package fr.gopartner.tregusto.common.exception.dto;

import org.springframework.http.HttpStatus;

import java.time.OffsetDateTime;

public record ApiError(String message, HttpStatus status, int code, OffsetDateTime timestamp) {
}