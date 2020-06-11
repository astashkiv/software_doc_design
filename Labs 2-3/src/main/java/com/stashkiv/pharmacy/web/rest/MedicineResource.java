package com.stashkiv.pharmacy.web.rest;

import com.stashkiv.pharmacy.domain.Medicine;
import com.stashkiv.pharmacy.service.MedicineService;
import com.stashkiv.pharmacy.web.rest.errors.BadRequestAlertException;
import com.stashkiv.pharmacy.service.dto.MedicineCriteria;
import com.stashkiv.pharmacy.service.MedicineQueryService;

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
 * REST controller for managing {@link com.stashkiv.pharmacy.domain.Medicine}.
 */
@RestController
@RequestMapping("/api")
public class MedicineResource {

    private final Logger log = LoggerFactory.getLogger(MedicineResource.class);

    private static final String ENTITY_NAME = "medicine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MedicineService medicineService;

    private final MedicineQueryService medicineQueryService;

    public MedicineResource(MedicineService medicineService, MedicineQueryService medicineQueryService) {
        this.medicineService = medicineService;
        this.medicineQueryService = medicineQueryService;
    }

    /**
     * {@code POST  /medicines} : Create a new medicine.
     *
     * @param medicine the medicine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new medicine, or with status {@code 400 (Bad Request)} if the medicine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/medicines")
    public ResponseEntity<Medicine> createMedicine(@Valid @RequestBody Medicine medicine) throws URISyntaxException {
        log.debug("REST request to save Medicine : {}", medicine);
        if (medicine.getId() != null) {
            throw new BadRequestAlertException("A new medicine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Medicine result = medicineService.save(medicine);
        return ResponseEntity.created(new URI("/api/medicines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /medicines} : Updates an existing medicine.
     *
     * @param medicine the medicine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated medicine,
     * or with status {@code 400 (Bad Request)} if the medicine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the medicine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/medicines")
    public ResponseEntity<Medicine> updateMedicine(@Valid @RequestBody Medicine medicine) throws URISyntaxException {
        log.debug("REST request to update Medicine : {}", medicine);
        if (medicine.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Medicine result = medicineService.save(medicine);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, medicine.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /medicines} : get all the medicines.
     *
     * @param pageable the pagination information.
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of medicines in body.
     */
    @GetMapping("/medicines")
    public ResponseEntity<List<Medicine>> getAllMedicines(MedicineCriteria criteria, Pageable pageable) {
        log.debug("REST request to get Medicines by criteria: {}", criteria);
        Page<Medicine> page = medicineQueryService.findByCriteria(criteria, pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /medicines/count} : count all the medicines.
     *
     * @param criteria the criteria which the requested entities should match.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the count in body.
     */
    @GetMapping("/medicines/count")
    public ResponseEntity<Long> countMedicines(MedicineCriteria criteria) {
        log.debug("REST request to count Medicines by criteria: {}", criteria);
        return ResponseEntity.ok().body(medicineQueryService.countByCriteria(criteria));
    }

    /**
     * {@code GET  /medicines/:id} : get the "id" medicine.
     *
     * @param id the id of the medicine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the medicine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/medicines/{id}")
    public ResponseEntity<Medicine> getMedicine(@PathVariable Long id) {
        log.debug("REST request to get Medicine : {}", id);
        Optional<Medicine> medicine = medicineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(medicine);
    }

    /**
     * {@code DELETE  /medicines/:id} : delete the "id" medicine.
     *
     * @param id the id of the medicine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/medicines/{id}")
    public ResponseEntity<Void> deleteMedicine(@PathVariable Long id) {
        log.debug("REST request to delete Medicine : {}", id);
        medicineService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id.toString())).build();
    }
}
