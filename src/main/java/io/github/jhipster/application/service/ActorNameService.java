package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ActorNameDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ActorName.
 */
public interface ActorNameService {

    /**
     * Save a actorName.
     *
     * @param actorNameDTO the entity to save
     * @return the persisted entity
     */
    ActorNameDTO save(ActorNameDTO actorNameDTO);

    /**
     * Get all the actorNames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorNameDTO> findAll(Pageable pageable);


    /**
     * Get the "id" actorName.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActorNameDTO> findOne(Long id);

    /**
     * Delete the "id" actorName.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the actorName corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorNameDTO> search(String query, Pageable pageable);
}
