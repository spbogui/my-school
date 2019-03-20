package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.StudentDiplomaDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StudentDiploma.
 */
public interface StudentDiplomaService {

    /**
     * Save a studentDiploma.
     *
     * @param studentDiplomaDTO the entity to save
     * @return the persisted entity
     */
    StudentDiplomaDTO save(StudentDiplomaDTO studentDiplomaDTO);

    /**
     * Get all the studentDiplomas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentDiplomaDTO> findAll(Pageable pageable);


    /**
     * Get the "id" studentDiploma.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StudentDiplomaDTO> findOne(Long id);

    /**
     * Delete the "id" studentDiploma.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the studentDiploma corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StudentDiplomaDTO> search(String query, Pageable pageable);
}
