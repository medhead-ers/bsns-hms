package com.medhead.ers.bsns_hms.data.sample;

import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.data.tools.Generator;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.apache.commons.lang3.StringUtils;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Configuration
public class DataSampleLoader {
    @Bean
    CommandLineRunner initDatabase(HospitalRepository hospitalRepository) {
        Hospital londonRoyalHospital = Hospital.builder()
                .name("Hôpital royal de Londres")
                .code("HLRWR")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(51.51746719004866)
                        .longitude(-0.05958983237779776).build())
                .address(Address.builder()
                        .numberAndStreetName("Whitechapel Rd")
                        .postCode("E1 1FR")
                        .city("London")
                        .country("United Kingdom")
                        .build())
                .medicalSpecialities(new HashSet<>(Arrays.asList(
                        MedicalSpeciality.CARDIOLOGY,
                        MedicalSpeciality.OPHTHALMOLOGY)))
                .build();
        londonRoyalHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(londonRoyalHospital.getCode(), 10, BedroomState.AVAILABLE, 1));
        londonRoyalHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(londonRoyalHospital.getCode(), 5, BedroomState.UNAVAILABLE, 20));

        return args -> {
            hospitalRepository.save(londonRoyalHospital);
        };
    }


}
