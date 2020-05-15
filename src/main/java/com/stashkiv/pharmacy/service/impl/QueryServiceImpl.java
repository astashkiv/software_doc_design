package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.QueryService;
import com.stashkiv.pharmacy.domain.Query;
import com.stashkiv.pharmacy.repository.QueryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Query}.
 */
@Service
@Transactional
public class QueryServiceImpl implements QueryService {

    private final Logger log = LoggerFactory.getLogger(QueryServiceImpl.class);

    private final QueryRepository queryRepository;

    public QueryServiceImpl(QueryRepository queryRepository) {
        this.queryRepository = queryRepository;
    }

    /**
     * Save a query.
     *
     * @param query the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Query save(Query query) {
        log.debug("Request to save Query : {}", query);
        return queryRepository.save(query);
    }

    /**
     * Get all the queries.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Query> findAll(Pageable pageable) {
        log.debug("Request to get all Queries");
        return queryRepository.findAll(pageable);
    }

    /**
     * Get one query by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Query> findOne(Long id) {
        log.debug("Request to get Query : {}", id);
        return queryRepository.findById(id);
    }

    /**
     * Delete the query by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Query : {}", id);
        queryRepository.deleteById(id);
    }
}
