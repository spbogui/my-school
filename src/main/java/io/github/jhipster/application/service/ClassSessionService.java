package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ClassSessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClassSession.
 */
public interface ClassSessionService {

    /**
     * Save a classSession.
     *
     * @param classSessionDTO the entity to save
     * @return the persisted entity
     */
    ClassSessionDTO save(ClassSessionDTO classSessionDTO);

    /**
     * Get all the classSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClassSessionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classSession.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClassSessionDTO> findOne(Long id);

    /**
     * Delete the "id" classSession.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the classSession corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClassSessionDTO> search(String query, Pageable pageable);
}
