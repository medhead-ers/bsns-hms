package com.medhead.ers.bsns_hms.application.messaging.message.factory;


import com.medhead.ers.bsns_hms.application.messaging.message.EmergencyBedroomBookedMessage;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;

public interface MessageFactory {
    static EmergencyBedroomBookedMessage createEmergencyBedroomBookedMessage(EmergencyBedroom emergencyBedroom){
        return new EmergencyBedroomBookedMessage(emergencyBedroom);
    }
}
