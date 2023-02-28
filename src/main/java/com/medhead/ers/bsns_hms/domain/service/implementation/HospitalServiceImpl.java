package com.medhead.ers.bsns_hms.domain.service.implementation;

import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.domain.NoEmergencyBedroomsAvailableInHospitalException;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;
import com.medhead.ers.bsns_hms.domain.exception.HospitalNotFoundException;
import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;
    @Override
    public Hospital saveHospital(Hospital hospital) throws HospitalCodeAlreadyExistException {
        try {
            return hospitalRepository.save(hospital);
        }
        catch (DataIntegrityViolationException exception){
            throw new HospitalCodeAlreadyExistException(hospital.getCode());
        }
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospitalById(UUID uuid) {
        return hospitalRepository.findById(uuid).orElseThrow(() -> new HospitalNotFoundException(uuid));
    }

    @Override
    public EmergencyBedroom bookEmergencyBedroom(UUID hospitalId, UUID emergencyId, UUID patientId) throws NoEmergencyBedroomsAvailableInHospitalException {
        Hospital hospital = getHospitalById(hospitalId);
        EmergencyBedroom emergencyBedroom = hospital.getEmergencyBedrooms().stream().filter(
                eb -> eb.getState() == BedroomState.AVAILABLE
        ).findFirst().orElseThrow(() -> new NoEmergencyBedroomsAvailableInHospitalException(hospitalId, emergencyId));

        emergencyBedroom.book(patientId, emergencyId);
        hospitalRepository.save(hospital);
        return emergencyBedroom;
    }
}
