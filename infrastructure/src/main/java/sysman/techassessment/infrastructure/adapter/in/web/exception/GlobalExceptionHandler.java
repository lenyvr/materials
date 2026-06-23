package sysman.techassessment.infrastructure.adapter.in.web.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import sysman.techassessment.domain.exception.BusinessDomainException;
import sysman.techassessment.domain.exception.BuyDateOlderThanSoldDate;
import sysman.techassessment.domain.exception.MaterialAlreadyExists;
import sysman.techassessment.infrastructure.adapter.in.web.dto.ErrorResponseDto;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponseDto handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = ex.getBindingResult().getAllErrors().stream()
                .map(error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    return fieldName + ": " + errorMessage;
                })
                .collect(Collectors.joining(", "));
        log.error("Validation error: {} ",message, ex);
        return new ErrorResponseDto(HttpStatus.BAD_REQUEST.name(), message);
    }

    @ExceptionHandler(BusinessDomainException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponseDto handleDataException(BusinessDomainException ex) {
        log.error("Data validation exception: {}", ex.getMessage(), ex);
        return new ErrorResponseDto(HttpStatus.CONFLICT.name(), ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponseDto handleAllExceptions(Exception ex) {
        log.error("Unknown Exception: {} ", ex.getMessage(), ex);
        return new ErrorResponseDto(HttpStatus.INTERNAL_SERVER_ERROR.name(), "An unexpected error occurred: ");
    }
}
