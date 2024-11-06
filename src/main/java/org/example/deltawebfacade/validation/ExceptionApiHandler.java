package org.example.deltawebfacade.validation;

import org.apache.tomcat.websocket.AuthenticationException;
import org.example.deltawebfacade.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestControllerAdvice
public class ExceptionApiHandler {

    @ExceptionHandler(NotFoundByIdException.class)
    public ResponseEntity<ErrorResponse> notFoundByIdException(NotFoundByIdException exception) {
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NotFoundByEmailException.class)
    public ResponseEntity<ErrorResponse> notFoundByEmailException(NotFoundByEmailException exception) {
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistByEmailException.class)
    public ResponseEntity<ErrorResponse> existByEmailException(ExistByEmailException exception) {
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ErrorResponse> loginException(LoginException exception) {
        return createResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponse> authenticationException(AuthenticationException exception) {
        return createResponse(exception, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FileNullException.class)
    public ResponseEntity<ErrorResponse> fileNullException(FileNullException exception) {
        return createResponse(exception, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(NotFoundByPageException.class)
    public ResponseEntity<ErrorResponse> notFoundByPageException(NotFoundByPageException exception) {
        return createResponse(exception, HttpStatus.NOT_FOUND);
    }


    private ResponseEntity<ErrorResponse> createResponse(Exception exception, HttpStatus httpStatus) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ErrorResponse(exception.getMessage(),
                        new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime())));
    }

}