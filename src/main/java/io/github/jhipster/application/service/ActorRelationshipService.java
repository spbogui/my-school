package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ActorRelationshipDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ActorRelationship.
 */
public interface ActorRelationshipService {

    /**
     * Save a actorRelationship.
     *
     * @param actorRelationshipDTO the entity to save
     * @return the persisted entity
     */
    ActorRelationshipDTO save(ActorRelationshipDTO actorRelationshipDTO);

    /**
     * Get all the actorRelationships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorRelationshipDTO> findAll(Pageable pageable);


    /**
     * Get the "id" actorRelationship.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ActorRelationshipDTO> findOne(Long id);

    /**
     * Delete the "id" actorRelationship.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the actorRelationship corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ActorRelationshipDTO> search(String query, Pageable pageable);
}
