package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.EvaluationMode;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing EvaluationMode.
 */
public interface EvaluationModeService {

    /**
     * Save a evaluationMode.
     *
     * @param evaluationMode the entity to save
     * @return the persisted entity
     */
    EvaluationMode save(EvaluationMode evaluationMode);

    /**
     * Get all the evaluationModes.
     *
     * @return the list of entities
     */
    List<EvaluationMode> findAll();


    /**
     * Get the "id" evaluationMode.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EvaluationMode> findOne(Long id);

    /**
     * Delete the "id" evaluationMode.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the evaluationMode corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<EvaluationMode> search(String query);
}
