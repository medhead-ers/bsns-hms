package com.medhead.ers.bsns_hms.domain.exception;

public class HospitalNotFoundException extends  RuntimeException{
    public HospitalNotFoundException(String identifier) {
        super("Could not find hospital with identifier : " +identifier);
    }
}
