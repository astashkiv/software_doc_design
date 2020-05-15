package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.PatientCertificate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link PatientCertificate}.
 */
public interface PatientCertificateService {

    /**
     * Save a patientCertificate.
     *
     * @param patientCertificate the entity to save.
     * @return the persisted entity.
     */
    PatientCertificate save(PatientCertificate patientCertificate);

    /**
     * Get all the patientCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PatientCertificate> findAll(Pageable pageable);

    /**
     * Get the "id" patientCertificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PatientCertificate> findOne(Long id);

    /**
     * Delete the "id" patientCertificate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
