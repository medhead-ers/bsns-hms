package com.medhead.ers.bsns_hms.domain.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medhead.ers.bsns_hms.domain.valueObject.BedroomState;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class EmergencyBedroom {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "VARCHAR(36)")
    private UUID id;
    @NotNull
    @Setter(AccessLevel.NONE)
    @Enumerated(EnumType.STRING)
    private BedroomState state;
    @NotEmpty
    @Column(unique = true, columnDefinition = "VARCHAR(13)")
    @Pattern(regexp = "^[A-Z0-9]{5}_EBR_[0-9]{3}$")
    private String code;
    @Nullable
    @Setter(AccessLevel.NONE)
    private UUID patientId;
    @Nullable
    @Setter(AccessLevel.NONE)
    private UUID emergencyId;
    @ManyToOne
    @JsonIgnore
    private Hospital hospital;

    public void book(UUID patientId, UUID emergencyId) {
        this.state = BedroomState.OCCUPIED;
        this.patientId = patientId;
        this.emergencyId = emergencyId;
    }
}
