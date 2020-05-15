package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.DoctorCertificateService;
import com.stashkiv.pharmacy.domain.DoctorCertificate;
import com.stashkiv.pharmacy.repository.DoctorCertificateRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link DoctorCertificate}.
 */
@Service
@Transactional
public class DoctorCertificateServiceImpl implements DoctorCertificateService {

    private final Logger log = LoggerFactory.getLogger(DoctorCertificateServiceImpl.class);

    private final DoctorCertificateRepository doctorCertificateRepository;

    public DoctorCertificateServiceImpl(DoctorCertificateRepository doctorCertificateRepository) {
        this.doctorCertificateRepository = doctorCertificateRepository;
    }

    /**
     * Save a doctorCertificate.
     *
     * @param doctorCertificate the entity to save.
     * @return the persisted entity.
     */
    @Override
    public DoctorCertificate save(DoctorCertificate doctorCertificate) {
        log.debug("Request to save DoctorCertificate : {}", doctorCertificate);
        return doctorCertificateRepository.save(doctorCertificate);
    }

    /**
     * Get all the doctorCertificates.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DoctorCertificate> findAll(Pageable pageable) {
        log.debug("Request to get all DoctorCertificates");
        return doctorCertificateRepository.findAll(pageable);
    }

    /**
     * Get one doctorCertificate by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DoctorCertificate> findOne(Long id) {
        log.debug("Request to get DoctorCertificate : {}", id);
        return doctorCertificateRepository.findById(id);
    }

    /**
     * Delete the doctorCertificate by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete DoctorCertificate : {}", id);
        doctorCertificateRepository.deleteById(id);
    }
}
