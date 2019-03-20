package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.StudentEvaluationDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentEvaluation.
 */
public interface StudentEvaluationService {

    /**
     * Save a studentEvaluation.
     *
     * @param studentEvaluationDTO the entity to save
     * @return the persisted entity
     */
    StudentEvaluationDTO save(StudentEvaluationDTO studentEvaluationDTO);

    /**
     * Get all the studentEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentEvaluationDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentEvaluation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudentEvaluationDTO> findOne(Long id);

    /**
     * Delete the "id" studentEvaluation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the studentEvaluation corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentEvaluationDTO> search(String query, Pageable pageable);
}
