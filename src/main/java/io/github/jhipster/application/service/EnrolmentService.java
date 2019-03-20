package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.EnrolmentDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing Enrolment.
 */
public interface EnrolmentService {

    /**
     * Save a enrolment.
     *
     * @param enrolmentDTO the entity to save
     * @return the persisted entity
     */
    EnrolmentDTO save(EnrolmentDTO enrolmentDTO);

    /**
     * Get all the enrolments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EnrolmentDTO> findAll(Pageable pageable);


    /**
     * Get the "id" enrolment.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<EnrolmentDTO> findOne(Long id);

    /**
     * Delete the "id" enrolment.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the enrolment corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<EnrolmentDTO> search(String query, Pageable pageable);
}
