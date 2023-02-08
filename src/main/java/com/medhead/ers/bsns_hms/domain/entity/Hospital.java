package com.medhead.ers.bsns_hms.domain.entity;

import com.medhead.ers.bsns_hms.domain.valueObject.Address;
import com.medhead.ers.bsns_hms.domain.valueObject.GPSCoordinates;
import com.medhead.ers.bsns_hms.domain.valueObject.MedicalSpeciality;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Collection;
import java.util.EnumSet;
import java.util.Set;
import java.util.UUID;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Hospital
{
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @NonNull
    private String name;
    @NonNull
    @Embedded
    private Address address;
    @NonNull
    @Embedded
    private GPSCoordinates gpsCoordinates;
    @NonNull
    @Enumerated(EnumType.STRING)
    private Collection<MedicalSpeciality> medicalSpecialities;
}
