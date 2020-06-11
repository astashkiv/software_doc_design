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

import com.stashkiv.pharmacy.domain.Query;
import com.stashkiv.pharmacy.domain.*; // for static metamodels
import com.stashkiv.pharmacy.repository.QueryRepository;
import com.stashkiv.pharmacy.service.dto.QueryCriteria;

/**
 * Service for executing complex queries for {@link Query} entities in the database.
 * The main input is a {@link QueryCriteria} which gets converted to {@link Specification},
 * in a way that all the filters must apply.
 * It returns a {@link List} of {@link Query} or a {@link Page} of {@link Query} which fulfills the criteria.
 */
@Service
@Transactional(readOnly = true)
public class QueryQueryService extends QueryService<Query> {

    private final Logger log = LoggerFactory.getLogger(QueryQueryService.class);

    private final QueryRepository queryRepository;

    public QueryQueryService(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    /**
     * Return a {@link List} of {@link Query} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public List<Query> findByCriteria(QueryCriteria criteria) {
        log.debug("find by criteria : {}", criteria);
        final Specification<Query> specification = createSpecification(criteria);
        return queryRepository.findAll(specification);
    }

    /**
     * Return a {@link Page} of {@link Query} which matches the criteria from the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @param page The page, which should be returned.
     * @return the matching entities.
     */
    @Transactional(readOnly = true)
    public Page<Query> findByCriteria(QueryCriteria criteria, Pageable page) {
        log.debug("find by criteria : {}, page: {}", criteria, page);
        final Specification<Query> specification = createSpecification(criteria);
        return queryRepository.findAll(specification, page);
    }

    /**
     * Return the number of matching entities in the database.
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the number of matching entities.
     */
    @Transactional(readOnly = true)
    public long countByCriteria(QueryCriteria criteria) {
        log.debug("count by criteria : {}", criteria);
        final Specification<Query> specification = createSpecification(criteria);
        return queryRepository.count(specification);
    }

    /**
     * Function to convert {@link QueryCriteria} to a {@link Specification}
     * @param criteria The object which holds all the filters, which the entities should match.
     * @return the matching {@link Specification} of the entity.
     */
    protected Specification<Query> createSpecification(QueryCriteria criteria) {
        Specification<Query> specification = Specification.where(null);
        if (criteria != null) {
            if (criteria.getId() != null) {
                specification = specification.and(buildRangeSpecification(criteria.getId(), Query_.id));
            }
            if (criteria.getQuery() != null) {
                specification = specification.and(buildStringSpecification(criteria.getQuery(), Query_.query));
            }
            if (criteria.getAnswer() != null) {
                specification = specification.and(buildStringSpecification(criteria.getAnswer(), Query_.answer));
            }
            if (criteria.getAnsweredById() != null) {
                specification = specification.and(buildSpecification(criteria.getAnsweredById(),
                    root -> root.join(Query_.answeredBy, JoinType.LEFT).get(Doctor_.id)));
            }
            if (criteria.getAskedById() != null) {
                specification = specification.and(buildSpecification(criteria.getAskedById(),
                    root -> root.join(Query_.askedBy, JoinType.LEFT).get(Patient_.id)));
            }
        }
        return specification;
    }
}
