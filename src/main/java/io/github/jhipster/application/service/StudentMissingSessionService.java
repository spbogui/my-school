package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.StudentMissingSessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentMissingSession.
 */
public interface StudentMissingSessionService {

    /**
     * Save a studentMissingSession.
     *
     * @param studentMissingSessionDTO the entity to save
     * @return the persisted entity
     */
    StudentMissingSessionDTO save(StudentMissingSessionDTO studentMissingSessionDTO);

    /**
     * Get all the studentMissingSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentMissingSessionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentMissingSession.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudentMissingSessionDTO> findOne(Long id);

    /**
     * Delete the "id" studentMissingSession.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the studentMissingSession corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentMissingSessionDTO> search(String query, Pageable pageable);
}
