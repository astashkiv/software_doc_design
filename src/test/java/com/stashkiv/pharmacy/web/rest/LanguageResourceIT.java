package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Language;
import com.stashkiv.pharmacy.domain.Medicine;
import com.stashkiv.pharmacy.repository.LanguageRepository;
import com.stashkiv.pharmacy.service.LanguageService;
import com.stashkiv.pharmacy.service.dto.LanguageCriteria;
import com.stashkiv.pharmacy.service.LanguageQueryService;

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
 * Integration tests for the {@link LanguageResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class LanguageResourceIT {

    private static final String DEFAULT_TRANSLATION = "AAAAAAAAAA";
    private static final String UPDATED_TRANSLATION = "BBBBBBBBBB";

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LanguageService languageService;

    @Autowired
    private LanguageQueryService languageQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restLanguageMockMvc;

    private Language language;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createEntity(EntityManager em) {
        Language language = new Language()
            .translation(DEFAULT_TRANSLATION);
        return language;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Language createUpdatedEntity(EntityManager em) {
        Language language = new Language()
            .translation(UPDATED_TRANSLATION);
        return language;
    }

    @BeforeEach
    public void initTest() {
        language = createEntity(em);
    }

    @Test
    @Transactional
    public void createLanguage() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // Create the Language
        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(language)))
            .andExpect(status().isCreated());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate + 1);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getTranslation()).isEqualTo(DEFAULT_TRANSLATION);
    }

    @Test
    @Transactional
    public void createLanguageWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = languageRepository.findAll().size();

        // Create the Language with an existing ID
        language.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restLanguageMockMvc.perform(post("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(language)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllLanguages() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)));
    }
    
    @Test
    @Transactional
    public void getLanguage() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get the language
        restLanguageMockMvc.perform(get("/api/languages/{id}", language.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(language.getId().intValue()))
            .andExpect(jsonPath("$.translation").value(DEFAULT_TRANSLATION));
    }


    @Test
    @Transactional
    public void getLanguagesByIdFiltering() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        Long id = language.getId();

        defaultLanguageShouldBeFound("id.equals=" + id);
        defaultLanguageShouldNotBeFound("id.notEquals=" + id);

        defaultLanguageShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultLanguageShouldNotBeFound("id.greaterThan=" + id);

        defaultLanguageShouldBeFound("id.lessThanOrEqual=" + id);
        defaultLanguageShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllLanguagesByTranslationIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation equals to DEFAULT_TRANSLATION
        defaultLanguageShouldBeFound("translation.equals=" + DEFAULT_TRANSLATION);

        // Get all the languageList where translation equals to UPDATED_TRANSLATION
        defaultLanguageShouldNotBeFound("translation.equals=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllLanguagesByTranslationIsNotEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation not equals to DEFAULT_TRANSLATION
        defaultLanguageShouldNotBeFound("translation.notEquals=" + DEFAULT_TRANSLATION);

        // Get all the languageList where translation not equals to UPDATED_TRANSLATION
        defaultLanguageShouldBeFound("translation.notEquals=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllLanguagesByTranslationIsInShouldWork() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation in DEFAULT_TRANSLATION or UPDATED_TRANSLATION
        defaultLanguageShouldBeFound("translation.in=" + DEFAULT_TRANSLATION + "," + UPDATED_TRANSLATION);

        // Get all the languageList where translation equals to UPDATED_TRANSLATION
        defaultLanguageShouldNotBeFound("translation.in=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllLanguagesByTranslationIsNullOrNotNull() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation is not null
        defaultLanguageShouldBeFound("translation.specified=true");

        // Get all the languageList where translation is null
        defaultLanguageShouldNotBeFound("translation.specified=false");
    }
                @Test
    @Transactional
    public void getAllLanguagesByTranslationContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation contains DEFAULT_TRANSLATION
        defaultLanguageShouldBeFound("translation.contains=" + DEFAULT_TRANSLATION);

        // Get all the languageList where translation contains UPDATED_TRANSLATION
        defaultLanguageShouldNotBeFound("translation.contains=" + UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void getAllLanguagesByTranslationNotContainsSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);

        // Get all the languageList where translation does not contain DEFAULT_TRANSLATION
        defaultLanguageShouldNotBeFound("translation.doesNotContain=" + DEFAULT_TRANSLATION);

        // Get all the languageList where translation does not contain UPDATED_TRANSLATION
        defaultLanguageShouldBeFound("translation.doesNotContain=" + UPDATED_TRANSLATION);
    }


    @Test
    @Transactional
    public void getAllLanguagesByMedicinesIsEqualToSomething() throws Exception {
        // Initialize the database
        languageRepository.saveAndFlush(language);
        Medicine medicines = MedicineResourceIT.createEntity(em);
        em.persist(medicines);
        em.flush();
        language.addMedicines(medicines);
        languageRepository.saveAndFlush(language);
        Long medicinesId = medicines.getId();

        // Get all the languageList where medicines equals to medicinesId
        defaultLanguageShouldBeFound("medicinesId.equals=" + medicinesId);

        // Get all the languageList where medicines equals to medicinesId + 1
        defaultLanguageShouldNotBeFound("medicinesId.equals=" + (medicinesId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultLanguageShouldBeFound(String filter) throws Exception {
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(language.getId().intValue())))
            .andExpect(jsonPath("$.[*].translation").value(hasItem(DEFAULT_TRANSLATION)));

        // Check, that the count call also returns 1
        restLanguageMockMvc.perform(get("/api/languages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultLanguageShouldNotBeFound(String filter) throws Exception {
        restLanguageMockMvc.perform(get("/api/languages?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restLanguageMockMvc.perform(get("/api/languages/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingLanguage() throws Exception {
        // Get the language
        restLanguageMockMvc.perform(get("/api/languages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateLanguage() throws Exception {
        // Initialize the database
        languageService.save(language);

        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Update the language
        Language updatedLanguage = languageRepository.findById(language.getId()).get();
        // Disconnect from session so that the updates on updatedLanguage are not directly saved in db
        em.detach(updatedLanguage);
        updatedLanguage
            .translation(UPDATED_TRANSLATION);

        restLanguageMockMvc.perform(put("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedLanguage)))
            .andExpect(status().isOk());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
        Language testLanguage = languageList.get(languageList.size() - 1);
        assertThat(testLanguage.getTranslation()).isEqualTo(UPDATED_TRANSLATION);
    }

    @Test
    @Transactional
    public void updateNonExistingLanguage() throws Exception {
        int databaseSizeBeforeUpdate = languageRepository.findAll().size();

        // Create the Language

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restLanguageMockMvc.perform(put("/api/languages")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(language)))
            .andExpect(status().isBadRequest());

        // Validate the Language in the database
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteLanguage() throws Exception {
        // Initialize the database
        languageService.save(language);

        int databaseSizeBeforeDelete = languageRepository.findAll().size();

        // Delete the language
        restLanguageMockMvc.perform(delete("/api/languages/{id}", language.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Language> languageList = languageRepository.findAll();
        assertThat(languageList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
