package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.ClassSessionType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing ClassSessionType.
 */
public interface ClassSessionTypeService {

    /**
     * Save a classSessionType.
     *
     * @param classSessionType the entity to save
     * @return the persisted entity
     */
    ClassSessionType save(ClassSessionType classSessionType);

    /**
     * Get all the classSessionTypes.
     *
     * @return the list of entities
     */
    List<ClassSessionType> findAll();


    /**
     * Get the "id" classSessionType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClassSessionType> findOne(Long id);

    /**
     * Delete the "id" classSessionType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the classSessionType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<ClassSessionType> search(String query);
}
