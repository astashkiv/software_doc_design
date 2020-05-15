package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.DoctorCertificate;
import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.repository.DoctorCertificateRepository;
import com.stashkiv.pharmacy.service.DoctorCertificateService;
import com.stashkiv.pharmacy.service.dto.DoctorCertificateCriteria;
import com.stashkiv.pharmacy.service.DoctorCertificateQueryService;

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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link DoctorCertificateResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class DoctorCertificateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private DoctorCertificateRepository doctorCertificateRepository;

    @Autowired
    private DoctorCertificateService doctorCertificateService;

    @Autowired
    private DoctorCertificateQueryService doctorCertificateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restDoctorCertificateMockMvc;

    private DoctorCertificate doctorCertificate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorCertificate createEntity(EntityManager em) {
        DoctorCertificate doctorCertificate = new DoctorCertificate()
            .name(DEFAULT_NAME)
            .receivedDate(DEFAULT_RECEIVED_DATE);
        return doctorCertificate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static DoctorCertificate createUpdatedEntity(EntityManager em) {
        DoctorCertificate doctorCertificate = new DoctorCertificate()
            .name(UPDATED_NAME)
            .receivedDate(UPDATED_RECEIVED_DATE);
        return doctorCertificate;
    }

    @BeforeEach
    public void initTest() {
        doctorCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createDoctorCertificate() throws Exception {
        int databaseSizeBeforeCreate = doctorCertificateRepository.findAll().size();

        // Create the DoctorCertificate
        restDoctorCertificateMockMvc.perform(post("/api/doctor-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorCertificate)))
            .andExpect(status().isCreated());

        // Validate the DoctorCertificate in the database
        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        DoctorCertificate testDoctorCertificate = doctorCertificateList.get(doctorCertificateList.size() - 1);
        assertThat(testDoctorCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDoctorCertificate.getReceivedDate()).isEqualTo(DEFAULT_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void createDoctorCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = doctorCertificateRepository.findAll().size();

        // Create the DoctorCertificate with an existing ID
        doctorCertificate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDoctorCertificateMockMvc.perform(post("/api/doctor-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorCertificate in the database
        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = doctorCertificateRepository.findAll().size();
        // set the field null
        doctorCertificate.setName(null);

        // Create the DoctorCertificate, which fails.

        restDoctorCertificateMockMvc.perform(post("/api/doctor-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorCertificate)))
            .andExpect(status().isBadRequest());

        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificates() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(DEFAULT_RECEIVED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getDoctorCertificate() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get the doctorCertificate
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates/{id}", doctorCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(doctorCertificate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.receivedDate").value(DEFAULT_RECEIVED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getDoctorCertificatesByIdFiltering() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        Long id = doctorCertificate.getId();

        defaultDoctorCertificateShouldBeFound("id.equals=" + id);
        defaultDoctorCertificateShouldNotBeFound("id.notEquals=" + id);

        defaultDoctorCertificateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultDoctorCertificateShouldNotBeFound("id.greaterThan=" + id);

        defaultDoctorCertificateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultDoctorCertificateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllDoctorCertificatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name equals to DEFAULT_NAME
        defaultDoctorCertificateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the doctorCertificateList where name equals to UPDATED_NAME
        defaultDoctorCertificateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name not equals to DEFAULT_NAME
        defaultDoctorCertificateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the doctorCertificateList where name not equals to UPDATED_NAME
        defaultDoctorCertificateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultDoctorCertificateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the doctorCertificateList where name equals to UPDATED_NAME
        defaultDoctorCertificateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name is not null
        defaultDoctorCertificateShouldBeFound("name.specified=true");

        // Get all the doctorCertificateList where name is null
        defaultDoctorCertificateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllDoctorCertificatesByNameContainsSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name contains DEFAULT_NAME
        defaultDoctorCertificateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the doctorCertificateList where name contains UPDATED_NAME
        defaultDoctorCertificateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where name does not contain DEFAULT_NAME
        defaultDoctorCertificateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the doctorCertificateList where name does not contain UPDATED_NAME
        defaultDoctorCertificateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate equals to DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.equals=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.equals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate not equals to DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.notEquals=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate not equals to UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.notEquals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate in DEFAULT_RECEIVED_DATE or UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.in=" + DEFAULT_RECEIVED_DATE + "," + UPDATED_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.in=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate is not null
        defaultDoctorCertificateShouldBeFound("receivedDate.specified=true");

        // Get all the doctorCertificateList where receivedDate is null
        defaultDoctorCertificateShouldNotBeFound("receivedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate is greater than or equal to DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.greaterThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate is greater than or equal to UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.greaterThanOrEqual=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate is less than or equal to DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.lessThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate is less than or equal to SMALLER_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.lessThanOrEqual=" + SMALLER_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate is less than DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.lessThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate is less than UPDATED_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.lessThan=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllDoctorCertificatesByReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);

        // Get all the doctorCertificateList where receivedDate is greater than DEFAULT_RECEIVED_DATE
        defaultDoctorCertificateShouldNotBeFound("receivedDate.greaterThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the doctorCertificateList where receivedDate is greater than SMALLER_RECEIVED_DATE
        defaultDoctorCertificateShouldBeFound("receivedDate.greaterThan=" + SMALLER_RECEIVED_DATE);
    }


    @Test
    @Transactional
    public void getAllDoctorCertificatesByDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        doctorCertificateRepository.saveAndFlush(doctorCertificate);
        Doctor doctor = DoctorResourceIT.createEntity(em);
        em.persist(doctor);
        em.flush();
        doctorCertificate.setDoctor(doctor);
        doctorCertificateRepository.saveAndFlush(doctorCertificate);
        Long doctorId = doctor.getId();

        // Get all the doctorCertificateList where doctor equals to doctorId
        defaultDoctorCertificateShouldBeFound("doctorId.equals=" + doctorId);

        // Get all the doctorCertificateList where doctor equals to doctorId + 1
        defaultDoctorCertificateShouldNotBeFound("doctorId.equals=" + (doctorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultDoctorCertificateShouldBeFound(String filter) throws Exception {
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(doctorCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(DEFAULT_RECEIVED_DATE.toString())));

        // Check, that the count call also returns 1
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultDoctorCertificateShouldNotBeFound(String filter) throws Exception {
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingDoctorCertificate() throws Exception {
        // Get the doctorCertificate
        restDoctorCertificateMockMvc.perform(get("/api/doctor-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDoctorCertificate() throws Exception {
        // Initialize the database
        doctorCertificateService.save(doctorCertificate);

        int databaseSizeBeforeUpdate = doctorCertificateRepository.findAll().size();

        // Update the doctorCertificate
        DoctorCertificate updatedDoctorCertificate = doctorCertificateRepository.findById(doctorCertificate.getId()).get();
        // Disconnect from session so that the updates on updatedDoctorCertificate are not directly saved in db
        em.detach(updatedDoctorCertificate);
        updatedDoctorCertificate
            .name(UPDATED_NAME)
            .receivedDate(UPDATED_RECEIVED_DATE);

        restDoctorCertificateMockMvc.perform(put("/api/doctor-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedDoctorCertificate)))
            .andExpect(status().isOk());

        // Validate the DoctorCertificate in the database
        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeUpdate);
        DoctorCertificate testDoctorCertificate = doctorCertificateList.get(doctorCertificateList.size() - 1);
        assertThat(testDoctorCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDoctorCertificate.getReceivedDate()).isEqualTo(UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingDoctorCertificate() throws Exception {
        int databaseSizeBeforeUpdate = doctorCertificateRepository.findAll().size();

        // Create the DoctorCertificate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restDoctorCertificateMockMvc.perform(put("/api/doctor-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(doctorCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the DoctorCertificate in the database
        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteDoctorCertificate() throws Exception {
        // Initialize the database
        doctorCertificateService.save(doctorCertificate);

        int databaseSizeBeforeDelete = doctorCertificateRepository.findAll().size();

        // Delete the doctorCertificate
        restDoctorCertificateMockMvc.perform(delete("/api/doctor-certificates/{id}", doctorCertificate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<DoctorCertificate> doctorCertificateList = doctorCertificateRepository.findAll();
        assertThat(doctorCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
