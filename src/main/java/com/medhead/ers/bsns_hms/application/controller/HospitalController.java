package com.medhead.ers.bsns_hms.application.controller;

import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;
import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import jakarta.validation.Valid;
import org.apache.commons.lang3.EnumUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    List<Hospital> all() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/hospitals/{identifier}")
    Hospital one(@PathVariable String identifier) {
        try {
            return hospitalService.getHospitalById(UUID.fromString(identifier));
        }
        catch (IllegalArgumentException e){
            return hospitalService.getHospitalByCode(identifier);
        }
    }

    @PostMapping("/hospitals")
    @ResponseStatus(HttpStatus.CREATED)
    Hospital newHospital(@Valid @RequestBody Hospital hospital) throws HospitalCodeAlreadyExistException {
        return hospitalService.saveHospital(hospital);
    }

    @PostMapping("/hospitals/{identifier}/emergency-bedrooms/{quantity}")
    @ResponseStatus(HttpStatus.CREATED)
    List<EmergencyBedroom>  createEmergencyBedrooms(@PathVariable String identifier, @PathVariable Integer quantity) throws HospitalCodeAlreadyExistException {
        Hospital hospital;
        try {
            hospital = hospitalService.getHospitalById(UUID.fromString(identifier));
        }
        catch (IllegalArgumentException e){
            hospital = hospitalService.getHospitalByCode(identifier);
        }
        return hospitalService.addEmergencyBedroomsToHospital(hospital, quantity);
    }

    @GetMapping("/hospitals/{identifier}/emergency-bedrooms")
    List<EmergencyBedroom>  allEmergencyBedroomsOfHospital(@PathVariable String identifier, @RequestParam Optional<String> state) {
        Hospital hospital;
        try {
            hospital = hospitalService.getHospitalById(UUID.fromString(identifier));
        }
        catch (IllegalArgumentException e){
            hospital = hospitalService.getHospitalByCode(identifier);
        }
        List<EmergencyBedroom> emergencyBedroomList = hospital.getEmergencyBedrooms();
        if (state.isPresent() && EnumUtils.isValidEnumIgnoreCase(BedroomState.class, state.get())){
            return emergencyBedroomList.stream().filter(
                    emergencyBedroom -> emergencyBedroom.getState() == BedroomState.valueOf(state.get().toUpperCase())
            ).toList();
        }
        return emergencyBedroomList;
    }
}
