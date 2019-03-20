package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.RubricAmountDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RubricAmount.
 */
public interface RubricAmountService {

    /**
     * Save a rubricAmount.
     *
     * @param rubricAmountDTO the entity to save
     * @return the persisted entity
     */
    RubricAmountDTO save(RubricAmountDTO rubricAmountDTO);

    /**
     * Get all the rubricAmounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RubricAmountDTO> findAll(Pageable pageable);


    /**
     * Get the "id" rubricAmount.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RubricAmountDTO> findOne(Long id);

    /**
     * Delete the "id" rubricAmount.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the rubricAmount corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RubricAmountDTO> search(String query, Pageable pageable);
}
