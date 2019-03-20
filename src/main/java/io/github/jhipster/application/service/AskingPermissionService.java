package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.AskingPermissionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing AskingPermission.
 */
public interface AskingPermissionService {

    /**
     * Save a askingPermission.
     *
     * @param askingPermissionDTO the entity to save
     * @return the persisted entity
     */
    AskingPermissionDTO save(AskingPermissionDTO askingPermissionDTO);

    /**
     * Get all the askingPermissions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AskingPermissionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" askingPermission.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<AskingPermissionDTO> findOne(Long id);

    /**
     * Delete the "id" askingPermission.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the askingPermission corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<AskingPermissionDTO> search(String query, Pageable pageable);
}
