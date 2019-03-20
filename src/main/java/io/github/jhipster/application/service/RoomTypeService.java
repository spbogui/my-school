package io.github.jhipster.application.service;

import io.github.jhipster.application.domain.RoomType;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RoomType.
 */
public interface RoomTypeService {

    /**
     * Save a roomType.
     *
     * @param roomType the entity to save
     * @return the persisted entity
     */
    RoomType save(RoomType roomType);

    /**
     * Get all the roomTypes.
     *
     * @return the list of entities
     */
    List<RoomType> findAll();


    /**
     * Get the "id" roomType.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RoomType> findOne(Long id);

    /**
     * Delete the "id" roomType.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the roomType corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @return the list of entities
     */
    List<RoomType> search(String query);
}
