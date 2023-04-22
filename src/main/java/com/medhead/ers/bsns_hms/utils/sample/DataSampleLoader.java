package com.medhead.ers.bsns_hms.utils.sample;

import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.utils.tools.Generator;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.HashSet;

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
                Generator.emergencyBedroomsGenerator(londonRoyalHospital.getCode(), 10, BedroomState.AVAILABLE, londonRoyalHospital.getTotalEmergencyBedrooms() +1));
        londonRoyalHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(londonRoyalHospital.getCode(), 5, BedroomState.UNAVAILABLE, londonRoyalHospital.getTotalEmergencyBedrooms() + 1));

        Hospital bartHealthNHSTrust = Hospital.builder()
                .name("Barts Health NHS Trust")
                .code("HSNHS")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(51.52368647004438)
                        .longitude(-0.1425965117175789).build())
                .address(Address.builder()
                        .numberAndStreetName("A501 Road")
                        .postCode("E1 EBA")
                        .city("London")
                        .country("United Kingdom")
                        .build())
                .medicalSpecialities(new HashSet<>(Arrays.asList(
                        MedicalSpeciality.OPHTHALMOLOGY,
                        MedicalSpeciality.NEUROPATHOLOGY,
                        MedicalSpeciality.DIAGNOSTIC)))
                .build();
        bartHealthNHSTrust.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(bartHealthNHSTrust.getCode(), 5, BedroomState.AVAILABLE, bartHealthNHSTrust.getTotalEmergencyBedrooms() +1));

        Hospital stThomasHospital = Hospital.builder()
                .name("St Thomas' Hospital")
                .code("STTHS")
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(51.52728944582913)
                        .longitude(-0.17310874839511742).build())
                .address(Address.builder()
                        .numberAndStreetName("Westminster Bridge Road")
                        .postCode("SE1 7EH")
                        .city("London")
                        .country("United Kingdom")
                        .build())
                .medicalSpecialities(new HashSet<>(Arrays.asList(
                        MedicalSpeciality.CARDIOLOGY,
                        MedicalSpeciality.IMMUNOLOGY,
                        MedicalSpeciality.OPHTHALMOLOGY)))
                .build();
        stThomasHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(stThomasHospital.getCode(), 5, BedroomState.AVAILABLE, stThomasHospital.getTotalEmergencyBedrooms() + 1));


        return args -> {
            hospitalRepository.save(londonRoyalHospital);
            hospitalRepository.save(bartHealthNHSTrust);
            hospitalRepository.save(stThomasHospital);
        };
    }


}
