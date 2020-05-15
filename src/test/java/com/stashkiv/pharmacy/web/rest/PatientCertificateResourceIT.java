package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.PatientCertificate;
import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.repository.PatientCertificateRepository;
import com.stashkiv.pharmacy.service.PatientCertificateService;
import com.stashkiv.pharmacy.service.dto.PatientCertificateCriteria;
import com.stashkiv.pharmacy.service.PatientCertificateQueryService;

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
 * Integration tests for the {@link PatientCertificateResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class PatientCertificateResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final LocalDate DEFAULT_RECEIVED_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_RECEIVED_DATE = LocalDate.now(ZoneId.systemDefault());
    private static final LocalDate SMALLER_RECEIVED_DATE = LocalDate.ofEpochDay(-1L);

    @Autowired
    private PatientCertificateRepository patientCertificateRepository;

    @Autowired
    private PatientCertificateService patientCertificateService;

    @Autowired
    private PatientCertificateQueryService patientCertificateQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPatientCertificateMockMvc;

    private PatientCertificate patientCertificate;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientCertificate createEntity(EntityManager em) {
        PatientCertificate patientCertificate = new PatientCertificate()
            .name(DEFAULT_NAME)
            .receivedDate(DEFAULT_RECEIVED_DATE);
        return patientCertificate;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PatientCertificate createUpdatedEntity(EntityManager em) {
        PatientCertificate patientCertificate = new PatientCertificate()
            .name(UPDATED_NAME)
            .receivedDate(UPDATED_RECEIVED_DATE);
        return patientCertificate;
    }

    @BeforeEach
    public void initTest() {
        patientCertificate = createEntity(em);
    }

    @Test
    @Transactional
    public void createPatientCertificate() throws Exception {
        int databaseSizeBeforeCreate = patientCertificateRepository.findAll().size();

        // Create the PatientCertificate
        restPatientCertificateMockMvc.perform(post("/api/patient-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientCertificate)))
            .andExpect(status().isCreated());

        // Validate the PatientCertificate in the database
        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeCreate + 1);
        PatientCertificate testPatientCertificate = patientCertificateList.get(patientCertificateList.size() - 1);
        assertThat(testPatientCertificate.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPatientCertificate.getReceivedDate()).isEqualTo(DEFAULT_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void createPatientCertificateWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = patientCertificateRepository.findAll().size();

        // Create the PatientCertificate with an existing ID
        patientCertificate.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPatientCertificateMockMvc.perform(post("/api/patient-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the PatientCertificate in the database
        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = patientCertificateRepository.findAll().size();
        // set the field null
        patientCertificate.setName(null);

        // Create the PatientCertificate, which fails.

        restPatientCertificateMockMvc.perform(post("/api/patient-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientCertificate)))
            .andExpect(status().isBadRequest());

        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllPatientCertificates() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(DEFAULT_RECEIVED_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getPatientCertificate() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get the patientCertificate
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates/{id}", patientCertificate.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(patientCertificate.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.receivedDate").value(DEFAULT_RECEIVED_DATE.toString()));
    }


    @Test
    @Transactional
    public void getPatientCertificatesByIdFiltering() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        Long id = patientCertificate.getId();

        defaultPatientCertificateShouldBeFound("id.equals=" + id);
        defaultPatientCertificateShouldNotBeFound("id.notEquals=" + id);

        defaultPatientCertificateShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPatientCertificateShouldNotBeFound("id.greaterThan=" + id);

        defaultPatientCertificateShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPatientCertificateShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPatientCertificatesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name equals to DEFAULT_NAME
        defaultPatientCertificateShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the patientCertificateList where name equals to UPDATED_NAME
        defaultPatientCertificateShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name not equals to DEFAULT_NAME
        defaultPatientCertificateShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the patientCertificateList where name not equals to UPDATED_NAME
        defaultPatientCertificateShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPatientCertificateShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the patientCertificateList where name equals to UPDATED_NAME
        defaultPatientCertificateShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name is not null
        defaultPatientCertificateShouldBeFound("name.specified=true");

        // Get all the patientCertificateList where name is null
        defaultPatientCertificateShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPatientCertificatesByNameContainsSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name contains DEFAULT_NAME
        defaultPatientCertificateShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the patientCertificateList where name contains UPDATED_NAME
        defaultPatientCertificateShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where name does not contain DEFAULT_NAME
        defaultPatientCertificateShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the patientCertificateList where name does not contain UPDATED_NAME
        defaultPatientCertificateShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate equals to DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.equals=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.equals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsNotEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate not equals to DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.notEquals=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate not equals to UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.notEquals=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsInShouldWork() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate in DEFAULT_RECEIVED_DATE or UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.in=" + DEFAULT_RECEIVED_DATE + "," + UPDATED_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate equals to UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.in=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsNullOrNotNull() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate is not null
        defaultPatientCertificateShouldBeFound("receivedDate.specified=true");

        // Get all the patientCertificateList where receivedDate is null
        defaultPatientCertificateShouldNotBeFound("receivedDate.specified=false");
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate is greater than or equal to DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.greaterThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate is greater than or equal to UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.greaterThanOrEqual=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate is less than or equal to DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.lessThanOrEqual=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate is less than or equal to SMALLER_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.lessThanOrEqual=" + SMALLER_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsLessThanSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate is less than DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.lessThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate is less than UPDATED_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.lessThan=" + UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void getAllPatientCertificatesByReceivedDateIsGreaterThanSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);

        // Get all the patientCertificateList where receivedDate is greater than DEFAULT_RECEIVED_DATE
        defaultPatientCertificateShouldNotBeFound("receivedDate.greaterThan=" + DEFAULT_RECEIVED_DATE);

        // Get all the patientCertificateList where receivedDate is greater than SMALLER_RECEIVED_DATE
        defaultPatientCertificateShouldBeFound("receivedDate.greaterThan=" + SMALLER_RECEIVED_DATE);
    }


    @Test
    @Transactional
    public void getAllPatientCertificatesByDoctorIsEqualToSomething() throws Exception {
        // Initialize the database
        patientCertificateRepository.saveAndFlush(patientCertificate);
        Patient doctor = PatientResourceIT.createEntity(em);
        em.persist(doctor);
        em.flush();
        patientCertificate.setDoctor(doctor);
        patientCertificateRepository.saveAndFlush(patientCertificate);
        Long doctorId = doctor.getId();

        // Get all the patientCertificateList where doctor equals to doctorId
        defaultPatientCertificateShouldBeFound("doctorId.equals=" + doctorId);

        // Get all the patientCertificateList where doctor equals to doctorId + 1
        defaultPatientCertificateShouldNotBeFound("doctorId.equals=" + (doctorId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPatientCertificateShouldBeFound(String filter) throws Exception {
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(patientCertificate.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].receivedDate").value(hasItem(DEFAULT_RECEIVED_DATE.toString())));

        // Check, that the count call also returns 1
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPatientCertificateShouldNotBeFound(String filter) throws Exception {
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPatientCertificate() throws Exception {
        // Get the patientCertificate
        restPatientCertificateMockMvc.perform(get("/api/patient-certificates/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePatientCertificate() throws Exception {
        // Initialize the database
        patientCertificateService.save(patientCertificate);

        int databaseSizeBeforeUpdate = patientCertificateRepository.findAll().size();

        // Update the patientCertificate
        PatientCertificate updatedPatientCertificate = patientCertificateRepository.findById(patientCertificate.getId()).get();
        // Disconnect from session so that the updates on updatedPatientCertificate are not directly saved in db
        em.detach(updatedPatientCertificate);
        updatedPatientCertificate
            .name(UPDATED_NAME)
            .receivedDate(UPDATED_RECEIVED_DATE);

        restPatientCertificateMockMvc.perform(put("/api/patient-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPatientCertificate)))
            .andExpect(status().isOk());

        // Validate the PatientCertificate in the database
        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeUpdate);
        PatientCertificate testPatientCertificate = patientCertificateList.get(patientCertificateList.size() - 1);
        assertThat(testPatientCertificate.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPatientCertificate.getReceivedDate()).isEqualTo(UPDATED_RECEIVED_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingPatientCertificate() throws Exception {
        int databaseSizeBeforeUpdate = patientCertificateRepository.findAll().size();

        // Create the PatientCertificate

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPatientCertificateMockMvc.perform(put("/api/patient-certificates")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(patientCertificate)))
            .andExpect(status().isBadRequest());

        // Validate the PatientCertificate in the database
        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePatientCertificate() throws Exception {
        // Initialize the database
        patientCertificateService.save(patientCertificate);

        int databaseSizeBeforeDelete = patientCertificateRepository.findAll().size();

        // Delete the patientCertificate
        restPatientCertificateMockMvc.perform(delete("/api/patient-certificates/{id}", patientCertificate.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<PatientCertificate> patientCertificateList = patientCertificateRepository.findAll();
        assertThat(patientCertificateList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
