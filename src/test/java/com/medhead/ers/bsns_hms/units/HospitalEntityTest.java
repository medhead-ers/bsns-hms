package com.medhead.ers.bsns_hms.units;

import com.medhead.ers.bsns_hms.data.tools.Generator;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;

public class HospitalEntityTest {
    @Test
    public void test_AddAvailableEmergencyBedroomsToHospital() {
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
        EmergencyBedroom hospitalEmergencyBedroom = hospital.getBedrooms().get(0);
        Assertions.assertEquals(1, hospital.getBedrooms().stream().count());
        Assertions.assertEquals(emergencyBedroom.getCode(), hospitalEmergencyBedroom.getCode());
        Assertions.assertEquals(BedroomState.AVAILABLE, hospitalEmergencyBedroom.getState());
    }
}
