package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Admin;
import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.repository.AdminRepository;
import com.stashkiv.pharmacy.service.AdminService;
import com.stashkiv.pharmacy.service.dto.AdminCriteria;
import com.stashkiv.pharmacy.service.AdminQueryService;

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
 * Integration tests for the {@link AdminResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class AdminResourceIT {

    private static final String DEFAULT_USERNAME = "AAAAAAAAAA";
    private static final String UPDATED_USERNAME = "BBBBBBBBBB";

    private static final String DEFAULT_PASSWORD = "AAAAAAAAAA";
    private static final String UPDATED_PASSWORD = "BBBBBBBBBB";

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private AdminService adminService;

    @Autowired
    private AdminQueryService adminQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAdminMockMvc;

    private Admin admin;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createEntity(EntityManager em) {
        Admin admin = new Admin()
            .username(DEFAULT_USERNAME)
            .password(DEFAULT_PASSWORD);
        return admin;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Admin createUpdatedEntity(EntityManager em) {
        Admin admin = new Admin()
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);
        return admin;
    }

    @BeforeEach
    public void initTest() {
        admin = createEntity(em);
    }

    @Test
    @Transactional
    public void createAdmin() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isCreated());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate + 1);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getUsername()).isEqualTo(DEFAULT_USERNAME);
        assertThat(testAdmin.getPassword()).isEqualTo(DEFAULT_PASSWORD);
    }

    @Test
    @Transactional
    public void createAdminWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = adminRepository.findAll().size();

        // Create the Admin with an existing ID
        admin.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void checkUsernameIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setUsername(null);

        // Create the Admin, which fails.

        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void checkPasswordIsRequired() throws Exception {
        int databaseSizeBeforeTest = adminRepository.findAll().size();
        // set the field null
        admin.setPassword(null);

        // Create the Admin, which fails.

        restAdminMockMvc.perform(post("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeTest);
    }

    @Test
    @Transactional
    public void getAllAdmins() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));
    }
    
    @Test
    @Transactional
    public void getAdmin() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", admin.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(admin.getId().intValue()))
            .andExpect(jsonPath("$.username").value(DEFAULT_USERNAME))
            .andExpect(jsonPath("$.password").value(DEFAULT_PASSWORD));
    }


    @Test
    @Transactional
    public void getAdminsByIdFiltering() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        Long id = admin.getId();

        defaultAdminShouldBeFound("id.equals=" + id);
        defaultAdminShouldNotBeFound("id.notEquals=" + id);

        defaultAdminShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultAdminShouldNotBeFound("id.greaterThan=" + id);

        defaultAdminShouldBeFound("id.lessThanOrEqual=" + id);
        defaultAdminShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllAdminsByUsernameIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username equals to DEFAULT_USERNAME
        defaultAdminShouldBeFound("username.equals=" + DEFAULT_USERNAME);

        // Get all the adminList where username equals to UPDATED_USERNAME
        defaultAdminShouldNotBeFound("username.equals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByUsernameIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username not equals to DEFAULT_USERNAME
        defaultAdminShouldNotBeFound("username.notEquals=" + DEFAULT_USERNAME);

        // Get all the adminList where username not equals to UPDATED_USERNAME
        defaultAdminShouldBeFound("username.notEquals=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByUsernameIsInShouldWork() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username in DEFAULT_USERNAME or UPDATED_USERNAME
        defaultAdminShouldBeFound("username.in=" + DEFAULT_USERNAME + "," + UPDATED_USERNAME);

        // Get all the adminList where username equals to UPDATED_USERNAME
        defaultAdminShouldNotBeFound("username.in=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByUsernameIsNullOrNotNull() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username is not null
        defaultAdminShouldBeFound("username.specified=true");

        // Get all the adminList where username is null
        defaultAdminShouldNotBeFound("username.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdminsByUsernameContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username contains DEFAULT_USERNAME
        defaultAdminShouldBeFound("username.contains=" + DEFAULT_USERNAME);

        // Get all the adminList where username contains UPDATED_USERNAME
        defaultAdminShouldNotBeFound("username.contains=" + UPDATED_USERNAME);
    }

    @Test
    @Transactional
    public void getAllAdminsByUsernameNotContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where username does not contain DEFAULT_USERNAME
        defaultAdminShouldNotBeFound("username.doesNotContain=" + DEFAULT_USERNAME);

        // Get all the adminList where username does not contain UPDATED_USERNAME
        defaultAdminShouldBeFound("username.doesNotContain=" + UPDATED_USERNAME);
    }


    @Test
    @Transactional
    public void getAllAdminsByPasswordIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password equals to DEFAULT_PASSWORD
        defaultAdminShouldBeFound("password.equals=" + DEFAULT_PASSWORD);

        // Get all the adminList where password equals to UPDATED_PASSWORD
        defaultAdminShouldNotBeFound("password.equals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllAdminsByPasswordIsNotEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password not equals to DEFAULT_PASSWORD
        defaultAdminShouldNotBeFound("password.notEquals=" + DEFAULT_PASSWORD);

        // Get all the adminList where password not equals to UPDATED_PASSWORD
        defaultAdminShouldBeFound("password.notEquals=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllAdminsByPasswordIsInShouldWork() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password in DEFAULT_PASSWORD or UPDATED_PASSWORD
        defaultAdminShouldBeFound("password.in=" + DEFAULT_PASSWORD + "," + UPDATED_PASSWORD);

        // Get all the adminList where password equals to UPDATED_PASSWORD
        defaultAdminShouldNotBeFound("password.in=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllAdminsByPasswordIsNullOrNotNull() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password is not null
        defaultAdminShouldBeFound("password.specified=true");

        // Get all the adminList where password is null
        defaultAdminShouldNotBeFound("password.specified=false");
    }
                @Test
    @Transactional
    public void getAllAdminsByPasswordContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password contains DEFAULT_PASSWORD
        defaultAdminShouldBeFound("password.contains=" + DEFAULT_PASSWORD);

        // Get all the adminList where password contains UPDATED_PASSWORD
        defaultAdminShouldNotBeFound("password.contains=" + UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void getAllAdminsByPasswordNotContainsSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);

        // Get all the adminList where password does not contain DEFAULT_PASSWORD
        defaultAdminShouldNotBeFound("password.doesNotContain=" + DEFAULT_PASSWORD);

        // Get all the adminList where password does not contain UPDATED_PASSWORD
        defaultAdminShouldBeFound("password.doesNotContain=" + UPDATED_PASSWORD);
    }


    @Test
    @Transactional
    public void getAllAdminsByDoctorsIsEqualToSomething() throws Exception {
        // Initialize the database
        adminRepository.saveAndFlush(admin);
        Doctor doctors = DoctorResourceIT.createEntity(em);
        em.persist(doctors);
        em.flush();
        admin.addDoctors(doctors);
        adminRepository.saveAndFlush(admin);
        Long doctorsId = doctors.getId();

        // Get all the adminList where doctors equals to doctorsId
        defaultAdminShouldBeFound("doctorsId.equals=" + doctorsId);

        // Get all the adminList where doctors equals to doctorsId + 1
        defaultAdminShouldNotBeFound("doctorsId.equals=" + (doctorsId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultAdminShouldBeFound(String filter) throws Exception {
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(admin.getId().intValue())))
            .andExpect(jsonPath("$.[*].username").value(hasItem(DEFAULT_USERNAME)))
            .andExpect(jsonPath("$.[*].password").value(hasItem(DEFAULT_PASSWORD)));

        // Check, that the count call also returns 1
        restAdminMockMvc.perform(get("/api/admins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultAdminShouldNotBeFound(String filter) throws Exception {
        restAdminMockMvc.perform(get("/api/admins?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restAdminMockMvc.perform(get("/api/admins/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingAdmin() throws Exception {
        // Get the admin
        restAdminMockMvc.perform(get("/api/admins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAdmin() throws Exception {
        // Initialize the database
        adminService.save(admin);

        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Update the admin
        Admin updatedAdmin = adminRepository.findById(admin.getId()).get();
        // Disconnect from session so that the updates on updatedAdmin are not directly saved in db
        em.detach(updatedAdmin);
        updatedAdmin
            .username(UPDATED_USERNAME)
            .password(UPDATED_PASSWORD);

        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedAdmin)))
            .andExpect(status().isOk());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
        Admin testAdmin = adminList.get(adminList.size() - 1);
        assertThat(testAdmin.getUsername()).isEqualTo(UPDATED_USERNAME);
        assertThat(testAdmin.getPassword()).isEqualTo(UPDATED_PASSWORD);
    }

    @Test
    @Transactional
    public void updateNonExistingAdmin() throws Exception {
        int databaseSizeBeforeUpdate = adminRepository.findAll().size();

        // Create the Admin

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAdminMockMvc.perform(put("/api/admins")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(admin)))
            .andExpect(status().isBadRequest());

        // Validate the Admin in the database
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteAdmin() throws Exception {
        // Initialize the database
        adminService.save(admin);

        int databaseSizeBeforeDelete = adminRepository.findAll().size();

        // Delete the admin
        restAdminMockMvc.perform(delete("/api/admins/{id}", admin.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Admin> adminList = adminRepository.findAll();
        assertThat(adminList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
