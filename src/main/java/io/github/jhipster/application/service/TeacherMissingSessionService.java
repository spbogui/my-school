package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TeacherMissingSessionDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TeacherMissingSession.
 */
public interface TeacherMissingSessionService {

    /**
     * Save a teacherMissingSession.
     *
     * @param teacherMissingSessionDTO the entity to save
     * @return the persisted entity
     */
    TeacherMissingSessionDTO save(TeacherMissingSessionDTO teacherMissingSessionDTO);

    /**
     * Get all the teacherMissingSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherMissingSessionDTO> findAll(Pageable pageable);


    /**
     * Get the "id" teacherMissingSession.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeacherMissingSessionDTO> findOne(Long id);

    /**
     * Delete the "id" teacherMissingSession.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the teacherMissingSession corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherMissingSessionDTO> search(String query, Pageable pageable);
}
