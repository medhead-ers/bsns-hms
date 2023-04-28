package com.medhead.ers.bsns_hms.domain.service.implementation;

import com.medhead.ers.bsns_hms.application.messaging.exception.MessagePublicationFailException;
import com.medhead.ers.bsns_hms.application.messaging.message.factory.MessageFactory;
import com.medhead.ers.bsns_hms.application.messaging.service.definition.MessagePublisher;
import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.domain.exception.NoEmergencyBedroomsAvailableInHospitalException;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;
import com.medhead.ers.bsns_hms.domain.exception.HospitalNotFoundException;
import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.utils.tools.Generator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class HospitalServiceImpl implements HospitalService {
    @Autowired
    HospitalRepository hospitalRepository;
    @Autowired
    MessagePublisher messagePublisher;

    @Override
    public Hospital saveHospital(Hospital hospital) throws HospitalCodeAlreadyExistException {
        try {
            hospital =  hospitalRepository.save(hospital);
            messagePublisher.publish(MessageFactory.createHospitalCreatedMessage(hospital));
            return hospital;
        }
        catch (DataIntegrityViolationException exception){
            throw new HospitalCodeAlreadyExistException(hospital.getCode());
        } catch (MessagePublicationFailException e) {
            // Pour le POC, aucune gestion d'erreur sur la publication de message ne sera implémentée.
            return hospital;
        }
    }

    @Override
    public List<Hospital> getAllHospitals() {
        return hospitalRepository.findAll();
    }

    @Override
    public Hospital getHospitalById(UUID uuid) {
        return hospitalRepository.findById(uuid).orElseThrow(() -> new HospitalNotFoundException(uuid.toString()));
    }

    @Override
    public Hospital getHospitalByCode(String code) {
        return hospitalRepository.findByCode(code).orElseThrow(() -> new HospitalNotFoundException(code));
    }

    @Override
    public List<EmergencyBedroom> addEmergencyBedroomsToHospital(Hospital hospital, int quantity) {
        int startIndex = hospital.getTotalEmergencyBedrooms() + 1;
        hospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(hospital.getCode(), quantity, BedroomState.AVAILABLE, startIndex));
        hospital = hospitalRepository.save(hospital);
        try {
            messagePublisher.publish(MessageFactory.createEmergencyBedroomCreatedMessage(hospital));
            return hospital.getEmergencyBedrooms();
        }
        // Pour le POC, aucune gestion d'erreur sur la publication de message ne sera implémentée.
        catch (MessagePublicationFailException e){return hospital.getEmergencyBedrooms();}
    }

    @Override
    public EmergencyBedroom bookEmergencyBedroom(UUID hospitalId, UUID emergencyId, UUID patientId) throws NoEmergencyBedroomsAvailableInHospitalException {
        Hospital hospital = getHospitalById(hospitalId);
        EmergencyBedroom emergencyBedroom = hospital.getEmergencyBedrooms().stream().filter(
                eb -> eb.getState() == BedroomState.AVAILABLE
        ).findFirst().orElseThrow(() -> new NoEmergencyBedroomsAvailableInHospitalException(hospitalId, emergencyId));

        emergencyBedroom.book(patientId, emergencyId);
        hospitalRepository.save(hospital);
        try {
            messagePublisher.publish(MessageFactory.createEmergencyBedroomBookedMessage(emergencyBedroom));
            return emergencyBedroom;
        }
        // Pour le POC, aucune gestion d'erreur sur la publication de message ne sera implémentée.
        catch (MessagePublicationFailException e){return emergencyBedroom;}
    }
}
