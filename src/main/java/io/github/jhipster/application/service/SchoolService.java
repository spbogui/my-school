package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.SchoolDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing School.
 */
public interface SchoolService {

    /**
     * Save a school.
     *
     * @param schoolDTO the entity to save
     * @return the persisted entity
     */
    SchoolDTO save(SchoolDTO schoolDTO);

    /**
     * Get all the schools.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolDTO> findAll(Pageable pageable);


    /**
     * Get the "id" school.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SchoolDTO> findOne(Long id);

    /**
     * Delete the "id" school.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the school corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolDTO> search(String query, Pageable pageable);
}
