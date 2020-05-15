package com.stashkiv.pharmacy.repository;

import com.stashkiv.pharmacy.domain.DoctorCertificate;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data  repository for the DoctorCertificate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface DoctorCertificateRepository extends JpaRepository<DoctorCertificate, Long>, JpaSpecificationExecutor<DoctorCertificate> {
}
