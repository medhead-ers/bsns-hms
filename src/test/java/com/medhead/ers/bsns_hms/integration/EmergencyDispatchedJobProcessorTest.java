package com.medhead.ers.bsns_hms.integration;

import com.medhead.ers.bsns_hms.TestWithRedis;
import com.medhead.ers.bsns_hms.application.messaging.redis.config.MessageListener;
import com.medhead.ers.bsns_hms.data.tools.Generator;
import com.medhead.ers.bsns_hms.domain.entity.EmergencyBedroom;
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
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.system.CapturedOutput;
import org.springframework.boot.test.system.OutputCaptureExtension;
import org.springframework.test.annotation.DirtiesContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static com.medhead.ers.bsns_hms.utils.FileReader.readFile;

@DirtiesContext
@ExtendWith(OutputCaptureExtension.class)
public class EmergencyDispatchedJobProcessorTest extends TestWithRedis {

    private final static String MOCK_MESSAGE_RESOURCES_PATH = "src/test/resources/mock/message/";

    @Autowired
    private MessageListener messageListener;
    @Autowired
    private HospitalService hospitalService;

    @Test
    void test_successBookEmergencyBedroomWhenEmergencyWasDispatched(CapturedOutput output) throws IOException, HospitalCodeAlreadyExistException {
        String eventName = "EmergencyDispatched";
        String jobProcessorName = eventName + "Job";
        UUID testEmergencyId = UUID.randomUUID();
        UUID testPatientId = UUID.randomUUID();

        // Given
        Hospital hospitalInDB = hospitalService.saveHospital(buildTestHospitalWithEmergencyBedroomsAvailable());
        String emergencyDispatchedMessageFromFile = buildEmergencyDispatchedMessage(hospitalInDB.getId(), testEmergencyId, testPatientId);
        // Then
        Assertions.assertDoesNotThrow( ()-> {
            // When
            messageListener.receiveMessage(emergencyDispatchedMessageFromFile);
        });
        EmergencyBedroom emergencyBedroom = hospitalService.getHospitalById(hospitalInDB.getId())
                .getEmergencyBedrooms().stream().filter(
                        eb -> eb.getEmergencyId().equals(testEmergencyId) && eb.getPatientId().equals(testPatientId)
                ).findFirst().orElseThrow();

        // ... Then
        Assertions.assertTrue(output.getAll().contains("Message reçu de type : " + eventName));
        Assertions.assertTrue(output.getAll().contains("Traitement de l'événement de type : " + eventName + ". Job processor : "+jobProcessorName));
        Assertions.assertTrue(output.getAll().contains("Fin de traitement de l'événement de type : " + eventName +" - Événement traité avec succès."));
        Assertions.assertEquals(BedroomState.OCCUPIED, emergencyBedroom.getState());
    }

    //------------------------------------------------------------------

    private String buildEmergencyDispatchedMessage(UUID hospitalId, UUID emergencyId, UUID patientId) throws IOException {
        return readFile(MOCK_MESSAGE_RESOURCES_PATH + "emergency_dispatched.message")
                .replace("#HOSPITAL_UUID#", hospitalId.toString())
                .replace("#EMERGENCY_UUID#", emergencyId.toString())
                .replace("#PATIENT_UUID#", patientId.toString());
    }

    private Hospital buildTestHospitalWithEmergencyBedroomsAvailable() {
        Hospital testHospital =  buildTestHospital();

        testHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(testHospital.getCode(), 10, BedroomState.AVAILABLE, 1));

        return testHospital;
    }

    private Hospital buildTestHospital(){
        return Hospital.builder()
                .name("Test Hospital")
                .code(RandomStringUtils.randomAlphanumeric(5).toUpperCase())
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
                        MedicalSpeciality.CARDIOLOGY,
                        MedicalSpeciality.OPHTHALMOLOGY)))
                .build();
    }
}
