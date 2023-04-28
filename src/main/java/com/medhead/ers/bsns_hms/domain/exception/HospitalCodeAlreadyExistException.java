package com.medhead.ers.bsns_hms.domain.exception;

public class HospitalCodeAlreadyExistException extends Exception{
    public HospitalCodeAlreadyExistException(String hospitalCode){
        super("Hospital with code \""+hospitalCode+"\" already exist.");
    }
}
