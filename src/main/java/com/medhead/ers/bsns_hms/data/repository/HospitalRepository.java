package com.medhead.ers.bsns_hms.data.repository;

import com.medhead.ers.bsns_hms.domain.entity.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface HospitalRepository extends JpaRepository<Hospital, UUID> {
    Optional<Hospital> findByCode(String code);
}
