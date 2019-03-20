package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.LeaveHoliDayDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing LeaveHoliDay.
 */
public interface LeaveHoliDayService {

    /**
     * Save a leaveHoliDay.
     *
     * @param leaveHoliDayDTO the entity to save
     * @return the persisted entity
     */
    LeaveHoliDayDTO save(LeaveHoliDayDTO leaveHoliDayDTO);

    /**
     * Get all the leaveHoliDays.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveHoliDayDTO> findAll(Pageable pageable);


    /**
     * Get the "id" leaveHoliDay.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<LeaveHoliDayDTO> findOne(Long id);

    /**
     * Delete the "id" leaveHoliDay.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the leaveHoliDay corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<LeaveHoliDayDTO> search(String query, Pageable pageable);
}
