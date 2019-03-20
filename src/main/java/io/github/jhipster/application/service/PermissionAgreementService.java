package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.PermissionAgreementDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing PermissionAgreement.
 */
public interface PermissionAgreementService {

    /**
     * Save a permissionAgreement.
     *
     * @param permissionAgreementDTO the entity to save
     * @return the persisted entity
     */
    PermissionAgreementDTO save(PermissionAgreementDTO permissionAgreementDTO);

    /**
     * Get all the permissionAgreements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PermissionAgreementDTO> findAll(Pageable pageable);


    /**
     * Get the "id" permissionAgreement.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PermissionAgreementDTO> findOne(Long id);

    /**
     * Delete the "id" permissionAgreement.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the permissionAgreement corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PermissionAgreementDTO> search(String query, Pageable pageable);
}
