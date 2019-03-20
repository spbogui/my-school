package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.IdentifierType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing IdentifierType.
 */
public interface IdentifierTypeService {

    /**
     * Save a identifierType.
     *
     * @param identifierType the entity to save
     * @return the persisted entity
     */
    IdentifierType save(IdentifierType identifierType);

    /**
     * Get all the identifierTypes.
     *
     * @return the list of entities
     */
    List<IdentifierType> findAll();


    /**
     * Get the "id" identifierType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<IdentifierType> findOne(Long id);

    /**
     * Delete the "id" identifierType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the identifierType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<IdentifierType> search(String query);
}
