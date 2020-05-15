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

import com.stashkiv.pharmacy.domain.DoctorCertificate;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.DoctorCertificateRepository;
import com.stashkiv.pharmacy.service.dto.DoctorCertificateCriteria;

/**
 * Service for executing complex queries for {@link DoctorCertificate} entities in the database.
 * The main input is a {@link DoctorCertificateCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link DoctorCertificate} or a {@link Page} of {@link DoctorCertificate} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DoctorCertificateQueryService extends QueryService<DoctorCertificate> {

    private final Logger log = LoggerFactory.getLogger(DoctorCertificateQueryService.class);

    private final DoctorCertificateRepository doctorCertificateRepository;

    public DoctorCertificateQueryService(DoctorCertificateRepository doctorCertificateRepository) {
        this.doctorCertificateRepository = doctorCertificateRepository;
    }

    /**
     * Return a {@link List} of {@link DoctorCertificate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<DoctorCertificate> findByCriteria(DoctorCertificateCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<DoctorCertificate> specification = createSpecification(criteria);
        return doctorCertificateRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link DoctorCertificate} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<DoctorCertificate> findByCriteria(DoctorCertificateCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<DoctorCertificate> specification = createSpecification(criteria);
        return doctorCertificateRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DoctorCertificateCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<DoctorCertificate> specification = createSpecification(criteria);
        return doctorCertificateRepository.count(specification);
    }

    /**
     * Function to convert {@link DoctorCertificateCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<DoctorCertificate> createSpecification(DoctorCertificateCriteria criteria) {
        Specification<DoctorCertificate> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), DoctorCertificate_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), DoctorCertificate_.name));
            }
            if (criteria.getReceivedDate() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getReceivedDate(), DoctorCertificate_.receivedDate));
            }
            if (criteria.getDoctorId() != null) {
                specification = specification.and(buildSpecification(criteria.getDoctorId(),
                    root -> root.join(DoctorCertificate_.doctor, JoinType.LEFT).get(Doctor_.id)));
            }
        }
        return specification;
    }
}
