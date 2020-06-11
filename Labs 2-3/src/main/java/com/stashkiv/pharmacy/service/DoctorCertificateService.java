package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.DoctorCertificate;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link DoctorCertificate}.
 */
public interface DoctorCertificateService {

    /**
     * Save a doctorCertificate.
     *
     * @param doctorCertificate the entity to save.
     * @return the persisted entity.
     */
    DoctorCertificate save(DoctorCertificate doctorCertificate);

    /**
     * Get all the doctorCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<DoctorCertificate> findAll(Pageable pageable);

    /**
     * Get the "id" doctorCertificate.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<DoctorCertificate> findOne(Long id);

    /**
     * Delete the "id" doctorCertificate.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
