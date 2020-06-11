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

import com.stashkiv.pharmacy.domain.MedicalCondition;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.MedicalConditionRepository;
import com.stashkiv.pharmacy.service.dto.MedicalConditionCriteria;

/**
 * Service for executing complex queries for {@link MedicalCondition} entities in the database.
 * The main input is a {@link MedicalConditionCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link MedicalCondition} or a {@link Page} of {@link MedicalCondition} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MedicalConditionQueryService extends QueryService<MedicalCondition> {

    private final Logger log = LoggerFactory.getLogger(MedicalConditionQueryService.class);

    private final MedicalConditionRepository medicalConditionRepository;

    public MedicalConditionQueryService(MedicalConditionRepository medicalConditionRepository) {
        this.medicalConditionRepository = medicalConditionRepository;
    }

    /**
     * Return a {@link List} of {@link MedicalCondition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<MedicalCondition> findByCriteria(MedicalConditionCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<MedicalCondition> specification = createSpecification(criteria);
        return medicalConditionRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link MedicalCondition} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<MedicalCondition> findByCriteria(MedicalConditionCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<MedicalCondition> specification = createSpecification(criteria);
        return medicalConditionRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MedicalConditionCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<MedicalCondition> specification = createSpecification(criteria);
        return medicalConditionRepository.count(specification);
    }

    /**
     * Function to convert {@link MedicalConditionCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<MedicalCondition> createSpecification(MedicalConditionCriteria criteria) {
        Specification<MedicalCondition> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), MedicalCondition_.id));
            }
            if (criteria.getAge() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getAge(), MedicalCondition_.age));
            }
            if (criteria.getHeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getHeight(), MedicalCondition_.height));
            }
            if (criteria.getWeight() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getWeight(), MedicalCondition_.weight));
            }
            if (criteria.getComments() != null) {
                specification = specification.and(buildStringSpecification(criteria.getComments(), MedicalCondition_.comments));
            }
            if (criteria.getTemperature() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getTemperature(), MedicalCondition_.temperature));
            }
            if (criteria.getBloodSugarLevel() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getBloodSugarLevel(), MedicalCondition_.bloodSugarLevel));
            }
            if (criteria.getPressure() != null) {
                specification = specification.and(buildStringSpecification(criteria.getPressure(), MedicalCondition_.pressure));
            }
            if (criteria.getPulse() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getPulse(), MedicalCondition_.pulse));
            }
            if (criteria.getPatientId() != null) {
                specification = specification.and(buildSpecification(criteria.getPatientId(),
                    root -> root.join(MedicalCondition_.patient, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
