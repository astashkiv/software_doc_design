package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.domain.Query;
import com.stashkiv.pharmacy.service.QueryService;
import com.stashkiv.pharmacy.web.rest.errors.BadRequestAlertException;
import com.stashkiv.pharmacy.service.dto.QueryCriteria;
import com.stashkiv.pharmacy.service.QueryQueryService;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link com.stashkiv.pharmacy.domain.Query}.
 */
@RestController
@RequestMapping("/api")
public class QueryResource {

    private final Logger log = LoggerFactory.getLogger(QueryResource.class);

    private static final String ENTITY_NAME = "query";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final QueryService queryService;

    private final QueryQueryService queryQueryService;

    public QueryResource(QueryService queryService, QueryQueryService queryQueryService) {
        this.queryService = queryService;
        this.queryQueryService = queryQueryService;
    }

    /**
     * {@code POST  /queries} : Create a new query.
     *
     * @param query the query to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new query, or with status {@code 400 (Bad Request)} if the query has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/queries")
    public ResponseEntity<Query> createQuery(@Valid @RequestBody Query query) throws URISyntaxException {
        log.debug("REST request to save Query : {}", query);
        if (query.getId() != null) {
            throw new BadRequestAlertException("A new query cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Query result = queryService.save(query);
        return ResponseEntity.created(new URI("/api/queries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /queries} : Updates an existing query.
     *
     * @param query the query to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated query,
     * or with status {@code 400 (Bad Request)} if the query is not valid,
     * or with status {@code 500 (Internal Server Error)} if the query couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/queries")
    public ResponseEntity<Query> updateQuery(@Valid @RequestBody Query query) throws URISyntaxException {
        log.debug("REST request to update Query : {}", query);
        if (query.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Query result = queryService.save(query);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, query.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /queries} : get all the queries.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of queries in body.
     */
    @GetMapping("/queries")
    public ResponseEntity<List<Query>> getAllQueries(QueryCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Queries by criteria: {}", criteria);
        Page<Query> page = queryQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /queries/count} : count all the queries.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/queries/count")
    public ResponseEntity<Long> countQueries(QueryCriteria criteria) {
        log.debug("REST request to count Queries by criteria: {}", criteria);
        return ResponseEntity.ok().body(queryQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /queries/:id} : get the "id" query.
     *
     * @param id the id of the query to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the query, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/queries/{id}")
    public ResponseEntity<Query> getQuery(@PathVariable Long id) {
        log.debug("REST request to get Query : {}", id);
        Optional<Query> query = queryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(query);
    }

    /**
     * {@code DELETE  /queries/:id} : delete the "id" query.
     *
     * @param id the id of the query to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/queries/{id}")
    public ResponseEntity<Void> deleteQuery(@PathVariable Long id) {
        log.debug("REST request to delete Query : {}", id);
        queryService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
