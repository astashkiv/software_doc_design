package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.Prescription;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Prescription}.
 */
public interface PrescriptionService {

    /**
     * Save a prescription.
     *
     * @param prescription the entity to save.
     * @return the persisted entity.
     */
    Prescription save(Prescription prescription);

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Prescription> findAll(Pageable pageable);

    /**
     * Get all the prescriptions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Prescription> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" prescription.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Prescription> findOne(Long id);

    /**
     * Delete the "id" prescription.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
