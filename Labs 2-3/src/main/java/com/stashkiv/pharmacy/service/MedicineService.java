package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.Medicine;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Medicine}.
 */
public interface MedicineService {

    /**
     * Save a medicine.
     *
     * @param medicine the entity to save.
     * @return the persisted entity.
     */
    Medicine save(Medicine medicine);

    /**
     * Get all the medicines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Medicine> findAll(Pageable pageable);

    /**
     * Get all the medicines with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    Page<Medicine> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" medicine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Medicine> findOne(Long id);

    /**
     * Delete the "id" medicine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
