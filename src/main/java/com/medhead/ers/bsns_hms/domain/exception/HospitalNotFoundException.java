package com.medhead.ers.bsns_hms.domain.exception;

import java.util.UUID;

public class HospitalNotFoundException extends  RuntimeException{
    public HospitalNotFoundException(UUID uuid) {
        super("Could not find hospital " + uuid);
    }
}
