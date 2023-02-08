package com.medhead.ers.bsns_hms.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext
class HospitalControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private HospitalRepository hospitalRepository;

    @Test
    void test_createHospital() throws Exception{
        // Given
        Hospital hospital = buildTestHospital();

        // When
        mockMvc.perform(post("/hospitals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hospital)))

        // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(hospital.getName())))
                .andExpect(jsonPath("$.address.numberAndStreetName", is(hospital.getAddress().getNumberAndStreetName())))
                .andExpect(jsonPath("$.address.addressComplement", is(hospital.getAddress().getAddressComplement())))
                .andExpect(jsonPath("$.address.city", is(hospital.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postCode", is(hospital.getAddress().getPostCode())))
                .andExpect(jsonPath("$.address.country", is(hospital.getAddress().getCountry())))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(hospital.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(hospital.getGpsCoordinates().getLatitude()), Double.class));
    }

    @Test
    void test_GetOneHospital() throws Exception {
        // Given
        Hospital hospital = hospitalRepository.save(buildTestHospital());
        UUID hospitalId = hospital.getId();
        // When
        mockMvc.perform(get("/hospitals/" + hospitalId))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is(hospital.getName())))
                .andExpect(jsonPath("$.address.numberAndStreetName", is(hospital.getAddress().getNumberAndStreetName())))
                .andExpect(jsonPath("$.address.addressComplement", is(hospital.getAddress().getAddressComplement())))
                .andExpect(jsonPath("$.address.city", is(hospital.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postCode", is(hospital.getAddress().getPostCode())))
                .andExpect(jsonPath("$.address.country", is(hospital.getAddress().getCountry())))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(hospital.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(hospital.getGpsCoordinates().getLatitude()), Double.class));
    }

    @Test
    void test_failGetOneHospital () throws Exception {
        // Given
        UUID hospitalId = UUID.randomUUID();
        // When
        mockMvc.perform(get("/hospitals/" + hospitalId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    void test_getHospitals() throws Exception {
        // Given
        int totalHospitalsInRepository = ((int) hospitalRepository.count());
        // When
        mockMvc.perform(get("/hospitals"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalHospitalsInRepository)));
    }

    //------------------------------------------------------------------
    public Hospital buildTestHospital() {
        return Hospital.builder()
                .name("Test Hospital")
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
