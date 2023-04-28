package com.medhead.ers.bsns_hms.application.messaging.message.factory;


import com.medhead.ers.bsns_hms.application.messaging.message.EmergencyBedroomBookedMessage;
import com.medhead.ers.bsns_hms.application.messaging.message.EmergencyBedroomCreatedMessage;
import com.medhead.ers.bsns_hms.application.messaging.message.HospitalCreatedMessage;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;

public interface MessageFactory {
    static EmergencyBedroomBookedMessage createEmergencyBedroomBookedMessage(EmergencyBedroom emergencyBedroom){
        return new EmergencyBedroomBookedMessage(emergencyBedroom);
    }

    static EmergencyBedroomCreatedMessage createEmergencyBedroomCreatedMessage(Hospital hospital){
        return new EmergencyBedroomCreatedMessage(hospital);
    }

    static HospitalCreatedMessage createHospitalCreatedMessage(Hospital hospital){
        return new HospitalCreatedMessage(hospital);
    }
}
