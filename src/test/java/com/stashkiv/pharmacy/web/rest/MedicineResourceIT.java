package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Medicine;
import com.stashkiv.pharmacy.domain.Language;
import com.stashkiv.pharmacy.domain.Prescription;
import com.stashkiv.pharmacy.repository.MedicineRepository;
import com.stashkiv.pharmacy.service.MedicineService;
import com.stashkiv.pharmacy.service.dto.MedicineCriteria;
import com.stashkiv.pharmacy.service.MedicineQueryService;

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
 * Integration tests for the {@link MedicineResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)
@ExtendWith(MockitoExtension.class)
@AutoConfigureMockMvc
@WithMockUser
public class MedicineResourceIT {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_INSTRUCTION = "AAAAAAAAAA";
    private static final String UPDATED_INSTRUCTION = "BBBBBBBBBB";

    @Autowired
    private MedicineRepository medicineRepository;

    @Mock
    private MedicineRepository medicineRepositoryMock;

    @Mock
    private MedicineService medicineServiceMock;

    @Autowired
    private MedicineService medicineService;

    @Autowired
    private MedicineQueryService medicineQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicineMockMvc;

    private Medicine medicine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .name(DEFAULT_NAME)
            .instruction(DEFAULT_INSTRUCTION);
        return medicine;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Medicine createUpdatedEntity(EntityManager em) {
        Medicine medicine = new Medicine()
            .name(UPDATED_NAME)
            .instruction(UPDATED_INSTRUCTION);
        return medicine;
    }

    @BeforeEach
    public void initTest() {
        medicine = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicine() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isCreated());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate + 1);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testMedicine.getInstruction()).isEqualTo(DEFAULT_INSTRUCTION);
    }

    @Test
    @Transactional
    public void createMedicineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicineRepository.findAll().size();

        // Create the Medicine with an existing ID
        medicine.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkNameIsRequired() throws Exception {
        int databaseSizeBeforeTest = medicineRepository.findAll().size();
        // set the field null
        medicine.setName(null);

        // Create the Medicine, which fails.

        restMedicineMockMvc.perform(post("/api/medicines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isBadRequest());

        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllMedicines() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList
        restMedicineMockMvc.perform(get("/api/medicines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION)));
    }
    
    @SuppressWarnings({"unchecked"})
    public void getAllMedicinesWithEagerRelationshipsIsEnabled() throws Exception {
        when(medicineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMedicineMockMvc.perform(get("/api/medicines?eagerload=true"))
            .andExpect(status().isOk());

        verify(medicineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @SuppressWarnings({"unchecked"})
    public void getAllMedicinesWithEagerRelationshipsIsNotEnabled() throws Exception {
        when(medicineServiceMock.findAllWithEagerRelationships(any())).thenReturn(new PageImpl(new ArrayList<>()));

        restMedicineMockMvc.perform(get("/api/medicines?eagerload=true"))
            .andExpect(status().isOk());

        verify(medicineServiceMock, times(1)).findAllWithEagerRelationships(any());
    }

    @Test
    @Transactional
    public void getMedicine() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", medicine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicine.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.instruction").value(DEFAULT_INSTRUCTION));
    }


    @Test
    @Transactional
    public void getMedicinesByIdFiltering() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        Long id = medicine.getId();

        defaultMedicineShouldBeFound("id.equals=" + id);
        defaultMedicineShouldNotBeFound("id.notEquals=" + id);

        defaultMedicineShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedicineShouldNotBeFound("id.greaterThan=" + id);

        defaultMedicineShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedicineShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMedicinesByNameIsEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name equals to DEFAULT_NAME
        defaultMedicineShouldBeFound("name.equals=" + DEFAULT_NAME);

        // Get all the medicineList where name equals to UPDATED_NAME
        defaultMedicineShouldNotBeFound("name.equals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicinesByNameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name not equals to DEFAULT_NAME
        defaultMedicineShouldNotBeFound("name.notEquals=" + DEFAULT_NAME);

        // Get all the medicineList where name not equals to UPDATED_NAME
        defaultMedicineShouldBeFound("name.notEquals=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicinesByNameIsInShouldWork() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name in DEFAULT_NAME or UPDATED_NAME
        defaultMedicineShouldBeFound("name.in=" + DEFAULT_NAME + "," + UPDATED_NAME);

        // Get all the medicineList where name equals to UPDATED_NAME
        defaultMedicineShouldNotBeFound("name.in=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicinesByNameIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name is not null
        defaultMedicineShouldBeFound("name.specified=true");

        // Get all the medicineList where name is null
        defaultMedicineShouldNotBeFound("name.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicinesByNameContainsSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name contains DEFAULT_NAME
        defaultMedicineShouldBeFound("name.contains=" + DEFAULT_NAME);

        // Get all the medicineList where name contains UPDATED_NAME
        defaultMedicineShouldNotBeFound("name.contains=" + UPDATED_NAME);
    }

    @Test
    @Transactional
    public void getAllMedicinesByNameNotContainsSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where name does not contain DEFAULT_NAME
        defaultMedicineShouldNotBeFound("name.doesNotContain=" + DEFAULT_NAME);

        // Get all the medicineList where name does not contain UPDATED_NAME
        defaultMedicineShouldBeFound("name.doesNotContain=" + UPDATED_NAME);
    }


    @Test
    @Transactional
    public void getAllMedicinesByInstructionIsEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction equals to DEFAULT_INSTRUCTION
        defaultMedicineShouldBeFound("instruction.equals=" + DEFAULT_INSTRUCTION);

        // Get all the medicineList where instruction equals to UPDATED_INSTRUCTION
        defaultMedicineShouldNotBeFound("instruction.equals=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllMedicinesByInstructionIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction not equals to DEFAULT_INSTRUCTION
        defaultMedicineShouldNotBeFound("instruction.notEquals=" + DEFAULT_INSTRUCTION);

        // Get all the medicineList where instruction not equals to UPDATED_INSTRUCTION
        defaultMedicineShouldBeFound("instruction.notEquals=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllMedicinesByInstructionIsInShouldWork() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction in DEFAULT_INSTRUCTION or UPDATED_INSTRUCTION
        defaultMedicineShouldBeFound("instruction.in=" + DEFAULT_INSTRUCTION + "," + UPDATED_INSTRUCTION);

        // Get all the medicineList where instruction equals to UPDATED_INSTRUCTION
        defaultMedicineShouldNotBeFound("instruction.in=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllMedicinesByInstructionIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction is not null
        defaultMedicineShouldBeFound("instruction.specified=true");

        // Get all the medicineList where instruction is null
        defaultMedicineShouldNotBeFound("instruction.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicinesByInstructionContainsSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction contains DEFAULT_INSTRUCTION
        defaultMedicineShouldBeFound("instruction.contains=" + DEFAULT_INSTRUCTION);

        // Get all the medicineList where instruction contains UPDATED_INSTRUCTION
        defaultMedicineShouldNotBeFound("instruction.contains=" + UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void getAllMedicinesByInstructionNotContainsSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);

        // Get all the medicineList where instruction does not contain DEFAULT_INSTRUCTION
        defaultMedicineShouldNotBeFound("instruction.doesNotContain=" + DEFAULT_INSTRUCTION);

        // Get all the medicineList where instruction does not contain UPDATED_INSTRUCTION
        defaultMedicineShouldBeFound("instruction.doesNotContain=" + UPDATED_INSTRUCTION);
    }


    @Test
    @Transactional
    public void getAllMedicinesByLanguagesInIsEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);
        Language languagesIn = LanguageResourceIT.createEntity(em);
        em.persist(languagesIn);
        em.flush();
        medicine.addLanguagesIn(languagesIn);
        medicineRepository.saveAndFlush(medicine);
        Long languagesInId = languagesIn.getId();

        // Get all the medicineList where languagesIn equals to languagesInId
        defaultMedicineShouldBeFound("languagesInId.equals=" + languagesInId);

        // Get all the medicineList where languagesIn equals to languagesInId + 1
        defaultMedicineShouldNotBeFound("languagesInId.equals=" + (languagesInId + 1));
    }


    @Test
    @Transactional
    public void getAllMedicinesByPrescriptionsIsEqualToSomething() throws Exception {
        // Initialize the database
        medicineRepository.saveAndFlush(medicine);
        Prescription prescriptions = PrescriptionResourceIT.createEntity(em);
        em.persist(prescriptions);
        em.flush();
        medicine.addPrescriptions(prescriptions);
        medicineRepository.saveAndFlush(medicine);
        Long prescriptionsId = prescriptions.getId();

        // Get all the medicineList where prescriptions equals to prescriptionsId
        defaultMedicineShouldBeFound("prescriptionsId.equals=" + prescriptionsId);

        // Get all the medicineList where prescriptions equals to prescriptionsId + 1
        defaultMedicineShouldNotBeFound("prescriptionsId.equals=" + (prescriptionsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedicineShouldBeFound(String filter) throws Exception {
        restMedicineMockMvc.perform(get("/api/medicines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicine.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].instruction").value(hasItem(DEFAULT_INSTRUCTION)));

        // Check, that the count call also returns 1
        restMedicineMockMvc.perform(get("/api/medicines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedicineShouldNotBeFound(String filter) throws Exception {
        restMedicineMockMvc.perform(get("/api/medicines?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedicineMockMvc.perform(get("/api/medicines/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMedicine() throws Exception {
        // Get the medicine
        restMedicineMockMvc.perform(get("/api/medicines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicine() throws Exception {
        // Initialize the database
        medicineService.save(medicine);

        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Update the medicine
        Medicine updatedMedicine = medicineRepository.findById(medicine.getId()).get();
        // Disconnect from session so that the updates on updatedMedicine are not directly saved in db
        em.detach(updatedMedicine);
        updatedMedicine
            .name(UPDATED_NAME)
            .instruction(UPDATED_INSTRUCTION);

        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicine)))
            .andExpect(status().isOk());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
        Medicine testMedicine = medicineList.get(medicineList.size() - 1);
        assertThat(testMedicine.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testMedicine.getInstruction()).isEqualTo(UPDATED_INSTRUCTION);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicine() throws Exception {
        int databaseSizeBeforeUpdate = medicineRepository.findAll().size();

        // Create the Medicine

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicineMockMvc.perform(put("/api/medicines")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicine)))
            .andExpect(status().isBadRequest());

        // Validate the Medicine in the database
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicine() throws Exception {
        // Initialize the database
        medicineService.save(medicine);

        int databaseSizeBeforeDelete = medicineRepository.findAll().size();

        // Delete the medicine
        restMedicineMockMvc.perform(delete("/api/medicines/{id}", medicine.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Medicine> medicineList = medicineRepository.findAll();
        assertThat(medicineList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
