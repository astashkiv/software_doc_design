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

import com.stashkiv.pharmacy.domain.Medicine;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.MedicineRepository;
import com.stashkiv.pharmacy.service.dto.MedicineCriteria;

/**
 * Service for executing complex queries for {@link Medicine} entities in the database.
 * The main input is a {@link MedicineCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Medicine} or a {@link Page} of {@link Medicine} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class MedicineQueryService extends QueryService<Medicine> {

    private final Logger log = LoggerFactory.getLogger(MedicineQueryService.class);

    private final MedicineRepository medicineRepository;

    public MedicineQueryService(MedicineRepository medicineRepository) {
        this.medicineRepository = medicineRepository;
    }

    /**
     * Return a {@link List} of {@link Medicine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Medicine> findByCriteria(MedicineCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Medicine> specification = createSpecification(criteria);
        return medicineRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Medicine} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Medicine> findByCriteria(MedicineCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Medicine> specification = createSpecification(criteria);
        return medicineRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(MedicineCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Medicine> specification = createSpecification(criteria);
        return medicineRepository.count(specification);
    }

    /**
     * Function to convert {@link MedicineCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Medicine> createSpecification(MedicineCriteria criteria) {
        Specification<Medicine> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Medicine_.id));
            }
            if (criteria.getName() != null) {
                specification = specification.and(buildStringSpecification(criteria.getName(), Medicine_.name));
            }
            if (criteria.getInstruction() != null) {
                specification = specification.and(buildStringSpecification(criteria.getInstruction(), Medicine_.instruction));
            }
            if (criteria.getLanguagesInId() != null) {
                specification = specification.and(buildSpecification(criteria.getLanguagesInId(),
                    root -> root.join(Medicine_.languagesIns, JoinType.LEFT).get(Language_.id)));
            }
            if (criteria.getPrescriptionsId() != null) {
                specification = specification.and(buildSpecification(criteria.getPrescriptionsId(),
                    root -> root.join(Medicine_.prescriptions, JoinType.LEFT).get(Prescription_.id)));
            }
        }
        return specification;
    }
}
