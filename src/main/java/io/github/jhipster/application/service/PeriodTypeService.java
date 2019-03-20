package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.PeriodType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing PeriodType.
 */
public interface PeriodTypeService {

    /**
     * Save a periodType.
     *
     * @param periodType the entity to save
     * @return the persisted entity
     */
    PeriodType save(PeriodType periodType);

    /**
     * Get all the periodTypes.
     *
     * @return the list of entities
     */
    List<PeriodType> findAll();


    /**
     * Get the "id" periodType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<PeriodType> findOne(Long id);

    /**
     * Delete the "id" periodType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the periodType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<PeriodType> search(String query);
}
