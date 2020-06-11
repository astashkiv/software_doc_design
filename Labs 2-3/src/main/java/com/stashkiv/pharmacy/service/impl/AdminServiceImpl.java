package com.stashkiv.pharmacy.service.impl;

import com.stashkiv.pharmacy.service.AdminService;
import com.stashkiv.pharmacy.domain.Admin;
import com.stashkiv.pharmacy.repository.AdminRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Admin}.
 */
@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    private final Logger log = LoggerFactory.getLogger(AdminServiceImpl.class);

    private final AdminRepository adminRepository;

    public AdminServiceImpl(AdminRepository adminRepository) {
        this.adminRepository = adminRepository;
    }

    /**
     * Save a admin.
     *
     * @param admin the entity to save.
     * @return the persisted entity.
     */
    @Override
    public Admin save(Admin admin) {
        log.debug("Request to save Admin : {}", admin);
        return adminRepository.save(admin);
    }

    /**
     * Get all the admins.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Override
    @Transactional(readOnly = true)
    public Page<Admin> findAll(Pageable pageable) {
        log.debug("Request to get all Admins");
        return adminRepository.findAll(pageable);
    }

    /**
     * Get one admin by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Admin> findOne(Long id) {
        log.debug("Request to get Admin : {}", id);
        return adminRepository.findById(id);
    }

    /**
     * Delete the admin by id.
     *
     * @param id the id of the entity.
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Admin : {}", id);
        adminRepository.deleteById(id);
    }
}
