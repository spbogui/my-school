package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.RelationshipType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RelationshipType.
 */
public interface RelationshipTypeService {

    /**
     * Save a relationshipType.
     *
     * @param relationshipType the entity to save
     * @return the persisted entity
     */
    RelationshipType save(RelationshipType relationshipType);

    /**
     * Get all the relationshipTypes.
     *
     * @return the list of entities
     */
    List<RelationshipType> findAll();


    /**
     * Get the "id" relationshipType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RelationshipType> findOne(Long id);

    /**
     * Delete the "id" relationshipType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the relationshipType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<RelationshipType> search(String query);
}
