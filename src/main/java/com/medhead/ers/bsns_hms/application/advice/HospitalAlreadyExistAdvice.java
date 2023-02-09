package com.medhead.ers.bsns_hms.application.advice;

import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HospitalAlreadyExistAdvice {
    @ResponseBody
    @ExceptionHandler(HospitalCodeAlreadyExistException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    String hospitalAlreadyExistAdviceHandler(HospitalCodeAlreadyExistException exception) {
        return exception.getMessage();
    }
}
