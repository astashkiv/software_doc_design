package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.MedicalConditionService;
import com.stashkiv.pharmacy.domain.MedicalCondition;
import com.stashkiv.pharmacy.repository.MedicalConditionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link MedicalCondition}.
 */
@Service
@Transactional
public class MedicalConditionServiceImpl implements MedicalConditionService {

    private final Logger log = LoggerFactory.getLogger(MedicalConditionServiceImpl.class);

    private final MedicalConditionRepository medicalConditionRepository;

    public MedicalConditionServiceImpl(MedicalConditionRepository medicalConditionRepository) {
        this.medicalConditionRepository = medicalConditionRepository;
    }

    /**
     * Save a medicalCondition.
     *
     * @param medicalCondition the entity to save.
     * @return the persisted entity.
     */
    @Override
    public MedicalCondition save(MedicalCondition medicalCondition) {
        log.debug("Request to save MedicalCondition : {}", medicalCondition);
        return medicalConditionRepository.save(medicalCondition);
    }

    /**
     * Get all the medicalConditions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MedicalCondition> findAll(Pageable pageable) {
        log.debug("Request to get all MedicalConditions");
        return medicalConditionRepository.findAll(pageable);
    }

    /**
     * Get one medicalCondition by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<MedicalCondition> findOne(Long id) {
        log.debug("Request to get MedicalCondition : {}", id);
        return medicalConditionRepository.findById(id);
    }

    /**
     * Delete the medicalCondition by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MedicalCondition : {}", id);
        medicalConditionRepository.deleteById(id);
    }
}
