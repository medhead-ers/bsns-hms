package com.medhead.ers.bsns_hms.application.advice;

import com.medhead.ers.bsns_hms.domain.exception.HospitalNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class HospitalNotFoundAdvice {
    @ResponseBody
    @ExceptionHandler(HospitalNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String hospitalNotFoundAdviceHandler(HospitalNotFoundException exception) {
        return exception.getMessage();
    }
}
