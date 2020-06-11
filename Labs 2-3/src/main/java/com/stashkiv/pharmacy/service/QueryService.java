package com.stashkiv.pharmacy.service;

import com.stashkiv.pharmacy.domain.Query;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing {@link Query}.
 */
public interface QueryService {

    /**
     * Save a query.
     *
     * @param query the entity to save.
     * @return the persisted entity.
     */
    Query save(Query query);

    /**
     * Get all the queries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Query> findAll(Pageable pageable);

    /**
     * Get the "id" query.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Query> findOne(Long id);

    /**
     * Delete the "id" query.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
