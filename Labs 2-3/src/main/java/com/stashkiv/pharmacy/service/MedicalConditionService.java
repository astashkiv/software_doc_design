package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.MedicalCondition;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link MedicalCondition}.
 */
public interface MedicalConditionService {

    /**
     * Save a medicalCondition.
     *
     * @param medicalCondition the entity to save.
     * @return the persisted entity.
     */
    MedicalCondition save(MedicalCondition medicalCondition);

    /**
     * Get all the medicalConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<MedicalCondition> findAll(Pageable pageable);

    /**
     * Get the "id" medicalCondition.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<MedicalCondition> findOne(Long id);

    /**
     * Delete the "id" medicalCondition.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
