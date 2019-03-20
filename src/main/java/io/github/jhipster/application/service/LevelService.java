package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Level;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Level.
 */
public interface LevelService {

    /**
     * Save a level.
     *
     * @param level the entity to save
     * @return the persisted entity
     */
    Level save(Level level);

    /**
     * Get all the levels.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Level> findAll(Pageable pageable);


    /**
     * Get the "id" level.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Level> findOne(Long id);

    /**
     * Delete the "id" level.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the level corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<Level> search(String query, Pageable pageable);
}
