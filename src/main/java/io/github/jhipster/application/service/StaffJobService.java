package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.StaffJobDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing StaffJob.
 */
public interface StaffJobService {

    /**
     * Save a staffJob.
     *
     * @param staffJobDTO the entity to save
     * @return the persisted entity
     */
    StaffJobDTO save(StaffJobDTO staffJobDTO);

    /**
     * Get all the staffJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StaffJobDTO> findAll(Pageable pageable);


    /**
     * Get the "id" staffJob.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<StaffJobDTO> findOne(Long id);

    /**
     * Delete the "id" staffJob.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the staffJob corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<StaffJobDTO> search(String query, Pageable pageable);
}
