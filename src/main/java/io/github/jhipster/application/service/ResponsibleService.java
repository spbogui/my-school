package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ResponsibleDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Responsible.
 */
public interface ResponsibleService {

    /**
     * Save a responsible.
     *
     * @param responsibleDTO the entity to save
     * @return the persisted entity
     */
    ResponsibleDTO save(ResponsibleDTO responsibleDTO);

    /**
     * Get all the responsibles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResponsibleDTO> findAll(Pageable pageable);


    /**
     * Get the "id" responsible.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ResponsibleDTO> findOne(Long id);

    /**
     * Delete the "id" responsible.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the responsible corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ResponsibleDTO> search(String query, Pageable pageable);
}
