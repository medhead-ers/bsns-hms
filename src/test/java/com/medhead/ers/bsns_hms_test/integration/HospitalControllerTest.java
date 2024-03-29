package com.medhead.ers.bsns_hms_test.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.medhead.ers.bsns_hms.BsnsHmsApplication;
import com.medhead.ers.bsns_hms.data.repository.HospitalRepository;
import com.medhead.ers.bsns_hms.utils.tools.Generator;
import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.UUID;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = BsnsHmsApplication.class)
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
    void test_CreateHospital() throws Exception{
        // Given
        Hospital hospital = buildTestHospitalWithEmergencyBedrooms();

        // When
        mockMvc.perform(post("/hospitals")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(hospital)))

        // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", is(hospital.getName())))
                .andExpect(jsonPath("$.code", is(hospital.getCode())))
                .andExpect(jsonPath("$.address.numberAndStreetName", is(hospital.getAddress().getNumberAndStreetName())))
                .andExpect(jsonPath("$.address.addressComplement", is(hospital.getAddress().getAddressComplement())))
                .andExpect(jsonPath("$.address.city", is(hospital.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postCode", is(hospital.getAddress().getPostCode())))
                .andExpect(jsonPath("$.address.country", is(hospital.getAddress().getCountry())))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(hospital.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(hospital.getGpsCoordinates().getLatitude()), Double.class));
    }

    @Test
    void test_GetOneHospitalById() throws Exception {
        // Given
        Hospital hospital = hospitalRepository.save(buildTestHospitalWithEmergencyBedrooms());
        UUID hospitalId = hospital.getId();
        // When
        mockMvc.perform(get("/hospitals/" + hospitalId))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(hospital.getId().toString())))
                .andExpect(jsonPath("$.name", is(hospital.getName())))
                .andExpect(jsonPath("$.code", is(hospital.getCode())))
                .andExpect(jsonPath("$.address.numberAndStreetName", is(hospital.getAddress().getNumberAndStreetName())))
                .andExpect(jsonPath("$.address.addressComplement", is(hospital.getAddress().getAddressComplement())))
                .andExpect(jsonPath("$.address.city", is(hospital.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postCode", is(hospital.getAddress().getPostCode())))
                .andExpect(jsonPath("$.address.country", is(hospital.getAddress().getCountry())))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(hospital.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(hospital.getGpsCoordinates().getLatitude()), Double.class))
                .andExpect(jsonPath("$.availableEmergencyBedrooms", is(hospital.getAvailableEmergencyBedrooms())))
                .andExpect(jsonPath("$.totalEmergencyBedrooms", is(hospital.getTotalEmergencyBedrooms())));
    }

    @Test
    void test_GetOneHospitalByCode() throws Exception {
        // Given
        Hospital hospital = hospitalRepository.save(buildTestHospitalWithEmergencyBedrooms());
        String hospitalCode = hospital.getCode();
        // When
        mockMvc.perform(get("/hospitals/" + hospitalCode))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(hospital.getId().toString())))
                .andExpect(jsonPath("$.name", is(hospital.getName())))
                .andExpect(jsonPath("$.code", is(hospital.getCode())))
                .andExpect(jsonPath("$.address.numberAndStreetName", is(hospital.getAddress().getNumberAndStreetName())))
                .andExpect(jsonPath("$.address.addressComplement", is(hospital.getAddress().getAddressComplement())))
                .andExpect(jsonPath("$.address.city", is(hospital.getAddress().getCity())))
                .andExpect(jsonPath("$.address.postCode", is(hospital.getAddress().getPostCode())))
                .andExpect(jsonPath("$.address.country", is(hospital.getAddress().getCountry())))
                .andExpect(jsonPath("$.gpsCoordinates.longitude").value(is(hospital.getGpsCoordinates().getLongitude()), Double.class))
                .andExpect(jsonPath("$.gpsCoordinates.latitude").value(is(hospital.getGpsCoordinates().getLatitude()), Double.class))
                .andExpect(jsonPath("$.availableEmergencyBedrooms", is(hospital.getAvailableEmergencyBedrooms())))
                .andExpect(jsonPath("$.totalEmergencyBedrooms", is(hospital.getTotalEmergencyBedrooms())));
    }

    @Test
    void test_FailGetOneHospital () throws Exception {
        // Given
        UUID hospitalId = UUID.randomUUID();
        // When
        mockMvc.perform(get("/hospitals/" + hospitalId))
                // Then
                .andExpect(status().isNotFound());
    }

    @Test
    void test_GetHospitals() throws Exception {
        // Given
        int totalHospitalsInRepository = ((int) hospitalRepository.count());
        // When
        mockMvc.perform(get("/hospitals"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalHospitalsInRepository)));
    }

    @Test
    void test_GetEmergencyBedroomsForHospitalById() throws Exception {
        // Given
        Hospital testHospital = hospitalRepository.save(buildTestHospitalWithEmergencyBedrooms());
        int totalBedroomsInHospital = (int) testHospital.getEmergencyBedrooms().stream().count();
        // When
        mockMvc.perform(get("/hospitals/"+testHospital.getId()+"/emergency-bedrooms"))
        // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalBedroomsInHospital)));
    }

    @ParameterizedTest
    @ValueSource(ints = {1,5,10,15,20})
    void test_CreateEmergencyBedroomsForHospital(int qtyToCreate) throws Exception {
        // Given
        Hospital testHospital = hospitalRepository.save(buildTestHospitalWithoutEmergencyBedrooms());
        // When
        mockMvc.perform(post("/hospitals/"+testHospital.getCode()+"/emergency-bedrooms/"+qtyToCreate))
                // Then
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.size()", is(qtyToCreate)));
    }

    @Test
    void test_GetEmergencyBedroomsForHospitalByCode() throws Exception {
        // Given
        Hospital testHospital = hospitalRepository.save(buildTestHospitalWithEmergencyBedrooms());
        int totalBedroomsInHospital = (int) testHospital.getEmergencyBedrooms().stream().count();
        // When
        mockMvc.perform(get("/hospitals/"+testHospital.getCode()+"/emergency-bedrooms"))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalBedroomsInHospital)));
    }

    @ParameterizedTest
    @ValueSource(strings = {("available"),("occupied"),("unavailable")})
    void test_FilterEmergencyBedroomsForHospital(String bedroomsState) throws Exception {
        // Given
        Hospital testHospital = hospitalRepository.save(buildTestHospitalWithEmergencyBedrooms());
        int totalBedroomsInState = (int) testHospital.getEmergencyBedrooms().stream().filter(
                e-> e.getState() == BedroomState.valueOf(bedroomsState.toUpperCase())
        ).count();

        // When
        mockMvc.perform(get("/hospitals/"+testHospital.getId()+"/emergency-bedrooms?state="+bedroomsState))
                // Then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(totalBedroomsInState)));
    }

    //------------------------------------------------------------------
    private Hospital buildTestHospitalWithoutEmergencyBedrooms() {
        return buildTestHospital();
    }

    private Hospital buildTestHospitalWithEmergencyBedrooms() {
        Hospital testHospital =  buildTestHospital();

        testHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(testHospital.getCode(), 10, BedroomState.AVAILABLE, 1));
        testHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(testHospital.getCode(), 5, BedroomState.UNAVAILABLE, 20));
        testHospital.addEmergencyBedrooms(
                Generator.emergencyBedroomsGenerator(testHospital.getCode(), 4, BedroomState.OCCUPIED, 30));

        return testHospital;
    }

    private Hospital buildTestHospital() {
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
