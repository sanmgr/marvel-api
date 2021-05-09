package com.yapily.project.marvel.exception;

import com.yapily.project.marvel.model.ApiErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ApiExceptionHandler {

    @ExceptionHandler(value = {DataNotFoundApiException.class})
    public ResponseEntity<Object> handleNotFoundException (DataNotFoundApiException ex) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(status.value(), status);
        return new ResponseEntity<>(apiErrorResponse, status);
    }

    @ExceptionHandler(value = {ReadTimeOutException.class})
    public ResponseEntity<Object> handleReadTimeOutException (ReadTimeOutException ex) {
        HttpStatus status = HttpStatus.REQUEST_TIMEOUT;
        ApiErrorResponse apiErrorResponse = new ApiErrorResponse(status.value(), status);
        return new ResponseEntity<>(apiErrorResponse, status);
    }
}
