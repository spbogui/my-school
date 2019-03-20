package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.DiplomaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Diploma.
 */
public interface DiplomaService {

    /**
     * Save a diploma.
     *
     * @param diplomaDTO the entity to save
     * @return the persisted entity
     */
    DiplomaDTO save(DiplomaDTO diplomaDTO);

    /**
     * Get all the diplomas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiplomaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" diploma.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<DiplomaDTO> findOne(Long id);

    /**
     * Delete the "id" diploma.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the diploma corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiplomaDTO> search(String query, Pageable pageable);
}
