package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.domain.PatientCertificate;
import com.stashkiv.pharmacy.service.PatientCertificateService;
import com.stashkiv.pharmacy.web.rest.errors.BadRequestAlertException;
import com.stashkiv.pharmacy.service.dto.PatientCertificateCriteria;
import com.stashkiv.pharmacy.service.PatientCertificateQueryService;

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
 * REST controller for managing {@link com.stashkiv.pharmacy.domain.PatientCertificate}.
 */
@RestController
@RequestMapping("/api")
public class PatientCertificateResource {

    private final Logger log = LoggerFactory.getLogger(PatientCertificateResource.class);

    private static final String ENTITY_NAME = "patientCertificate";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final PatientCertificateService patientCertificateService;

    private final PatientCertificateQueryService patientCertificateQueryService;

    public PatientCertificateResource(PatientCertificateService patientCertificateService, PatientCertificateQueryService patientCertificateQueryService) {
        this.patientCertificateService = patientCertificateService;
        this.patientCertificateQueryService = patientCertificateQueryService;
    }

    /**
     * {@code POST  /patient-certificates} : Create a new patientCertificate.
     *
     * @param patientCertificate the patientCertificate to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new patientCertificate, or with status {@code 400 (Bad Request)} if the patientCertificate has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/patient-certificates")
    public ResponseEntity<PatientCertificate> createPatientCertificate(@Valid @RequestBody PatientCertificate patientCertificate) throws URISyntaxException {
        log.debug("REST request to save PatientCertificate : {}", patientCertificate);
        if (patientCertificate.getId() != null) {
            throw new BadRequestAlertException("A new patientCertificate cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PatientCertificate result = patientCertificateService.save(patientCertificate);
        return ResponseEntity.created(new URI("/api/patient-certificates/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /patient-certificates} : Updates an existing patientCertificate.
     *
     * @param patientCertificate the patientCertificate to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated patientCertificate,
     * or with status {@code 400 (Bad Request)} if the patientCertificate is not valid,
     * or with status {@code 500 (Internal Server Error)} if the patientCertificate couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/patient-certificates")
    public ResponseEntity<PatientCertificate> updatePatientCertificate(@Valid @RequestBody PatientCertificate patientCertificate) throws URISyntaxException {
        log.debug("REST request to update PatientCertificate : {}", patientCertificate);
        if (patientCertificate.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PatientCertificate result = patientCertificateService.save(patientCertificate);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, patientCertificate.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /patient-certificates} : get all the patientCertificates.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of patientCertificates in body.
     */
    @GetMapping("/patient-certificates")
    public ResponseEntity<List<PatientCertificate>> getAllPatientCertificates(PatientCertificateCriteria criteria, Pageable pageable) {
        log.debug("REST request to get PatientCertificates by criteria: {}", criteria);
        Page<PatientCertificate> page = patientCertificateQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /patient-certificates/count} : count all the patientCertificates.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/patient-certificates/count")
    public ResponseEntity<Long> countPatientCertificates(PatientCertificateCriteria criteria) {
        log.debug("REST request to count PatientCertificates by criteria: {}", criteria);
        return ResponseEntity.ok().body(patientCertificateQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /patient-certificates/:id} : get the "id" patientCertificate.
     *
     * @param id the id of the patientCertificate to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the patientCertificate, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/patient-certificates/{id}")
    public ResponseEntity<PatientCertificate> getPatientCertificate(@PathVariable Long id) {
        log.debug("REST request to get PatientCertificate : {}", id);
        Optional<PatientCertificate> patientCertificate = patientCertificateService.findOne(id);
        return ResponseUtil.wrapOrNotFound(patientCertificate);
    }

    /**
     * {@code DELETE  /patient-certificates/:id} : delete the "id" patientCertificate.
     *
     * @param id the id of the patientCertificate to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/patient-certificates/{id}")
    public ResponseEntity<Void> deletePatientCertificate(@PathVariable Long id) {
        log.debug("REST request to delete PatientCertificate : {}", id);
        patientCertificateService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
