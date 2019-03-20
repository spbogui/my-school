package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.SchoolSchoolYearDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing SchoolSchoolYear.
 */
public interface SchoolSchoolYearService {

    /**
     * Save a schoolSchoolYear.
     *
     * @param schoolSchoolYearDTO the entity to save
     * @return the persisted entity
     */
    SchoolSchoolYearDTO save(SchoolSchoolYearDTO schoolSchoolYearDTO);

    /**
     * Get all the schoolSchoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolSchoolYearDTO> findAll(Pageable pageable);


    /**
     * Get the "id" schoolSchoolYear.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<SchoolSchoolYearDTO> findOne(Long id);

    /**
     * Delete the "id" schoolSchoolYear.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the schoolSchoolYear corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SchoolSchoolYearDTO> search(String query, Pageable pageable);
}
