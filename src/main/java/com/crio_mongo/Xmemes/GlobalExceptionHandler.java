package com.crio_mongo.Xmemes;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.crio_mongo.Xmemes.exception.MemeAlreadyExistsException;
import com.crio_mongo.Xmemes.exception.MemeConstraintViolationException;
import com.crio_mongo.Xmemes.exception.MemeNotFoundException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MemeAlreadyExistsException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public String handleMemeAlreadyExistsException(MemeAlreadyExistsException e) {
        return "{\"message\": \"" + e.getMessage() + "\" }";
    }

    @ExceptionHandler(MemeNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public String handleMemeNotFoundException(MemeNotFoundException e) {
        return "{\"message\": \"" + e.getMessage() + "\" }";
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleConstraintViolationException(ConstraintViolationException e) {
        List<String> violations = e.getConstraintViolations()
                                    .stream()
                                    .map(ConstraintViolation::getMessageTemplate)
                                    .collect(Collectors.toList());
        return "{\"message\": \"" + joinWithNewLine(violations) + "\" }";
    }

    @ExceptionHandler(MemeConstraintViolationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public String handleMemeConstraintViolationException(MemeConstraintViolationException e) {
        return "{\"message\": \"" + joinWithNewLine(e.getViolations()) + "\" }";
    }

    private String joinWithNewLine(Collection<String> e) {
        StringBuilder sb = new StringBuilder();
        e.forEach(s -> sb.append(s).append("\n"));
        return sb.toString();
    }
}




