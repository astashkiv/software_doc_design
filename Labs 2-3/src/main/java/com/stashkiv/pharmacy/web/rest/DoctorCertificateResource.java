package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.domain.DoctorCertificate;
import com.stashkiv.pharmacy.service.DoctorCertificateService;
import com.stashkiv.pharmacy.web.rest.errors.BadRequestAlertException;
import com.stashkiv.pharmacy.service.dto.DoctorCertificateCriteria;
import com.stashkiv.pharmacy.service.DoctorCertificateQueryService;

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
 * REST controller for managing {@link com.stashkiv.pharmacy.domain.DoctorCertificate}.
 */
@RestController
@RequestMapping("/api")
public class DoctorCertificateResource {

    private final Logger log = LoggerFactory.getLogger(DoctorCertificateResource.class);

    private static final String ENTITY_NAME = "doctorCertificate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final DoctorCertificateService doctorCertificateService;

    private final DoctorCertificateQueryService doctorCertificateQueryService;

    public DoctorCertificateResource(DoctorCertificateService doctorCertificateService, DoctorCertificateQueryService doctorCertificateQueryService) {
        this.doctorCertificateService = doctorCertificateService;
        this.doctorCertificateQueryService = doctorCertificateQueryService;
    }

    /**
     * {@code POST  /doctor-certificates} : Create a new doctorCertificate.
     *
     * @param doctorCertificate the doctorCertificate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new doctorCertificate, or with status {@code 400 (Bad Request)} if the doctorCertificate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/doctor-certificates")
    public ResponseEntity<DoctorCertificate> createDoctorCertificate(@Valid @RequestBody DoctorCertificate doctorCertificate) throws URISyntaxException {
        log.debug("REST request to save DoctorCertificate : {}", doctorCertificate);
        if (doctorCertificate.getId() != null) {
            throw new BadRequestAlertException("A new doctorCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DoctorCertificate result = doctorCertificateService.save(doctorCertificate);
        return ResponseEntity.created(new URI("/api/doctor-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /doctor-certificates} : Updates an existing doctorCertificate.
     *
     * @param doctorCertificate the doctorCertificate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated doctorCertificate,
     * or with status {@code 400 (Bad Request)} if the doctorCertificate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the doctorCertificate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/doctor-certificates")
    public ResponseEntity<DoctorCertificate> updateDoctorCertificate(@Valid @RequestBody DoctorCertificate doctorCertificate) throws URISyntaxException {
        log.debug("REST request to update DoctorCertificate : {}", doctorCertificate);
        if (doctorCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DoctorCertificate result = doctorCertificateService.save(doctorCertificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, doctorCertificate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /doctor-certificates} : get all the doctorCertificates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of doctorCertificates in body.
     */
    @GetMapping("/doctor-certificates")
    public ResponseEntity<List<DoctorCertificate>> getAllDoctorCertificates(DoctorCertificateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get DoctorCertificates by criteria: {}", criteria);
        Page<DoctorCertificate> page = doctorCertificateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /doctor-certificates/count} : count all the doctorCertificates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/doctor-certificates/count")
    public ResponseEntity<Long> countDoctorCertificates(DoctorCertificateCriteria criteria) {
        log.debug("REST request to count DoctorCertificates by criteria: {}", criteria);
        return ResponseEntity.ok().body(doctorCertificateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /doctor-certificates/:id} : get the "id" doctorCertificate.
     *
     * @param id the id of the doctorCertificate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the doctorCertificate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/doctor-certificates/{id}")
    public ResponseEntity<DoctorCertificate> getDoctorCertificate(@PathVariable Long id) {
        log.debug("REST request to get DoctorCertificate : {}", id);
        Optional<DoctorCertificate> doctorCertificate = doctorCertificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(doctorCertificate);
    }

    /**
     * {@code DELETE  /doctor-certificates/:id} : delete the "id" doctorCertificate.
     *
     * @param id the id of the doctorCertificate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/doctor-certificates/{id}")
    public ResponseEntity<Void> deleteDoctorCertificate(@PathVariable Long id) {
        log.debug("REST request to delete DoctorCertificate : {}", id);
        doctorCertificateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
