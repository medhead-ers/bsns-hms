package com.medhead.ers.bsns_hms_test.units;

import com.medhead.ers.bsns_hms.BsnsHmsApplication;
import com.medhead.ers.bsns_hms.utils.tools.Generator;
import com.medhead.ers.bsns_hms.domain.exception.NoEmergencyBedroomsAvailableInHospitalException;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.exception.HospitalCodeAlreadyExistException;
import com.medhead.ers.bsns_hms.domain.service.definition.HospitalService;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

@SpringBootTest(classes = BsnsHmsApplication.class)
@DirtiesContext
class HospitalServiceTest {
    @Autowired
    HospitalService hospitalService;
    @Test
    void test_twoHospitalWithSameCodeThrowsHospitalCodeAlreadyExistException() throws HospitalCodeAlreadyExistException {
        // Given
        Hospital firstHospital = buildTestHospital(RandomStringUtils.randomAlphanumeric(5).toUpperCase());
        Hospital secondHospital = buildTestHospital(firstHospital.getCode());
        hospitalService.saveHospital(firstHospital);
        // Then
        Assertions.assertThrows(HospitalCodeAlreadyExistException.class, () -> {
            // When
            hospitalService.saveHospital(secondHospital);
        });
    }

    @Test
    void test_bookEmergencyBedroomThrowsNoEmergencyBedroomsAvailableInHospitalException() throws HospitalCodeAlreadyExistException {
        // Given
        Hospital hospital= hospitalService.saveHospital(buildTestHospitalWithoutEmergencyBedroomsAvailable());
        // Then
        Assertions.assertThrows(NoEmergencyBedroomsAvailableInHospitalException.class, () -> {
            // When
            hospitalService.bookEmergencyBedroom(hospital.getId(), UUID.randomUUID(), UUID.randomUUID());
        });
    }


    private Hospital buildTestHospital(String code) {
        return Hospital.builder()
                .name("Test Hospital")
                .code(code)
                .gpsCoordinates(GPSCoordinates.builder()
                        .latitude(50.51746719004866)
                        .longitude(-0.05958983237779776).build())
                .address(Address.builder()
                        .numberAndStreetName("Some Street")
                        .addressComplement("Some address complement")
                        .postCode("POST CODE")
                        .city("London")
                        .country("United Kingdom")
                        .build())
                .medicalSpecialities(new HashSet<>(Arrays.asList(
                        MedicalSpeciality.CARDIOLOGY)))
                .build();
    }

    private Hospital buildTestHospitalWithoutEmergencyBedroomsAvailable() {
        Hospital testHospital =  buildTestHospital(RandomStringUtils.randomAlphanumeric(5).toUpperCase());

        testHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(testHospital.getCode(), 10, BedroomState.UNAVAILABLE, 1));

        return testHospital;
    }

}
