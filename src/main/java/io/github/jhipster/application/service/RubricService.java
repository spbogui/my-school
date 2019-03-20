package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Rubric;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Rubric.
 */
public interface RubricService {

    /**
     * Save a rubric.
     *
     * @param rubric the entity to save
     * @return the persisted entity
     */
    Rubric save(Rubric rubric);

    /**
     * Get all the rubrics.
     *
     * @return the list of entities
     */
    List<Rubric> findAll();


    /**
     * Get the "id" rubric.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Rubric> findOne(Long id);

    /**
     * Delete the "id" rubric.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rubric corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Rubric> search(String query);
}
