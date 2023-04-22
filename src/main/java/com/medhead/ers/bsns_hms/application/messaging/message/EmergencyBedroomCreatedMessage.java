package com.medhead.ers.bsns_hms.application.messaging.message;

import com.medhead.ers.bsns_hms.application.messaging.event.AvailableEvent;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import org.json.JSONObject;

public class EmergencyBedroomCreatedMessage extends Message{
    public EmergencyBedroomCreatedMessage(Hospital hospital){
        this.eventType = AvailableEvent.EmergencyBedroomsCreated;
        this.setMetadata(
                new JSONObject().put("emergencyBedrooms", hospital.getEmergencyBedrooms()).toMap()
        );
    }
}
