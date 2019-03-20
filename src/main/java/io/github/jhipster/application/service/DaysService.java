package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Days;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Days.
 */
public interface DaysService {

    /**
     * Save a days.
     *
     * @param days the entity to save
     * @return the persisted entity
     */
    Days save(Days days);

    /**
     * Get all the days.
     *
     * @return the list of entities
     */
    List<Days> findAll();


    /**
     * Get the "id" days.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Days> findOne(Long id);

    /**
     * Delete the "id" days.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the days corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Days> search(String query);
}
