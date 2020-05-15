package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.MedicalCondition;
import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.repository.MedicalConditionRepository;
import com.stashkiv.pharmacy.service.MedicalConditionService;
import com.stashkiv.pharmacy.service.dto.MedicalConditionCriteria;
import com.stashkiv.pharmacy.service.MedicalConditionQueryService;

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
 * Integration tests for the {@link MedicalConditionResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class MedicalConditionResourceIT {

    private static final Integer DEFAULT_AGE = 1;
    private static final Integer UPDATED_AGE = 2;
    private static final Integer SMALLER_AGE = 1 - 1;

    private static final Double DEFAULT_HEIGHT = 1D;
    private static final Double UPDATED_HEIGHT = 2D;
    private static final Double SMALLER_HEIGHT = 1D - 1D;

    private static final Double DEFAULT_WEIGHT = 1D;
    private static final Double UPDATED_WEIGHT = 2D;
    private static final Double SMALLER_WEIGHT = 1D - 1D;

    private static final String DEFAULT_COMMENTS = "AAAAAAAAAA";
    private static final String UPDATED_COMMENTS = "BBBBBBBBBB";

    private static final Double DEFAULT_TEMPERATURE = 1D;
    private static final Double UPDATED_TEMPERATURE = 2D;
    private static final Double SMALLER_TEMPERATURE = 1D - 1D;

    private static final Double DEFAULT_BLOOD_SUGAR_LEVEL = 1D;
    private static final Double UPDATED_BLOOD_SUGAR_LEVEL = 2D;
    private static final Double SMALLER_BLOOD_SUGAR_LEVEL = 1D - 1D;

    private static final String DEFAULT_PRESSURE = "AAAAAAAAAA";
    private static final String UPDATED_PRESSURE = "BBBBBBBBBB";

    private static final Integer DEFAULT_PULSE = 1;
    private static final Integer UPDATED_PULSE = 2;
    private static final Integer SMALLER_PULSE = 1 - 1;

    @Autowired
    private MedicalConditionRepository medicalConditionRepository;

    @Autowired
    private MedicalConditionService medicalConditionService;

    @Autowired
    private MedicalConditionQueryService medicalConditionQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restMedicalConditionMockMvc;

    private MedicalCondition medicalCondition;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCondition createEntity(EntityManager em) {
        MedicalCondition medicalCondition = new MedicalCondition()
            .age(DEFAULT_AGE)
            .height(DEFAULT_HEIGHT)
            .weight(DEFAULT_WEIGHT)
            .comments(DEFAULT_COMMENTS)
            .temperature(DEFAULT_TEMPERATURE)
            .bloodSugarLevel(DEFAULT_BLOOD_SUGAR_LEVEL)
            .pressure(DEFAULT_PRESSURE)
            .pulse(DEFAULT_PULSE);
        return medicalCondition;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static MedicalCondition createUpdatedEntity(EntityManager em) {
        MedicalCondition medicalCondition = new MedicalCondition()
            .age(UPDATED_AGE)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .comments(UPDATED_COMMENTS)
            .temperature(UPDATED_TEMPERATURE)
            .bloodSugarLevel(UPDATED_BLOOD_SUGAR_LEVEL)
            .pressure(UPDATED_PRESSURE)
            .pulse(UPDATED_PULSE);
        return medicalCondition;
    }

    @BeforeEach
    public void initTest() {
        medicalCondition = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedicalCondition() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionRepository.findAll().size();

        // Create the MedicalCondition
        restMedicalConditionMockMvc.perform(post("/api/medical-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCondition)))
            .andExpect(status().isCreated());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeCreate + 1);
        MedicalCondition testMedicalCondition = medicalConditionList.get(medicalConditionList.size() - 1);
        assertThat(testMedicalCondition.getAge()).isEqualTo(DEFAULT_AGE);
        assertThat(testMedicalCondition.getHeight()).isEqualTo(DEFAULT_HEIGHT);
        assertThat(testMedicalCondition.getWeight()).isEqualTo(DEFAULT_WEIGHT);
        assertThat(testMedicalCondition.getComments()).isEqualTo(DEFAULT_COMMENTS);
        assertThat(testMedicalCondition.getTemperature()).isEqualTo(DEFAULT_TEMPERATURE);
        assertThat(testMedicalCondition.getBloodSugarLevel()).isEqualTo(DEFAULT_BLOOD_SUGAR_LEVEL);
        assertThat(testMedicalCondition.getPressure()).isEqualTo(DEFAULT_PRESSURE);
        assertThat(testMedicalCondition.getPulse()).isEqualTo(DEFAULT_PULSE);
    }

    @Test
    @Transactional
    public void createMedicalConditionWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = medicalConditionRepository.findAll().size();

        // Create the MedicalCondition with an existing ID
        medicalCondition.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMedicalConditionMockMvc.perform(post("/api/medical-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCondition)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllMedicalConditions() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodSugarLevel").value(hasItem(DEFAULT_BLOOD_SUGAR_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].pressure").value(hasItem(DEFAULT_PRESSURE)))
            .andExpect(jsonPath("$.[*].pulse").value(hasItem(DEFAULT_PULSE)));
    }
    
    @Test
    @Transactional
    public void getMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get the medicalCondition
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions/{id}", medicalCondition.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(medicalCondition.getId().intValue()))
            .andExpect(jsonPath("$.age").value(DEFAULT_AGE))
            .andExpect(jsonPath("$.height").value(DEFAULT_HEIGHT.doubleValue()))
            .andExpect(jsonPath("$.weight").value(DEFAULT_WEIGHT.doubleValue()))
            .andExpect(jsonPath("$.comments").value(DEFAULT_COMMENTS))
            .andExpect(jsonPath("$.temperature").value(DEFAULT_TEMPERATURE.doubleValue()))
            .andExpect(jsonPath("$.bloodSugarLevel").value(DEFAULT_BLOOD_SUGAR_LEVEL.doubleValue()))
            .andExpect(jsonPath("$.pressure").value(DEFAULT_PRESSURE))
            .andExpect(jsonPath("$.pulse").value(DEFAULT_PULSE));
    }


    @Test
    @Transactional
    public void getMedicalConditionsByIdFiltering() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        Long id = medicalCondition.getId();

        defaultMedicalConditionShouldBeFound("id.equals=" + id);
        defaultMedicalConditionShouldNotBeFound("id.notEquals=" + id);

        defaultMedicalConditionShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultMedicalConditionShouldNotBeFound("id.greaterThan=" + id);

        defaultMedicalConditionShouldBeFound("id.lessThanOrEqual=" + id);
        defaultMedicalConditionShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age equals to DEFAULT_AGE
        defaultMedicalConditionShouldBeFound("age.equals=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age equals to UPDATED_AGE
        defaultMedicalConditionShouldNotBeFound("age.equals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age not equals to DEFAULT_AGE
        defaultMedicalConditionShouldNotBeFound("age.notEquals=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age not equals to UPDATED_AGE
        defaultMedicalConditionShouldBeFound("age.notEquals=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age in DEFAULT_AGE or UPDATED_AGE
        defaultMedicalConditionShouldBeFound("age.in=" + DEFAULT_AGE + "," + UPDATED_AGE);

        // Get all the medicalConditionList where age equals to UPDATED_AGE
        defaultMedicalConditionShouldNotBeFound("age.in=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age is not null
        defaultMedicalConditionShouldBeFound("age.specified=true");

        // Get all the medicalConditionList where age is null
        defaultMedicalConditionShouldNotBeFound("age.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age is greater than or equal to DEFAULT_AGE
        defaultMedicalConditionShouldBeFound("age.greaterThanOrEqual=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age is greater than or equal to UPDATED_AGE
        defaultMedicalConditionShouldNotBeFound("age.greaterThanOrEqual=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age is less than or equal to DEFAULT_AGE
        defaultMedicalConditionShouldBeFound("age.lessThanOrEqual=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age is less than or equal to SMALLER_AGE
        defaultMedicalConditionShouldNotBeFound("age.lessThanOrEqual=" + SMALLER_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age is less than DEFAULT_AGE
        defaultMedicalConditionShouldNotBeFound("age.lessThan=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age is less than UPDATED_AGE
        defaultMedicalConditionShouldBeFound("age.lessThan=" + UPDATED_AGE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByAgeIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where age is greater than DEFAULT_AGE
        defaultMedicalConditionShouldNotBeFound("age.greaterThan=" + DEFAULT_AGE);

        // Get all the medicalConditionList where age is greater than SMALLER_AGE
        defaultMedicalConditionShouldBeFound("age.greaterThan=" + SMALLER_AGE);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height equals to DEFAULT_HEIGHT
        defaultMedicalConditionShouldBeFound("height.equals=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height equals to UPDATED_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.equals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height not equals to DEFAULT_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.notEquals=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height not equals to UPDATED_HEIGHT
        defaultMedicalConditionShouldBeFound("height.notEquals=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height in DEFAULT_HEIGHT or UPDATED_HEIGHT
        defaultMedicalConditionShouldBeFound("height.in=" + DEFAULT_HEIGHT + "," + UPDATED_HEIGHT);

        // Get all the medicalConditionList where height equals to UPDATED_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.in=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height is not null
        defaultMedicalConditionShouldBeFound("height.specified=true");

        // Get all the medicalConditionList where height is null
        defaultMedicalConditionShouldNotBeFound("height.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height is greater than or equal to DEFAULT_HEIGHT
        defaultMedicalConditionShouldBeFound("height.greaterThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height is greater than or equal to UPDATED_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.greaterThanOrEqual=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height is less than or equal to DEFAULT_HEIGHT
        defaultMedicalConditionShouldBeFound("height.lessThanOrEqual=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height is less than or equal to SMALLER_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.lessThanOrEqual=" + SMALLER_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height is less than DEFAULT_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.lessThan=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height is less than UPDATED_HEIGHT
        defaultMedicalConditionShouldBeFound("height.lessThan=" + UPDATED_HEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByHeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where height is greater than DEFAULT_HEIGHT
        defaultMedicalConditionShouldNotBeFound("height.greaterThan=" + DEFAULT_HEIGHT);

        // Get all the medicalConditionList where height is greater than SMALLER_HEIGHT
        defaultMedicalConditionShouldBeFound("height.greaterThan=" + SMALLER_HEIGHT);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight equals to DEFAULT_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.equals=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight equals to UPDATED_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.equals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight not equals to DEFAULT_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.notEquals=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight not equals to UPDATED_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.notEquals=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight in DEFAULT_WEIGHT or UPDATED_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.in=" + DEFAULT_WEIGHT + "," + UPDATED_WEIGHT);

        // Get all the medicalConditionList where weight equals to UPDATED_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.in=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight is not null
        defaultMedicalConditionShouldBeFound("weight.specified=true");

        // Get all the medicalConditionList where weight is null
        defaultMedicalConditionShouldNotBeFound("weight.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight is greater than or equal to DEFAULT_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.greaterThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight is greater than or equal to UPDATED_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.greaterThanOrEqual=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight is less than or equal to DEFAULT_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.lessThanOrEqual=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight is less than or equal to SMALLER_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.lessThanOrEqual=" + SMALLER_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight is less than DEFAULT_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.lessThan=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight is less than UPDATED_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.lessThan=" + UPDATED_WEIGHT);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByWeightIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where weight is greater than DEFAULT_WEIGHT
        defaultMedicalConditionShouldNotBeFound("weight.greaterThan=" + DEFAULT_WEIGHT);

        // Get all the medicalConditionList where weight is greater than SMALLER_WEIGHT
        defaultMedicalConditionShouldBeFound("weight.greaterThan=" + SMALLER_WEIGHT);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments equals to DEFAULT_COMMENTS
        defaultMedicalConditionShouldBeFound("comments.equals=" + DEFAULT_COMMENTS);

        // Get all the medicalConditionList where comments equals to UPDATED_COMMENTS
        defaultMedicalConditionShouldNotBeFound("comments.equals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments not equals to DEFAULT_COMMENTS
        defaultMedicalConditionShouldNotBeFound("comments.notEquals=" + DEFAULT_COMMENTS);

        // Get all the medicalConditionList where comments not equals to UPDATED_COMMENTS
        defaultMedicalConditionShouldBeFound("comments.notEquals=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments in DEFAULT_COMMENTS or UPDATED_COMMENTS
        defaultMedicalConditionShouldBeFound("comments.in=" + DEFAULT_COMMENTS + "," + UPDATED_COMMENTS);

        // Get all the medicalConditionList where comments equals to UPDATED_COMMENTS
        defaultMedicalConditionShouldNotBeFound("comments.in=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments is not null
        defaultMedicalConditionShouldBeFound("comments.specified=true");

        // Get all the medicalConditionList where comments is null
        defaultMedicalConditionShouldNotBeFound("comments.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsContainsSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments contains DEFAULT_COMMENTS
        defaultMedicalConditionShouldBeFound("comments.contains=" + DEFAULT_COMMENTS);

        // Get all the medicalConditionList where comments contains UPDATED_COMMENTS
        defaultMedicalConditionShouldNotBeFound("comments.contains=" + UPDATED_COMMENTS);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByCommentsNotContainsSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where comments does not contain DEFAULT_COMMENTS
        defaultMedicalConditionShouldNotBeFound("comments.doesNotContain=" + DEFAULT_COMMENTS);

        // Get all the medicalConditionList where comments does not contain UPDATED_COMMENTS
        defaultMedicalConditionShouldBeFound("comments.doesNotContain=" + UPDATED_COMMENTS);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature equals to DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.equals=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature equals to UPDATED_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.equals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature not equals to DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.notEquals=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature not equals to UPDATED_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.notEquals=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature in DEFAULT_TEMPERATURE or UPDATED_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.in=" + DEFAULT_TEMPERATURE + "," + UPDATED_TEMPERATURE);

        // Get all the medicalConditionList where temperature equals to UPDATED_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.in=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature is not null
        defaultMedicalConditionShouldBeFound("temperature.specified=true");

        // Get all the medicalConditionList where temperature is null
        defaultMedicalConditionShouldNotBeFound("temperature.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature is greater than or equal to DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.greaterThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature is greater than or equal to UPDATED_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.greaterThanOrEqual=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature is less than or equal to DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.lessThanOrEqual=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature is less than or equal to SMALLER_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.lessThanOrEqual=" + SMALLER_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature is less than DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.lessThan=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature is less than UPDATED_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.lessThan=" + UPDATED_TEMPERATURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByTemperatureIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where temperature is greater than DEFAULT_TEMPERATURE
        defaultMedicalConditionShouldNotBeFound("temperature.greaterThan=" + DEFAULT_TEMPERATURE);

        // Get all the medicalConditionList where temperature is greater than SMALLER_TEMPERATURE
        defaultMedicalConditionShouldBeFound("temperature.greaterThan=" + SMALLER_TEMPERATURE);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel equals to DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.equals=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel equals to UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.equals=" + UPDATED_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel not equals to DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.notEquals=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel not equals to UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.notEquals=" + UPDATED_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel in DEFAULT_BLOOD_SUGAR_LEVEL or UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.in=" + DEFAULT_BLOOD_SUGAR_LEVEL + "," + UPDATED_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel equals to UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.in=" + UPDATED_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel is not null
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.specified=true");

        // Get all the medicalConditionList where bloodSugarLevel is null
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel is greater than or equal to DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.greaterThanOrEqual=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel is greater than or equal to UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.greaterThanOrEqual=" + UPDATED_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel is less than or equal to DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.lessThanOrEqual=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel is less than or equal to SMALLER_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.lessThanOrEqual=" + SMALLER_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel is less than DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.lessThan=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel is less than UPDATED_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.lessThan=" + UPDATED_BLOOD_SUGAR_LEVEL);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByBloodSugarLevelIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where bloodSugarLevel is greater than DEFAULT_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldNotBeFound("bloodSugarLevel.greaterThan=" + DEFAULT_BLOOD_SUGAR_LEVEL);

        // Get all the medicalConditionList where bloodSugarLevel is greater than SMALLER_BLOOD_SUGAR_LEVEL
        defaultMedicalConditionShouldBeFound("bloodSugarLevel.greaterThan=" + SMALLER_BLOOD_SUGAR_LEVEL);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByPressureIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure equals to DEFAULT_PRESSURE
        defaultMedicalConditionShouldBeFound("pressure.equals=" + DEFAULT_PRESSURE);

        // Get all the medicalConditionList where pressure equals to UPDATED_PRESSURE
        defaultMedicalConditionShouldNotBeFound("pressure.equals=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPressureIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure not equals to DEFAULT_PRESSURE
        defaultMedicalConditionShouldNotBeFound("pressure.notEquals=" + DEFAULT_PRESSURE);

        // Get all the medicalConditionList where pressure not equals to UPDATED_PRESSURE
        defaultMedicalConditionShouldBeFound("pressure.notEquals=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPressureIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure in DEFAULT_PRESSURE or UPDATED_PRESSURE
        defaultMedicalConditionShouldBeFound("pressure.in=" + DEFAULT_PRESSURE + "," + UPDATED_PRESSURE);

        // Get all the medicalConditionList where pressure equals to UPDATED_PRESSURE
        defaultMedicalConditionShouldNotBeFound("pressure.in=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPressureIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure is not null
        defaultMedicalConditionShouldBeFound("pressure.specified=true");

        // Get all the medicalConditionList where pressure is null
        defaultMedicalConditionShouldNotBeFound("pressure.specified=false");
    }
                @Test
    @Transactional
    public void getAllMedicalConditionsByPressureContainsSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure contains DEFAULT_PRESSURE
        defaultMedicalConditionShouldBeFound("pressure.contains=" + DEFAULT_PRESSURE);

        // Get all the medicalConditionList where pressure contains UPDATED_PRESSURE
        defaultMedicalConditionShouldNotBeFound("pressure.contains=" + UPDATED_PRESSURE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPressureNotContainsSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pressure does not contain DEFAULT_PRESSURE
        defaultMedicalConditionShouldNotBeFound("pressure.doesNotContain=" + DEFAULT_PRESSURE);

        // Get all the medicalConditionList where pressure does not contain UPDATED_PRESSURE
        defaultMedicalConditionShouldBeFound("pressure.doesNotContain=" + UPDATED_PRESSURE);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse equals to DEFAULT_PULSE
        defaultMedicalConditionShouldBeFound("pulse.equals=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse equals to UPDATED_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.equals=" + UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsNotEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse not equals to DEFAULT_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.notEquals=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse not equals to UPDATED_PULSE
        defaultMedicalConditionShouldBeFound("pulse.notEquals=" + UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsInShouldWork() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse in DEFAULT_PULSE or UPDATED_PULSE
        defaultMedicalConditionShouldBeFound("pulse.in=" + DEFAULT_PULSE + "," + UPDATED_PULSE);

        // Get all the medicalConditionList where pulse equals to UPDATED_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.in=" + UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsNullOrNotNull() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse is not null
        defaultMedicalConditionShouldBeFound("pulse.specified=true");

        // Get all the medicalConditionList where pulse is null
        defaultMedicalConditionShouldNotBeFound("pulse.specified=false");
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse is greater than or equal to DEFAULT_PULSE
        defaultMedicalConditionShouldBeFound("pulse.greaterThanOrEqual=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse is greater than or equal to UPDATED_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.greaterThanOrEqual=" + UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse is less than or equal to DEFAULT_PULSE
        defaultMedicalConditionShouldBeFound("pulse.lessThanOrEqual=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse is less than or equal to SMALLER_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.lessThanOrEqual=" + SMALLER_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsLessThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse is less than DEFAULT_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.lessThan=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse is less than UPDATED_PULSE
        defaultMedicalConditionShouldBeFound("pulse.lessThan=" + UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void getAllMedicalConditionsByPulseIsGreaterThanSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);

        // Get all the medicalConditionList where pulse is greater than DEFAULT_PULSE
        defaultMedicalConditionShouldNotBeFound("pulse.greaterThan=" + DEFAULT_PULSE);

        // Get all the medicalConditionList where pulse is greater than SMALLER_PULSE
        defaultMedicalConditionShouldBeFound("pulse.greaterThan=" + SMALLER_PULSE);
    }


    @Test
    @Transactional
    public void getAllMedicalConditionsByPatientIsEqualToSomething() throws Exception {
        // Initialize the database
        medicalConditionRepository.saveAndFlush(medicalCondition);
        Patient patient = PatientResourceIT.createEntity(em);
        em.persist(patient);
        em.flush();
        medicalCondition.setPatient(patient);
        medicalConditionRepository.saveAndFlush(medicalCondition);
        Long patientId = patient.getId();

        // Get all the medicalConditionList where patient equals to patientId
        defaultMedicalConditionShouldBeFound("patientId.equals=" + patientId);

        // Get all the medicalConditionList where patient equals to patientId + 1
        defaultMedicalConditionShouldNotBeFound("patientId.equals=" + (patientId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultMedicalConditionShouldBeFound(String filter) throws Exception {
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(medicalCondition.getId().intValue())))
            .andExpect(jsonPath("$.[*].age").value(hasItem(DEFAULT_AGE)))
            .andExpect(jsonPath("$.[*].height").value(hasItem(DEFAULT_HEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].weight").value(hasItem(DEFAULT_WEIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].comments").value(hasItem(DEFAULT_COMMENTS)))
            .andExpect(jsonPath("$.[*].temperature").value(hasItem(DEFAULT_TEMPERATURE.doubleValue())))
            .andExpect(jsonPath("$.[*].bloodSugarLevel").value(hasItem(DEFAULT_BLOOD_SUGAR_LEVEL.doubleValue())))
            .andExpect(jsonPath("$.[*].pressure").value(hasItem(DEFAULT_PRESSURE)))
            .andExpect(jsonPath("$.[*].pulse").value(hasItem(DEFAULT_PULSE)));

        // Check, that the count call also returns 1
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultMedicalConditionShouldNotBeFound(String filter) throws Exception {
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingMedicalCondition() throws Exception {
        // Get the medicalCondition
        restMedicalConditionMockMvc.perform(get("/api/medical-conditions/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionService.save(medicalCondition);

        int databaseSizeBeforeUpdate = medicalConditionRepository.findAll().size();

        // Update the medicalCondition
        MedicalCondition updatedMedicalCondition = medicalConditionRepository.findById(medicalCondition.getId()).get();
        // Disconnect from session so that the updates on updatedMedicalCondition are not directly saved in db
        em.detach(updatedMedicalCondition);
        updatedMedicalCondition
            .age(UPDATED_AGE)
            .height(UPDATED_HEIGHT)
            .weight(UPDATED_WEIGHT)
            .comments(UPDATED_COMMENTS)
            .temperature(UPDATED_TEMPERATURE)
            .bloodSugarLevel(UPDATED_BLOOD_SUGAR_LEVEL)
            .pressure(UPDATED_PRESSURE)
            .pulse(UPDATED_PULSE);

        restMedicalConditionMockMvc.perform(put("/api/medical-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedMedicalCondition)))
            .andExpect(status().isOk());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeUpdate);
        MedicalCondition testMedicalCondition = medicalConditionList.get(medicalConditionList.size() - 1);
        assertThat(testMedicalCondition.getAge()).isEqualTo(UPDATED_AGE);
        assertThat(testMedicalCondition.getHeight()).isEqualTo(UPDATED_HEIGHT);
        assertThat(testMedicalCondition.getWeight()).isEqualTo(UPDATED_WEIGHT);
        assertThat(testMedicalCondition.getComments()).isEqualTo(UPDATED_COMMENTS);
        assertThat(testMedicalCondition.getTemperature()).isEqualTo(UPDATED_TEMPERATURE);
        assertThat(testMedicalCondition.getBloodSugarLevel()).isEqualTo(UPDATED_BLOOD_SUGAR_LEVEL);
        assertThat(testMedicalCondition.getPressure()).isEqualTo(UPDATED_PRESSURE);
        assertThat(testMedicalCondition.getPulse()).isEqualTo(UPDATED_PULSE);
    }

    @Test
    @Transactional
    public void updateNonExistingMedicalCondition() throws Exception {
        int databaseSizeBeforeUpdate = medicalConditionRepository.findAll().size();

        // Create the MedicalCondition

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restMedicalConditionMockMvc.perform(put("/api/medical-conditions")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(medicalCondition)))
            .andExpect(status().isBadRequest());

        // Validate the MedicalCondition in the database
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteMedicalCondition() throws Exception {
        // Initialize the database
        medicalConditionService.save(medicalCondition);

        int databaseSizeBeforeDelete = medicalConditionRepository.findAll().size();

        // Delete the medicalCondition
        restMedicalConditionMockMvc.perform(delete("/api/medical-conditions/{id}", medicalCondition.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<MedicalCondition> medicalConditionList = medicalConditionRepository.findAll();
        assertThat(medicalConditionList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
