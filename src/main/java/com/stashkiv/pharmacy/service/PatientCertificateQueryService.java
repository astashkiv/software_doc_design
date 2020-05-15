package com.stashkiv.pharmacy.service;

import java.util.List;

import javax.persistence.criteria.JoinType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.github.jhipster.service.QueryService;

import com.stashkiv.pharmacy.domain.PatientCertificate;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.PatientCertificateRepository;
import com.stashkiv.pharmacy.service.dto.PatientCertificateCriteria;

/**
 * Service for executing complex queries for {@link PatientCertificate} entities in the database.
 * The main input is a {@link PatientCertificateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link PatientCertificate} or a {@link Page} of {@link PatientCertificate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientCertificateQueryService extends QueryService<PatientCertificate> {

    private final Logger log = LoggerFactory.getLogger(PatientCertificateQueryService.class);

    private final PatientCertificateRepository patientCertificateRepository;

    public PatientCertificateQueryService(PatientCertificateRepository patientCertificateRepository) {
        this.patientCertificateRepository = patientCertificateRepository;
    }

    /**
     * Return a {@link List} of {@link PatientCertificate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<PatientCertificate> findByCriteria(PatientCertificateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<PatientCertificate> specification = createSpecification(criteria);
        return patientCertificateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link PatientCertificate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<PatientCertificate> findByCriteria(PatientCertificateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<PatientCertificate> specification = createSpecification(criteria);
        return patientCertificateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientCertificateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<PatientCertificate> specification = createSpecification(criteria);
        return patientCertificateRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientCertificateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<PatientCertificate> createSpecification(PatientCertificateCriteria criteria) {
        Specification<PatientCertificate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), PatientCertificate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), PatientCertificate_.name));
            }
            if (criteria.getReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedDate(), PatientCertificate_.receivedDate));
            }
            if (criteria.getDoctorId() != null) {
                specification = specification.and(buildSpecification(criteria.getDoctorId(),
                    root -> root.join(PatientCertificate_.doctor, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
