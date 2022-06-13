package com.mendel.transaction.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.mendel.transaction.exception.BusinessException;
import com.mendel.transaction.exception.ErrorResponse;

@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(value = {BusinessException.class})
    protected ResponseEntity<ErrorResponse> handleBaseBusinessException(final BusinessException ex) {

        final ServletRequestAttributes attr =
                (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes();
        final HttpServletRequest requestFromContext = attr.getRequest();

        final ErrorResponse errorResponse = ex.toApiErrorResponse();
        errorResponse.setPath(requestFromContext.getRequestURI());

        return new ResponseEntity<>(errorResponse, new HttpHeaders(), ex.getStatusCode());
    }
}
