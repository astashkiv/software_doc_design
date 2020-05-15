package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Prescription;
import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.domain.Medicine;
import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.repository.PrescriptionRepository;
import com.stashkiv.pharmacy.service.PrescriptionService;
import com.stashkiv.pharmacy.service.dto.PrescriptionCriteria;
import com.stashkiv.pharmacy.service.PrescriptionQueryService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link PrescriptionResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class PrescriptionResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_TIMINGS = "AAAAAAAAAA";
    private static final String UPDATED_TIMINGS = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDICINE_COUNT = 1;
    private static final Integer UPDATED_MEDICINE_COUNT = 2;
    private static final Integer SMALLER_MEDICINE_COUNT = 1 - 1;

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Mock
    private PrescriptionRepository prescriptionRepositoryMock;

    @Mock
    private PrescriptionService prescriptionServiceMock;

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private PrescriptionQueryService prescriptionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPrescriptionMockMvc;

    private Prescription prescription;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prescription createEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .name(DEFAULT_NAME)
            .timings(DEFAULT_TIMINGS)
            .medicineCount(DEFAULT_MEDICINE_COUNT);
        return prescription;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Prescription createUpdatedEntity(EntityManager em) {
        Prescription prescription = new Prescription()
            .name(UPDATED_NAME)
            .timings(UPDATED_TIMINGS)
            .medicineCount(UPDATED_MEDICINE_COUNT);
        return prescription;
    }

    @BeforeEach
    public void initTest() {
        prescription = createEntity(em);
    }

    @Test
    @Transactional
    public void createPrescription() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isCreated());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate + 1);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testPrescription.getTimings()).isEqualTo(DEFAULT_TIMINGS);
        assertThat(testPrescription.getMedicineCount()).isEqualTo(DEFAULT_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void createPrescriptionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = prescriptionRepository.findAll().size();

        // Create the Prescription with an existing ID
        prescription.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPrescriptionMockMvc.perform(post("/api/prescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllPrescriptions() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].timings").value(hasItem(DEFAULT_TIMINGS)))
            .andExpect(jsonPath("$.[*].medicineCount").value(hasItem(DEFAULT_MEDICINE_COUNT)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllPrescriptionsWithEagerRelationshipsIsEnabled() throws Exception {
        when(prescriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrescriptionMockMvc.perform(get("/api/prescriptions?eagerload=true"))
            .andExpect(status().isOk());

        verify(prescriptionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllPrescriptionsWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(prescriptionServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restPrescriptionMockMvc.perform(get("/api/prescriptions?eagerload=true"))
            .andExpect(status().isOk());

        verify(prescriptionServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getPrescription() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", prescription.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(prescription.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.timings").value(DEFAULT_TIMINGS))
            .andExpect(jsonPath("$.medicineCount").value(DEFAULT_MEDICINE_COUNT));
    }


    @Test
    @Transactional
    public void getPrescriptionsByIdFiltering() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        Long id = prescription.getId();

        defaultPrescriptionShouldBeFound("id.equals=" + id);
        defaultPrescriptionShouldNotBeFound("id.notEquals=" + id);

        defaultPrescriptionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultPrescriptionShouldNotBeFound("id.greaterThan=" + id);

        defaultPrescriptionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultPrescriptionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name equals to DEFAULT_NAME
        defaultPrescriptionShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the prescriptionList where name equals to UPDATED_NAME
        defaultPrescriptionShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name not equals to DEFAULT_NAME
        defaultPrescriptionShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the prescriptionList where name not equals to UPDATED_NAME
        defaultPrescriptionShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByNameIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name in DEFAULT_NAME or UPDATED_NAME
        defaultPrescriptionShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the prescriptionList where name equals to UPDATED_NAME
        defaultPrescriptionShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name is not null
        defaultPrescriptionShouldBeFound("name.specified=true");

        // Get all the prescriptionList where name is null
        defaultPrescriptionShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrescriptionsByNameContainsSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name contains DEFAULT_NAME
        defaultPrescriptionShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the prescriptionList where name contains UPDATED_NAME
        defaultPrescriptionShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByNameNotContainsSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where name does not contain DEFAULT_NAME
        defaultPrescriptionShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the prescriptionList where name does not contain UPDATED_NAME
        defaultPrescriptionShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByTimingsIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings equals to DEFAULT_TIMINGS
        defaultPrescriptionShouldBeFound("timings.equals=" + DEFAULT_TIMINGS);

        // Get all the prescriptionList where timings equals to UPDATED_TIMINGS
        defaultPrescriptionShouldNotBeFound("timings.equals=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTimingsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings not equals to DEFAULT_TIMINGS
        defaultPrescriptionShouldNotBeFound("timings.notEquals=" + DEFAULT_TIMINGS);

        // Get all the prescriptionList where timings not equals to UPDATED_TIMINGS
        defaultPrescriptionShouldBeFound("timings.notEquals=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTimingsIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings in DEFAULT_TIMINGS or UPDATED_TIMINGS
        defaultPrescriptionShouldBeFound("timings.in=" + DEFAULT_TIMINGS + "," + UPDATED_TIMINGS);

        // Get all the prescriptionList where timings equals to UPDATED_TIMINGS
        defaultPrescriptionShouldNotBeFound("timings.in=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTimingsIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings is not null
        defaultPrescriptionShouldBeFound("timings.specified=true");

        // Get all the prescriptionList where timings is null
        defaultPrescriptionShouldNotBeFound("timings.specified=false");
    }
                @Test
    @Transactional
    public void getAllPrescriptionsByTimingsContainsSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings contains DEFAULT_TIMINGS
        defaultPrescriptionShouldBeFound("timings.contains=" + DEFAULT_TIMINGS);

        // Get all the prescriptionList where timings contains UPDATED_TIMINGS
        defaultPrescriptionShouldNotBeFound("timings.contains=" + UPDATED_TIMINGS);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByTimingsNotContainsSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where timings does not contain DEFAULT_TIMINGS
        defaultPrescriptionShouldNotBeFound("timings.doesNotContain=" + DEFAULT_TIMINGS);

        // Get all the prescriptionList where timings does not contain UPDATED_TIMINGS
        defaultPrescriptionShouldBeFound("timings.doesNotContain=" + UPDATED_TIMINGS);
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount equals to DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.equals=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount equals to UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.equals=" + UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsNotEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount not equals to DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.notEquals=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount not equals to UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.notEquals=" + UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsInShouldWork() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount in DEFAULT_MEDICINE_COUNT or UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.in=" + DEFAULT_MEDICINE_COUNT + "," + UPDATED_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount equals to UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.in=" + UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsNullOrNotNull() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount is not null
        defaultPrescriptionShouldBeFound("medicineCount.specified=true");

        // Get all the prescriptionList where medicineCount is null
        defaultPrescriptionShouldNotBeFound("medicineCount.specified=false");
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount is greater than or equal to DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.greaterThanOrEqual=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount is greater than or equal to UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.greaterThanOrEqual=" + UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount is less than or equal to DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.lessThanOrEqual=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount is less than or equal to SMALLER_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.lessThanOrEqual=" + SMALLER_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsLessThanSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount is less than DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.lessThan=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount is less than UPDATED_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.lessThan=" + UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void getAllPrescriptionsByMedicineCountIsGreaterThanSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);

        // Get all the prescriptionList where medicineCount is greater than DEFAULT_MEDICINE_COUNT
        defaultPrescriptionShouldNotBeFound("medicineCount.greaterThan=" + DEFAULT_MEDICINE_COUNT);

        // Get all the prescriptionList where medicineCount is greater than SMALLER_MEDICINE_COUNT
        defaultPrescriptionShouldBeFound("medicineCount.greaterThan=" + SMALLER_MEDICINE_COUNT);
    }


    @Test
    @Transactional
    public void getAllPrescriptionsBySignedByIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);
        Doctor signedBy = DoctorResourceIT.createEntity(em);
        em.persist(signedBy);
        em.flush();
        prescription.setSignedBy(signedBy);
        prescriptionRepository.saveAndFlush(prescription);
        Long signedById = signedBy.getId();

        // Get all the prescriptionList where signedBy equals to signedById
        defaultPrescriptionShouldBeFound("signedById.equals=" + signedById);

        // Get all the prescriptionList where signedBy equals to signedById + 1
        defaultPrescriptionShouldNotBeFound("signedById.equals=" + (signedById + 1));
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByMedicinesIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);
        Medicine medicines = MedicineResourceIT.createEntity(em);
        em.persist(medicines);
        em.flush();
        prescription.addMedicines(medicines);
        prescriptionRepository.saveAndFlush(prescription);
        Long medicinesId = medicines.getId();

        // Get all the prescriptionList where medicines equals to medicinesId
        defaultPrescriptionShouldBeFound("medicinesId.equals=" + medicinesId);

        // Get all the prescriptionList where medicines equals to medicinesId + 1
        defaultPrescriptionShouldNotBeFound("medicinesId.equals=" + (medicinesId + 1));
    }


    @Test
    @Transactional
    public void getAllPrescriptionsByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        prescriptionRepository.saveAndFlush(prescription);
        Patient patient = PatientResourceIT.createEntity(em);
        em.persist(patient);
        em.flush();
        prescription.setPatient(patient);
        prescriptionRepository.saveAndFlush(prescription);
        Long patientId = patient.getId();

        // Get all the prescriptionList where patient equals to patientId
        defaultPrescriptionShouldBeFound("patientId.equals=" + patientId);

        // Get all the prescriptionList where patient equals to patientId + 1
        defaultPrescriptionShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultPrescriptionShouldBeFound(String filter) throws Exception {
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(prescription.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].timings").value(hasItem(DEFAULT_TIMINGS)))
            .andExpect(jsonPath("$.[*].medicineCount").value(hasItem(DEFAULT_MEDICINE_COUNT)));

        // Check, that the count call also returns 1
        restPrescriptionMockMvc.perform(get("/api/prescriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultPrescriptionShouldNotBeFound(String filter) throws Exception {
        restPrescriptionMockMvc.perform(get("/api/prescriptions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restPrescriptionMockMvc.perform(get("/api/prescriptions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingPrescription() throws Exception {
        // Get the prescription
        restPrescriptionMockMvc.perform(get("/api/prescriptions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);

        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Update the prescription
        Prescription updatedPrescription = prescriptionRepository.findById(prescription.getId()).get();
        // Disconnect from session so that the updates on updatedPrescription are not directly saved in db
        em.detach(updatedPrescription);
        updatedPrescription
            .name(UPDATED_NAME)
            .timings(UPDATED_TIMINGS)
            .medicineCount(UPDATED_MEDICINE_COUNT);

        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedPrescription)))
            .andExpect(status().isOk());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
        Prescription testPrescription = prescriptionList.get(prescriptionList.size() - 1);
        assertThat(testPrescription.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testPrescription.getTimings()).isEqualTo(UPDATED_TIMINGS);
        assertThat(testPrescription.getMedicineCount()).isEqualTo(UPDATED_MEDICINE_COUNT);
    }

    @Test
    @Transactional
    public void updateNonExistingPrescription() throws Exception {
        int databaseSizeBeforeUpdate = prescriptionRepository.findAll().size();

        // Create the Prescription

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPrescriptionMockMvc.perform(put("/api/prescriptions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(prescription)))
            .andExpect(status().isBadRequest());

        // Validate the Prescription in the database
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deletePrescription() throws Exception {
        // Initialize the database
        prescriptionService.save(prescription);

        int databaseSizeBeforeDelete = prescriptionRepository.findAll().size();

        // Delete the prescription
        restPrescriptionMockMvc.perform(delete("/api/prescriptions/{id}", prescription.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Prescription> prescriptionList = prescriptionRepository.findAll();
        assertThat(prescriptionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
