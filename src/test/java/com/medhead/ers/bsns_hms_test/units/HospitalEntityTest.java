package com.medhead.ers.bsns_hms_test.units;

import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class HospitalEntityTest {
    @Test
    void test_AddAvailableEmergencyBedroomsToHospital() {
        // Given
        Hospital hospital = Hospital.builder()
                .code(RandomStringUtils.randomAlphanumeric(5).toUpperCase())
                .build();
        EmergencyBedroom emergencyBedroom = EmergencyBedroom.builder()
                .code(hospital.getCode() + "_EBR_" + 001)
                .state(BedroomState.AVAILABLE)
                .build();
        // When
        hospital.addEmergencyBedrooms(emergencyBedroom);
        // Then
        EmergencyBedroom hospitalEmergencyBedroom = hospital.getEmergencyBedrooms().get(0);
        Assertions.assertEquals(1, hospital.getEmergencyBedrooms().stream().count());
        Assertions.assertEquals(emergencyBedroom.getCode(), hospitalEmergencyBedroom.getCode());
        Assertions.assertEquals(BedroomState.AVAILABLE, hospitalEmergencyBedroom.getState());
    }
}
