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
public class HospitalController {
    @Autowired
    private HospitalService hospitalService;

    @GetMapping("/hospitals")
    List<Hospital> all() {
        return hospitalService.getAllHospitals();
    }

    @GetMapping("/hospitals/{uuid}")
    Hospital one(@PathVariable UUID uuid) {
        return hospitalService.getHospitalById(uuid);
    }

    @PostMapping("/hospitals")
    @ResponseStatus(HttpStatus.CREATED)
    Hospital newHospital(@Valid @RequestBody Hospital hospital) throws HospitalCodeAlreadyExistException {
        return hospitalService.saveHospital(hospital);
    }

    @GetMapping("/hospitals/{uuid}/emergency-bedrooms")
    List<EmergencyBedroom>  allEmergencyBedroomsOfHospital(@PathVariable UUID uuid, @RequestParam Optional<String> state) {
        List<EmergencyBedroom> emergencyBedroomList = hospitalService.getHospitalById(uuid).getEmergencyBedrooms();
        if (state.isPresent() && EnumUtils.isValidEnumIgnoreCase(BedroomState.class, state.get())){
            return emergencyBedroomList.stream().filter(
                    emergencyBedroom -> emergencyBedroom.getState() == BedroomState.valueOf(state.get().toUpperCase())
            ).toList();
        }
        return emergencyBedroomList;
    }
}
