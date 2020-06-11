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

import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.DoctorRepository;
import com.stashkiv.pharmacy.service.dto.DoctorCriteria;

/**
 * Service for executing complex queries for {@link Doctor} entities in the database.
 * The main input is a {@link DoctorCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Doctor} or a {@link Page} of {@link Doctor} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class DoctorQueryService extends QueryService<Doctor> {

    private final Logger log = LoggerFactory.getLogger(DoctorQueryService.class);

    private final DoctorRepository doctorRepository;

    public DoctorQueryService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    /**
     * Return a {@link List} of {@link Doctor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Doctor> findByCriteria(DoctorCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Doctor> specification = createSpecification(criteria);
        return doctorRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Doctor} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Doctor> findByCriteria(DoctorCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Doctor> specification = createSpecification(criteria);
        return doctorRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(DoctorCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Doctor> specification = createSpecification(criteria);
        return doctorRepository.count(specification);
    }

    /**
     * Function to convert {@link DoctorCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Doctor> createSpecification(DoctorCriteria criteria) {
        Specification<Doctor> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Doctor_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Doctor_.name));
            }
            if (criteria.getPhone() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPhone(), Doctor_.phone));
            }
            if (criteria.getAddress() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAddress(), Doctor_.address));
            }
            if (criteria.getFeedbacksId() != null) {
                specification = specification.and(buildSpecification(criteria.getFeedbacksId(),
                    root -> root.join(Doctor_.feedbacks, JoinType.LEFT).get(Feedback_.id)));
            }
            if (criteria.getCertificatesId() != null) {
                specification = specification.and(buildSpecification(criteria.getCertificatesId(),
                    root -> root.join(Doctor_.certificates, JoinType.LEFT).get(DoctorCertificate_.id)));
            }
            if (criteria.getPaymentsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPaymentsId(),
                    root -> root.join(Doctor_.payments, JoinType.LEFT).get(Payment_.id)));
            }
            if (criteria.getAdminId() != null) {
                specification = specification.and(buildSpecification(criteria.getAdminId(),
                    root -> root.join(Doctor_.admin, JoinType.LEFT).get(Admin_.id)));
            }
            if (criteria.getDepartmentId() != null) {
                specification = specification.and(buildSpecification(criteria.getDepartmentId(),
                    root -> root.join(Doctor_.department, JoinType.LEFT).get(Department_.id)));
            }
        }
        return specification;
    }
}
