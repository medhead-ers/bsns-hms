package com.medhead.ers.bsns_hms.application.messaging.message;

import com.medhead.ers.bsns_hms.application.messaging.event.AvailableEvent;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import org.json.JSONObject;

public class EmergencyBedroomBookedMessage extends Message{
    public EmergencyBedroomBookedMessage(EmergencyBedroom emergencyBedroom){
        this.eventType = AvailableEvent.EmergencyBedroomBooked;
        this.setMetadata(
                new JSONObject().put("emergencyBedroom", emergencyBedroom).toMap()
        );
    }
}
