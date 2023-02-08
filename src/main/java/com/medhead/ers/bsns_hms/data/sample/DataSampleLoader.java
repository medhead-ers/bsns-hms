package com.medhead.ers.bsns_hms.data.sample;

import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;

@Configuration
public class DataSampleLoader {
    @Bean
    CommandLineRunner initDatabase(HospitalRepository hospitalRepository) {
        Hospital londonRoyalHospital = Hospital.builder()
                .name("Hôpital royal de Londres")
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

        return args -> {
            hospitalRepository.save(londonRoyalHospital);
        };
    }
}
