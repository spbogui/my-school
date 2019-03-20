package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.SchoolYearDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SchoolYear.
 */
public interface SchoolYearService {

    /**
     * Save a schoolYear.
     *
     * @param schoolYearDTO the entity to save
     * @return the persisted entity
     */
    SchoolYearDTO save(SchoolYearDTO schoolYearDTO);

    /**
     * Get all the schoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolYearDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schoolYear.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SchoolYearDTO> findOne(Long id);

    /**
     * Delete the "id" schoolYear.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the schoolYear corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolYearDTO> search(String query, Pageable pageable);
}
