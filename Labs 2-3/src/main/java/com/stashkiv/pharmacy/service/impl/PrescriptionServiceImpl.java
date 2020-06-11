package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.PrescriptionService;
import com.stashkiv.pharmacy.domain.Prescription;
import com.stashkiv.pharmacy.repository.PrescriptionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Prescription}.
 */
@Service
@Transactional
public class PrescriptionServiceImpl implements PrescriptionService {

    private final Logger log = LoggerFactory.getLogger(PrescriptionServiceImpl.class);

    private final PrescriptionRepository prescriptionRepository;

    public PrescriptionServiceImpl(PrescriptionRepository prescriptionRepository) {
        this.prescriptionRepository = prescriptionRepository;
    }

    /**
     * Save a prescription.
     *
     * @param prescription the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Prescription save(Prescription prescription) {
        log.debug("Request to save Prescription : {}", prescription);
        return prescriptionRepository.save(prescription);
    }

    /**
     * Get all the prescriptions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Prescription> findAll(Pageable pageable) {
        log.debug("Request to get all Prescriptions");
        return prescriptionRepository.findAll(pageable);
    }

    /**
     * Get all the prescriptions with eager load of many-to-many relationships.
     *
     * @return the list of entities.
     */
    public Page<Prescription> findAllWithEagerRelationships(Pageable pageable) {
        return prescriptionRepository.findAllWithEagerRelationships(pageable);
    }

    /**
     * Get one prescription by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Prescription> findOne(Long id) {
        log.debug("Request to get Prescription : {}", id);
        return prescriptionRepository.findOneWithEagerRelationships(id);
    }

    /**
     * Delete the prescription by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Prescription : {}", id);
        prescriptionRepository.deleteById(id);
    }
}
