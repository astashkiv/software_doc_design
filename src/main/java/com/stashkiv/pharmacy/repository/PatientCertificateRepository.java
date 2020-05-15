package com.stashkiv.pharmacy.repository;

import com.stashkiv.pharmacy.domain.PatientCertificate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the PatientCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PatientCertificateRepository extends JpaRepository<PatientCertificate, Long>, JpaSpecificationExecutor<PatientCertificate> {
}
