package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.PatientCertificateService;
import com.stashkiv.pharmacy.domain.PatientCertificate;
import com.stashkiv.pharmacy.repository.PatientCertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link PatientCertificate}.
 */
@Service
@Transactional
public class PatientCertificateServiceImpl implements PatientCertificateService {

    private final Logger log = LoggerFactory.getLogger(PatientCertificateServiceImpl.class);

    private final PatientCertificateRepository patientCertificateRepository;

    public PatientCertificateServiceImpl(PatientCertificateRepository patientCertificateRepository) {
        this.patientCertificateRepository = patientCertificateRepository;
    }

    /**
     * Save a patientCertificate.
     *
     * @param patientCertificate the entity to save.
     * @return the persisted entity.
     */
    @Override
    public PatientCertificate save(PatientCertificate patientCertificate) {
        log.debug("Request to save PatientCertificate : {}", patientCertificate);
        return patientCertificateRepository.save(patientCertificate);
    }

    /**
     * Get all the patientCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PatientCertificate> findAll(Pageable pageable) {
        log.debug("Request to get all PatientCertificates");
        return patientCertificateRepository.findAll(pageable);
    }

    /**
     * Get one patientCertificate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PatientCertificate> findOne(Long id) {
        log.debug("Request to get PatientCertificate : {}", id);
        return patientCertificateRepository.findById(id);
    }

    /**
     * Delete the patientCertificate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PatientCertificate : {}", id);
        patientCertificateRepository.deleteById(id);
    }
}
