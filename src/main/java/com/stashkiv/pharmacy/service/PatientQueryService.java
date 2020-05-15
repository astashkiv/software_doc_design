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

import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.PatientRepository;
import com.stashkiv.pharmacy.service.dto.PatientCriteria;

/**
 * Service for executing complex queries for {@link Patient} entities in the database.
 * The main input is a {@link PatientCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Patient} or a {@link Page} of {@link Patient} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class PatientQueryService extends QueryService<Patient> {

    private final Logger log = LoggerFactory.getLogger(PatientQueryService.class);

    private final PatientRepository patientRepository;

    public PatientQueryService(PatientRepository patientRepository) {
        this.patientRepository = patientRepository;
    }

    /**
     * Return a {@link List} of {@link Patient} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Patient> findByCriteria(PatientCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Patient} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Patient> findByCriteria(PatientCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(PatientCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Patient> specification = createSpecification(criteria);
        return patientRepository.count(specification);
    }

    /**
     * Function to convert {@link PatientCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Patient> createSpecification(PatientCriteria criteria) {
        Specification<Patient> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Patient_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Patient_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Patient_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Patient_.address));
            }
            if (criteria.getFeedbacksId() != null) {
                specification = specification.and(buildSpecification(criteria.getFeedbacksId(),
                    root -> root.join(Patient_.feedbacks, JoinType.LEFT).get(Feedback_.id)));
            }
            if (criteria.getPrescriptionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrescriptionsId(),
                    root -> root.join(Patient_.prescriptions, JoinType.LEFT).get(Prescription_.id)));
            }
            if (criteria.getCertificatesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCertificatesId(),
                    root -> root.join(Patient_.certificates, JoinType.LEFT).get(PatientCertificate_.id)));
            }
            if (criteria.getQueriesId() != null) {
                specification = specification.and(buildSpecification(criteria.getQueriesId(),
                    root -> root.join(Patient_.queries, JoinType.LEFT).get(Query_.id)));
            }
            if (criteria.getBookingsId() != null) {
                specification = specification.and(buildSpecification(criteria.getBookingsId(),
                    root -> root.join(Patient_.bookings, JoinType.LEFT).get(Booking_.id)));
            }
            if (criteria.getConditionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getConditionsId(),
                    root -> root.join(Patient_.conditions, JoinType.LEFT).get(MedicalCondition_.id)));
            }
            if (criteria.getPaymentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsId(),
                    root -> root.join(Patient_.payments, JoinType.LEFT).get(Payment_.id)));
            }
        }
        return specification;
    }
}
