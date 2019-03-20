package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.PeriodDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Period.
 */
public interface PeriodService {

    /**
     * Save a period.
     *
     * @param periodDTO the entity to save
     * @return the persisted entity
     */
    PeriodDTO save(PeriodDTO periodDTO);

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeriodDTO> findAll(Pageable pageable);


    /**
     * Get the "id" period.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PeriodDTO> findOne(Long id);

    /**
     * Delete the "id" period.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the period corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PeriodDTO> search(String query, Pageable pageable);
}
