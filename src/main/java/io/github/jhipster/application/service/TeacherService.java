package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TeacherDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Teacher.
 */
public interface TeacherService {

    /**
     * Save a teacher.
     *
     * @param teacherDTO the entity to save
     * @return the persisted entity
     */
    TeacherDTO save(TeacherDTO teacherDTO);

    /**
     * Get all the teachers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherDTO> findAll(Pageable pageable);

    /**
     * Get all the Teacher with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    Page<TeacherDTO> findAllWithEagerRelationships(Pageable pageable);
    
    /**
     * Get the "id" teacher.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeacherDTO> findOne(Long id);

    /**
     * Delete the "id" teacher.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the teacher corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherDTO> search(String query, Pageable pageable);
}
