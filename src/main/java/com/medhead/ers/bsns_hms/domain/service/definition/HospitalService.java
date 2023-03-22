package com.medhead.ers.bsns_hms.domain.service.definition;

import com.medhead.ers.bsns_hms.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.bsns_hms.domain.exception.NoEmergencyBedroomsAvailableInHospitalException;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;

import java.util.List;
import java.util.UUID;

public interface HospitalService {
    Hospital saveHospital(Hospital hospital) throws HospitalCodeAlreadyExistException;
    List<Hospital> getAllHospitals();
    Hospital getHospitalById(UUID uuid);
    Hospital getHospitalByCode(String code);
    EmergencyBedroom bookEmergencyBedroom(UUID hospitalId, UUID emergencyId, UUID patientId) throws NoEmergencyBedroomsAvailableInHospitalException, MessagePublicationFailException;
}
