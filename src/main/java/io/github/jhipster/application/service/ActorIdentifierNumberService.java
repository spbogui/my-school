package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ActorIdentifierNumberDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ActorIdentifierNumber.
 */
public interface ActorIdentifierNumberService {

    /**
     * Save a actorIdentifierNumber.
     *
     * @param actorIdentifierNumberDTO the entity to save
     * @return the persisted entity
     */
    ActorIdentifierNumberDTO save(ActorIdentifierNumberDTO actorIdentifierNumberDTO);

    /**
     * Get all the actorIdentifierNumbers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorIdentifierNumberDTO> findAll(Pageable pageable);


    /**
     * Get the "id" actorIdentifierNumber.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActorIdentifierNumberDTO> findOne(Long id);

    /**
     * Delete the "id" actorIdentifierNumber.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the actorIdentifierNumber corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorIdentifierNumberDTO> search(String query, Pageable pageable);
}
