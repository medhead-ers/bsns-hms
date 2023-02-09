package com.medhead.ers.bsns_hms.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Hospital {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @NotEmpty
    private String name;
    @NotEmpty
    @Column(unique = true, columnDefinition = "VARCHAR(5)")
    @Pattern(regexp = "^[A-Z0-9]{5}$")
    private String code;
    @NotNull
    @Embedded
    private Address address;
    @NotNull
    @Embedded
    private GPSCoordinates gpsCoordinates;
    @NotNull
    @Enumerated(EnumType.STRING)
    private Set<MedicalSpeciality> medicalSpecialities;
    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    @JsonIgnore
    private List<EmergencyBedroom> bedrooms;

    public void addEmergencyBedrooms(EmergencyBedroom emergencyBedroom) {
        if (this.bedrooms == null)
            this.bedrooms = new ArrayList<>();
        this.bedrooms.add(emergencyBedroom);
    }

    public void addEmergencyBedrooms(List<EmergencyBedroom> emergencyBedroomList) {
        if (this.bedrooms == null)
            this.bedrooms = new ArrayList<>();
        this.bedrooms.addAll(emergencyBedroomList);
    }
}