package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.EvaluationType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EvaluationType.
 */
public interface EvaluationTypeService {

    /**
     * Save a evaluationType.
     *
     * @param evaluationType the entity to save
     * @return the persisted entity
     */
    EvaluationType save(EvaluationType evaluationType);

    /**
     * Get all the evaluationTypes.
     *
     * @return the list of entities
     */
    List<EvaluationType> findAll();


    /**
     * Get the "id" evaluationType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EvaluationType> findOne(Long id);

    /**
     * Delete the "id" evaluationType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EvaluationType> search(String query);
}
