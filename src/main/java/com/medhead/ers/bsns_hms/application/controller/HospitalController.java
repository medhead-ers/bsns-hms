package com.medhead.ers.bsns_hms.application.controller;

import com.medhead.ers.bsns_hms.domain.entity.Hospital;

import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    Hospital newHospital(@RequestBody Hospital hospital) {
        return hospitalService.saveHospital(hospital);
    }

}
