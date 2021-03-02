package uk.co.markzi.spring_validation;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.ServletWebRequest;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@RestControllerAdvice
@RequiredArgsConstructor
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * Default error handling for any other exceptions.
     *
     * @param ex The global exception to be handled
     * @return The error response
     */
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse defaultErrorHandler(Exception ex, ServletWebRequest request) {
        return new ErrorResponse(request.getRequest().getRequestURI(), HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage(), ErrorType.SERVER_ERROR);
    }

    /**
     * Respond with Bad Request 400 and list of validation errors when field validation failed.
     * <p>
     * {@link MethodArgumentNotValidException} thrown.
     *
     * @param ex The global exception to be handled
     * @return The error response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationError(MethodArgumentNotValidException ex, ServletWebRequest request) {

        List<FieldErrorDto> errors = new ArrayList<>();

        for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
            FieldErrorDto fieldErrorDto = new FieldErrorDto(fieldError.getDefaultMessage(), null, fieldError.getField(), fieldError.getRejectedValue() != null ? fieldError.getRejectedValue().toString() : null, null);
            errors.add(fieldErrorDto);
        }

        for (ObjectError objectError : ex.getBindingResult().getGlobalErrors()) {
            FieldErrorDto fieldErrorDto = new FieldErrorDto(objectError.getDefaultMessage(), objectError.getObjectName(), null, null, null);
            errors.add(fieldErrorDto);
        }

        return new ErrorResponse(request.getRequest().getRequestURI(), HttpStatus.BAD_REQUEST.value(), ErrorType.VALIDATION_ERROR, errors);
    }

    /**
     * Respond with Bad Request 400 when failed to parse input message body.
     * <p>
     * {@link HttpMessageNotReadableException} thrown.
     *
     * @param ex The global exception to be handled
     * @return The error response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationError(HttpMessageNotReadableException ex, ServletWebRequest request) {
        return new ErrorResponse(request.getRequest().getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ErrorType.REQUEST_ERROR);
    }

    /**
     * Respond with Bad Request 400 when media type isn't acceptable.
     * <p>
     * {@link HttpMediaTypeNotAcceptableException} thrown.
     *
     * @param ex The global exception to be handled
     * @return The error response
     */
    @ExceptionHandler(HttpMediaTypeNotAcceptableException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processValidationError(HttpMediaTypeNotAcceptableException ex, ServletWebRequest request) {
        return new ErrorResponse(request.getRequest().getRequestURI(), HttpStatus.BAD_REQUEST.value(), ex.getMessage(), ErrorType.REQUEST_ERROR);
    }


    /**
     * Respond with Bad Request 400 and list of validation errors when field validation failed.
     * <p>
     * {@link ConstraintViolationException} thrown.
     *
     * @param ex The global exception to be handled
     * @return The error response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse processConstraintViolationException(ConstraintViolationException ex, ServletWebRequest request) {

        Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();

        List<GlobalExceptionHandler.FieldErrorDto> errors = new ArrayList<>();

        for (ConstraintViolation constraintViolation : constraintViolations) {
            FieldErrorDto fieldErrorDto = new FieldErrorDto(constraintViolation.getMessage(), null, constraintViolation.getPropertyPath().toString(), constraintViolation.getInvalidValue() != null ? constraintViolation.getInvalidValue().toString() : null, null);
            errors.add(fieldErrorDto);
        }
        return new ErrorResponse(request.getRequest().getRequestURI(), HttpStatus.BAD_REQUEST.value(), ErrorType.VALIDATION_ERROR, errors);
    }

    @Getter
    @Setter
    @RequiredArgsConstructor
    @ToString
    public static class FieldErrorDto {

        private final String defaultMessage;
        private final String objectName;
        private final String field;
        private final String rejectedValue;
        private final String code;
    }

    public enum ErrorType {

        VALIDATION_ERROR("REQUEST_VALIDATION_ERROR", "The request is not valid"),

        REQUEST_ERROR("REQUEST_ERROR", "The request is not valid"),

        SERVER_ERROR("SERVER_ERROR", "The server failed to process the request");

        final String type;

        final String message;

        ErrorType(String type, String message) {
            this.type = type;
            this.message = message;
        }

        public String getType() {
            return type;
        }

        public String getMessage() {
            return message;
        }
    }

    /**
     * Provides an error response object where errors are contained within an array.
     */
    @Getter
    @Setter
    public static class ErrorResponse {

        private final String uuid;

        private final String path;

        private final Integer status;

        private final String message;

        private final String type;

        private final String typeDescription;

        private final List<FieldErrorDto> errors;

        public ErrorResponse(String path, Integer status, ErrorType type, List<FieldErrorDto> errors) {
            this.path = path;
            this.status = status;
            this.message = null;
            this.type = type.type;
            this.typeDescription = type.message;
            this.errors = errors;
            this.uuid = UUID.randomUUID().toString();
        }

        public ErrorResponse(String path, Integer status, String message, ErrorType type) {
            this.path = path;
            this.status = status;
            this.message = message;
            this.type = type.type;
            this.typeDescription = type.message;
            this.errors = null;
            this.uuid = UUID.randomUUID().toString();
        }
    }
}
