package com.medhead.ers.bsns_hms.domain.exception;

import java.util.UUID;

public class NoEmergencyBedroomsAvailableInHospitalException extends Exception{
    public NoEmergencyBedroomsAvailableInHospitalException(UUID hospitalId, UUID emergencyId) {
            super("Could not find any emergency bedrooms available in hospital \"" + hospitalId + "\" for emergency \"" + emergencyId + "\"");
    }
}
