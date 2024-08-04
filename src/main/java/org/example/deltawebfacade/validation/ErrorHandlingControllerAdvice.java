package org.example.deltawebfacade.validation;

import liquibase.exception.LockException;
import org.example.deltawebfacade.exceptions.ExistByEmailException;
import org.example.deltawebfacade.exceptions.LoginException;
import org.example.deltawebfacade.exceptions.NotFoundByEmailException;
import org.example.deltawebfacade.exceptions.NotFoundByIdException;
import org.springframework.http.HttpStatus;
import org.apache.tomcat.websocket.AuthenticationException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

@ControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(NotFoundByIdException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotFoundByIdException(
            NotFoundByIdException e) {
        return new ValidationErrorResponse(getViolations(e));
    }

    @ExceptionHandler(NotFoundByEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentNotFoundByEmailException(
            NotFoundByEmailException e) {
        return new ValidationErrorResponse(getViolations(e));
    }

    @ExceptionHandler(ExistByEmailException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentExistByEmailException(
            ExistByEmailException e) {
        return new ValidationErrorResponse(getViolations(e));
    }

    @ExceptionHandler(LoginException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ValidationErrorResponse onMethodArgumentLoginException(
            LoginException e) {
        return new ValidationErrorResponse(getViolations(e));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ResponseBody
    public ValidationErrorResponse handleAuthenticationException(AuthenticationException e){
        return new ValidationErrorResponse(getViolations(e));
    }


    private List<Violation> getViolations(Exception e) {
        final List<Violation> violations = new ArrayList<>();
        violations.add(new Violation(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").format(Calendar.getInstance().getTime()),
                e.getMessage()));
        return violations;
    }

}