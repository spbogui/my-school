package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.Regimen;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing Regimen.
 */
public interface RegimenService {

    /**
     * Save a regimen.
     *
     * @param regimen the entity to save
     * @return the persisted entity
     */
    Regimen save(Regimen regimen);

    /**
     * Get all the regimen.
     *
     * @return the list of entities
     */
    List<Regimen> findAll();


    /**
     * Get the "id" regimen.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<Regimen> findOne(Long id);

    /**
     * Delete the "id" regimen.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the regimen corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<Regimen> search(String query);
}
