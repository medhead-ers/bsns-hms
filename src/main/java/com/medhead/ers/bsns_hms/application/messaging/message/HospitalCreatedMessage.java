package com.medhead.ers.bsns_hms.application.messaging.message;

import com.medhead.ers.bsns_hms.application.messaging.event.AvailableEvent;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import org.json.JSONObject;

public class HospitalCreatedMessage extends Message{
    public HospitalCreatedMessage(Hospital hospital){
        this.eventType = AvailableEvent.HospitalCreated;
        this.setMetadata(
                new JSONObject().put("hospital", hospital).toMap()
        );
    }
}
