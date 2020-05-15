package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Query;
import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.repository.QueryRepository;
import com.stashkiv.pharmacy.service.QueryService;
import com.stashkiv.pharmacy.service.dto.QueryCriteria;
import com.stashkiv.pharmacy.service.QueryQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link QueryResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class QueryResourceIT {

    private static final String DEFAULT_QUERY = "AAAAAAAAAA";
    private static final String UPDATED_QUERY = "BBBBBBBBBB";

    private static final String DEFAULT_ANSWER = "AAAAAAAAAA";
    private static final String UPDATED_ANSWER = "BBBBBBBBBB";

    @Autowired
    private QueryRepository queryRepository;

    @Autowired
    private QueryService queryService;

    @Autowired
    private QueryQueryService queryQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restQueryMockMvc;

    private Query query;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Query createEntity(EntityManager em) {
        Query query = new Query()
            .query(DEFAULT_QUERY)
            .answer(DEFAULT_ANSWER);
        return query;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Query createUpdatedEntity(EntityManager em) {
        Query query = new Query()
            .query(UPDATED_QUERY)
            .answer(UPDATED_ANSWER);
        return query;
    }

    @BeforeEach
    public void initTest() {
        query = createEntity(em);
    }

    @Test
    @Transactional
    public void createQuery() throws Exception {
        int databaseSizeBeforeCreate = queryRepository.findAll().size();

        // Create the Query
        restQueryMockMvc.perform(post("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isCreated());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeCreate + 1);
        Query testQuery = queryList.get(queryList.size() - 1);
        assertThat(testQuery.getQuery()).isEqualTo(DEFAULT_QUERY);
        assertThat(testQuery.getAnswer()).isEqualTo(DEFAULT_ANSWER);
    }

    @Test
    @Transactional
    public void createQueryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = queryRepository.findAll().size();

        // Create the Query with an existing ID
        query.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restQueryMockMvc.perform(post("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isBadRequest());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkQueryIsRequired() throws Exception {
        int databaseSizeBeforeTest = queryRepository.findAll().size();
        // set the field null
        query.setQuery(null);

        // Create the Query, which fails.

        restQueryMockMvc.perform(post("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isBadRequest());

        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllQueries() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList
        restQueryMockMvc.perform(get("/api/queries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(query.getId().intValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)));
    }
    
    @Test
    @Transactional
    public void getQuery() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get the query
        restQueryMockMvc.perform(get("/api/queries/{id}", query.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(query.getId().intValue()))
            .andExpect(jsonPath("$.query").value(DEFAULT_QUERY))
            .andExpect(jsonPath("$.answer").value(DEFAULT_ANSWER));
    }


    @Test
    @Transactional
    public void getQueriesByIdFiltering() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        Long id = query.getId();

        defaultQueryShouldBeFound("id.equals=" + id);
        defaultQueryShouldNotBeFound("id.notEquals=" + id);

        defaultQueryShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultQueryShouldNotBeFound("id.greaterThan=" + id);

        defaultQueryShouldBeFound("id.lessThanOrEqual=" + id);
        defaultQueryShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllQueriesByQueryIsEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query equals to DEFAULT_QUERY
        defaultQueryShouldBeFound("query.equals=" + DEFAULT_QUERY);

        // Get all the queryList where query equals to UPDATED_QUERY
        defaultQueryShouldNotBeFound("query.equals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllQueriesByQueryIsNotEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query not equals to DEFAULT_QUERY
        defaultQueryShouldNotBeFound("query.notEquals=" + DEFAULT_QUERY);

        // Get all the queryList where query not equals to UPDATED_QUERY
        defaultQueryShouldBeFound("query.notEquals=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllQueriesByQueryIsInShouldWork() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query in DEFAULT_QUERY or UPDATED_QUERY
        defaultQueryShouldBeFound("query.in=" + DEFAULT_QUERY + "," + UPDATED_QUERY);

        // Get all the queryList where query equals to UPDATED_QUERY
        defaultQueryShouldNotBeFound("query.in=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllQueriesByQueryIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query is not null
        defaultQueryShouldBeFound("query.specified=true");

        // Get all the queryList where query is null
        defaultQueryShouldNotBeFound("query.specified=false");
    }
                @Test
    @Transactional
    public void getAllQueriesByQueryContainsSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query contains DEFAULT_QUERY
        defaultQueryShouldBeFound("query.contains=" + DEFAULT_QUERY);

        // Get all the queryList where query contains UPDATED_QUERY
        defaultQueryShouldNotBeFound("query.contains=" + UPDATED_QUERY);
    }

    @Test
    @Transactional
    public void getAllQueriesByQueryNotContainsSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where query does not contain DEFAULT_QUERY
        defaultQueryShouldNotBeFound("query.doesNotContain=" + DEFAULT_QUERY);

        // Get all the queryList where query does not contain UPDATED_QUERY
        defaultQueryShouldBeFound("query.doesNotContain=" + UPDATED_QUERY);
    }


    @Test
    @Transactional
    public void getAllQueriesByAnswerIsEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer equals to DEFAULT_ANSWER
        defaultQueryShouldBeFound("answer.equals=" + DEFAULT_ANSWER);

        // Get all the queryList where answer equals to UPDATED_ANSWER
        defaultQueryShouldNotBeFound("answer.equals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllQueriesByAnswerIsNotEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer not equals to DEFAULT_ANSWER
        defaultQueryShouldNotBeFound("answer.notEquals=" + DEFAULT_ANSWER);

        // Get all the queryList where answer not equals to UPDATED_ANSWER
        defaultQueryShouldBeFound("answer.notEquals=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllQueriesByAnswerIsInShouldWork() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer in DEFAULT_ANSWER or UPDATED_ANSWER
        defaultQueryShouldBeFound("answer.in=" + DEFAULT_ANSWER + "," + UPDATED_ANSWER);

        // Get all the queryList where answer equals to UPDATED_ANSWER
        defaultQueryShouldNotBeFound("answer.in=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllQueriesByAnswerIsNullOrNotNull() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer is not null
        defaultQueryShouldBeFound("answer.specified=true");

        // Get all the queryList where answer is null
        defaultQueryShouldNotBeFound("answer.specified=false");
    }
                @Test
    @Transactional
    public void getAllQueriesByAnswerContainsSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer contains DEFAULT_ANSWER
        defaultQueryShouldBeFound("answer.contains=" + DEFAULT_ANSWER);

        // Get all the queryList where answer contains UPDATED_ANSWER
        defaultQueryShouldNotBeFound("answer.contains=" + UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void getAllQueriesByAnswerNotContainsSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);

        // Get all the queryList where answer does not contain DEFAULT_ANSWER
        defaultQueryShouldNotBeFound("answer.doesNotContain=" + DEFAULT_ANSWER);

        // Get all the queryList where answer does not contain UPDATED_ANSWER
        defaultQueryShouldBeFound("answer.doesNotContain=" + UPDATED_ANSWER);
    }


    @Test
    @Transactional
    public void getAllQueriesByAnsweredByIsEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);
        Doctor answeredBy = DoctorResourceIT.createEntity(em);
        em.persist(answeredBy);
        em.flush();
        query.setAnsweredBy(answeredBy);
        queryRepository.saveAndFlush(query);
        Long answeredById = answeredBy.getId();

        // Get all the queryList where answeredBy equals to answeredById
        defaultQueryShouldBeFound("answeredById.equals=" + answeredById);

        // Get all the queryList where answeredBy equals to answeredById + 1
        defaultQueryShouldNotBeFound("answeredById.equals=" + (answeredById + 1));
    }


    @Test
    @Transactional
    public void getAllQueriesByAskedByIsEqualToSomething() throws Exception {
        // Initialize the database
        queryRepository.saveAndFlush(query);
        Patient askedBy = PatientResourceIT.createEntity(em);
        em.persist(askedBy);
        em.flush();
        query.setAskedBy(askedBy);
        queryRepository.saveAndFlush(query);
        Long askedById = askedBy.getId();

        // Get all the queryList where askedBy equals to askedById
        defaultQueryShouldBeFound("askedById.equals=" + askedById);

        // Get all the queryList where askedBy equals to askedById + 1
        defaultQueryShouldNotBeFound("askedById.equals=" + (askedById + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultQueryShouldBeFound(String filter) throws Exception {
        restQueryMockMvc.perform(get("/api/queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(query.getId().intValue())))
            .andExpect(jsonPath("$.[*].query").value(hasItem(DEFAULT_QUERY)))
            .andExpect(jsonPath("$.[*].answer").value(hasItem(DEFAULT_ANSWER)));

        // Check, that the count call also returns 1
        restQueryMockMvc.perform(get("/api/queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultQueryShouldNotBeFound(String filter) throws Exception {
        restQueryMockMvc.perform(get("/api/queries?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restQueryMockMvc.perform(get("/api/queries/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingQuery() throws Exception {
        // Get the query
        restQueryMockMvc.perform(get("/api/queries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateQuery() throws Exception {
        // Initialize the database
        queryService.save(query);

        int databaseSizeBeforeUpdate = queryRepository.findAll().size();

        // Update the query
        Query updatedQuery = queryRepository.findById(query.getId()).get();
        // Disconnect from session so that the updates on updatedQuery are not directly saved in db
        em.detach(updatedQuery);
        updatedQuery
            .query(UPDATED_QUERY)
            .answer(UPDATED_ANSWER);

        restQueryMockMvc.perform(put("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedQuery)))
            .andExpect(status().isOk());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeUpdate);
        Query testQuery = queryList.get(queryList.size() - 1);
        assertThat(testQuery.getQuery()).isEqualTo(UPDATED_QUERY);
        assertThat(testQuery.getAnswer()).isEqualTo(UPDATED_ANSWER);
    }

    @Test
    @Transactional
    public void updateNonExistingQuery() throws Exception {
        int databaseSizeBeforeUpdate = queryRepository.findAll().size();

        // Create the Query

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restQueryMockMvc.perform(put("/api/queries")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(query)))
            .andExpect(status().isBadRequest());

        // Validate the Query in the database
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteQuery() throws Exception {
        // Initialize the database
        queryService.save(query);

        int databaseSizeBeforeDelete = queryRepository.findAll().size();

        // Delete the query
        restQueryMockMvc.perform(delete("/api/queries/{id}", query.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Query> queryList = queryRepository.findAll();
        assertThat(queryList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
