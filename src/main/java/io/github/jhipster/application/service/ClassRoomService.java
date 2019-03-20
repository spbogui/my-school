package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.ClassRoomDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing ClassRoom.
 */
public interface ClassRoomService {

    /**
     * Save a classRoom.
     *
     * @param classRoomDTO the entity to save
     * @return the persisted entity
     */
    ClassRoomDTO save(ClassRoomDTO classRoomDTO);

    /**
     * Get all the classRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClassRoomDTO> findAll(Pageable pageable);


    /**
     * Get the "id" classRoom.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<ClassRoomDTO> findOne(Long id);

    /**
     * Delete the "id" classRoom.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the classRoom corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<ClassRoomDTO> search(String query, Pageable pageable);
}
