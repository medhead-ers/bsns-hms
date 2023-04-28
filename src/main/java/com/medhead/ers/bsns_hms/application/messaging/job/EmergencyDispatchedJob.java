package com.medhead.ers.bsns_hms.application.messaging.job;

import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class EmergencyDispatchedJob extends Job{

    @Autowired
    HospitalService hospitalService;

    @Override
    public void process() throws Exception{
        hospitalService.bookEmergencyBedroom(
                UUID.fromString(this.getEvent().getMetadata().get("hospitalId").toString()),
                UUID.fromString(this.getEvent().getMetadata().get("emergencyId").toString()),
                UUID.fromString(this.getEvent().getMetadata().get("patientId").toString())
        );
    }
}
