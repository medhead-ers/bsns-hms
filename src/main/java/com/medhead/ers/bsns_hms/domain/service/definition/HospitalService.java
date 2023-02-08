package com.medhead.ers.bsns_hms.domain.service.definition;

import com.medhead.ers.bsns_hms.domain.entity.Hospital;

import java.util.List;
import java.util.UUID;

public interface HospitalService {
    Hospital saveHospital(Hospital hospital);
    List<Hospital> getAllHospitals();
    Hospital getHospitalById(UUID uuid);
}
