package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.PharmacyApp;
import com.stashkiv.pharmacy.domain.Booking;
import com.stashkiv.pharmacy.domain.Doctor;
import com.stashkiv.pharmacy.domain.Patient;
import com.stashkiv.pharmacy.repository.BookingRepository;
import com.stashkiv.pharmacy.service.BookingService;
import com.stashkiv.pharmacy.service.dto.BookingCriteria;
import com.stashkiv.pharmacy.service.BookingQueryService;

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
import java.time.Instant;
import java.time.ZonedDateTime;
import java.time.ZoneOffset;
import java.time.ZoneId;
import java.util.List;

import static com.stashkiv.pharmacy.web.rest.TestUtil.sameInstant;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the {@link BookingResource} REST controller.
 */
@SpringBootTest(classes = PharmacyApp.class)

@AutoConfigureMockMvc
@WithMockUser
public class BookingResourceIT {

    private static final ZonedDateTime DEFAULT_WHEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(0L), ZoneOffset.UTC);
    private static final ZonedDateTime UPDATED_WHEN = ZonedDateTime.now(ZoneId.systemDefault()).withNano(0);
    private static final ZonedDateTime SMALLER_WHEN = ZonedDateTime.ofInstant(Instant.ofEpochMilli(-1L), ZoneOffset.UTC);

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingQueryService bookingQueryService;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restBookingMockMvc;

    private Booking booking;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createEntity(EntityManager em) {
        Booking booking = new Booking()
            .when(DEFAULT_WHEN);
        return booking;
    }
    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Booking createUpdatedEntity(EntityManager em) {
        Booking booking = new Booking()
            .when(UPDATED_WHEN);
        return booking;
    }

    @BeforeEach
    public void initTest() {
        booking = createEntity(em);
    }

    @Test
    @Transactional
    public void createBooking() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isCreated());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate + 1);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getWhen()).isEqualTo(DEFAULT_WHEN);
    }

    @Test
    @Transactional
    public void createBookingWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = bookingRepository.findAll().size();

        // Create the Booking with an existing ID
        booking.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restBookingMockMvc.perform(post("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeCreate);
    }


    @Test
    @Transactional
    public void getAllBookings() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].when").value(hasItem(sameInstant(DEFAULT_WHEN))));
    }
    
    @Test
    @Transactional
    public void getBooking() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", booking.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(booking.getId().intValue()))
            .andExpect(jsonPath("$.when").value(sameInstant(DEFAULT_WHEN)));
    }


    @Test
    @Transactional
    public void getBookingsByIdFiltering() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        Long id = booking.getId();

        defaultBookingShouldBeFound("id.equals=" + id);
        defaultBookingShouldNotBeFound("id.notEquals=" + id);

        defaultBookingShouldBeFound("id.greaterThanOrEqual=" + id);
        defaultBookingShouldNotBeFound("id.greaterThan=" + id);

        defaultBookingShouldBeFound("id.lessThanOrEqual=" + id);
        defaultBookingShouldNotBeFound("id.lessThan=" + id);
    }


    @Test
    @Transactional
    public void getAllBookingsByWhenIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when equals to DEFAULT_WHEN
        defaultBookingShouldBeFound("when.equals=" + DEFAULT_WHEN);

        // Get all the bookingList where when equals to UPDATED_WHEN
        defaultBookingShouldNotBeFound("when.equals=" + UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsNotEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when not equals to DEFAULT_WHEN
        defaultBookingShouldNotBeFound("when.notEquals=" + DEFAULT_WHEN);

        // Get all the bookingList where when not equals to UPDATED_WHEN
        defaultBookingShouldBeFound("when.notEquals=" + UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsInShouldWork() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when in DEFAULT_WHEN or UPDATED_WHEN
        defaultBookingShouldBeFound("when.in=" + DEFAULT_WHEN + "," + UPDATED_WHEN);

        // Get all the bookingList where when equals to UPDATED_WHEN
        defaultBookingShouldNotBeFound("when.in=" + UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsNullOrNotNull() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when is not null
        defaultBookingShouldBeFound("when.specified=true");

        // Get all the bookingList where when is null
        defaultBookingShouldNotBeFound("when.specified=false");
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsGreaterThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when is greater than or equal to DEFAULT_WHEN
        defaultBookingShouldBeFound("when.greaterThanOrEqual=" + DEFAULT_WHEN);

        // Get all the bookingList where when is greater than or equal to UPDATED_WHEN
        defaultBookingShouldNotBeFound("when.greaterThanOrEqual=" + UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsLessThanOrEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when is less than or equal to DEFAULT_WHEN
        defaultBookingShouldBeFound("when.lessThanOrEqual=" + DEFAULT_WHEN);

        // Get all the bookingList where when is less than or equal to SMALLER_WHEN
        defaultBookingShouldNotBeFound("when.lessThanOrEqual=" + SMALLER_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsLessThanSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when is less than DEFAULT_WHEN
        defaultBookingShouldNotBeFound("when.lessThan=" + DEFAULT_WHEN);

        // Get all the bookingList where when is less than UPDATED_WHEN
        defaultBookingShouldBeFound("when.lessThan=" + UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void getAllBookingsByWhenIsGreaterThanSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);

        // Get all the bookingList where when is greater than DEFAULT_WHEN
        defaultBookingShouldNotBeFound("when.greaterThan=" + DEFAULT_WHEN);

        // Get all the bookingList where when is greater than SMALLER_WHEN
        defaultBookingShouldBeFound("when.greaterThan=" + SMALLER_WHEN);
    }


    @Test
    @Transactional
    public void getAllBookingsByToWhoIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);
        Doctor toWho = DoctorResourceIT.createEntity(em);
        em.persist(toWho);
        em.flush();
        booking.setToWho(toWho);
        bookingRepository.saveAndFlush(booking);
        Long toWhoId = toWho.getId();

        // Get all the bookingList where toWho equals to toWhoId
        defaultBookingShouldBeFound("toWhoId.equals=" + toWhoId);

        // Get all the bookingList where toWho equals to toWhoId + 1
        defaultBookingShouldNotBeFound("toWhoId.equals=" + (toWhoId + 1));
    }


    @Test
    @Transactional
    public void getAllBookingsByByWhoIsEqualToSomething() throws Exception {
        // Initialize the database
        bookingRepository.saveAndFlush(booking);
        Patient byWho = PatientResourceIT.createEntity(em);
        em.persist(byWho);
        em.flush();
        booking.setByWho(byWho);
        bookingRepository.saveAndFlush(booking);
        Long byWhoId = byWho.getId();

        // Get all the bookingList where byWho equals to byWhoId
        defaultBookingShouldBeFound("byWhoId.equals=" + byWhoId);

        // Get all the bookingList where byWho equals to byWhoId + 1
        defaultBookingShouldNotBeFound("byWhoId.equals=" + (byWhoId + 1));
    }

    /**
     * Executes the search, and checks that the default entity is returned.
     */
    private void defaultBookingShouldBeFound(String filter) throws Exception {
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(booking.getId().intValue())))
            .andExpect(jsonPath("$.[*].when").value(hasItem(sameInstant(DEFAULT_WHEN))));

        // Check, that the count call also returns 1
        restBookingMockMvc.perform(get("/api/bookings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("1"));
    }

    /**
     * Executes the search, and checks that the default entity is not returned.
     */
    private void defaultBookingShouldNotBeFound(String filter) throws Exception {
        restBookingMockMvc.perform(get("/api/bookings?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$").isArray())
            .andExpect(jsonPath("$").isEmpty());

        // Check, that the count call also returns 0
        restBookingMockMvc.perform(get("/api/bookings/count?sort=id,desc&" + filter))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(content().string("0"));
    }


    @Test
    @Transactional
    public void getNonExistingBooking() throws Exception {
        // Get the booking
        restBookingMockMvc.perform(get("/api/bookings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Update the booking
        Booking updatedBooking = bookingRepository.findById(booking.getId()).get();
        // Disconnect from session so that the updates on updatedBooking are not directly saved in db
        em.detach(updatedBooking);
        updatedBooking
            .when(UPDATED_WHEN);

        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(updatedBooking)))
            .andExpect(status().isOk());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
        Booking testBooking = bookingList.get(bookingList.size() - 1);
        assertThat(testBooking.getWhen()).isEqualTo(UPDATED_WHEN);
    }

    @Test
    @Transactional
    public void updateNonExistingBooking() throws Exception {
        int databaseSizeBeforeUpdate = bookingRepository.findAll().size();

        // Create the Booking

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restBookingMockMvc.perform(put("/api/bookings")
            .contentType(MediaType.APPLICATION_JSON)
            .content(TestUtil.convertObjectToJsonBytes(booking)))
            .andExpect(status().isBadRequest());

        // Validate the Booking in the database
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteBooking() throws Exception {
        // Initialize the database
        bookingService.save(booking);

        int databaseSizeBeforeDelete = bookingRepository.findAll().size();

        // Delete the booking
        restBookingMockMvc.perform(delete("/api/bookings/{id}", booking.getId())
            .accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        List<Booking> bookingList = bookingRepository.findAll();
        assertThat(bookingList).hasSize(databaseSizeBeforeDelete - 1);
    }
}
