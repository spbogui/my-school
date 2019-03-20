package io.github.jhipster.application.service;

import io.github.jhipster.application.service.dto.TeacherSubjectSchoolYearDTO;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing TeacherSubjectSchoolYear.
 */
public interface TeacherSubjectSchoolYearService {

    /**
     * Save a teacherSubjectSchoolYear.
     *
     * @param teacherSubjectSchoolYearDTO the entity to save
     * @return the persisted entity
     */
    TeacherSubjectSchoolYearDTO save(TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO);

    /**
     * Get all the teacherSubjectSchoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherSubjectSchoolYearDTO> findAll(Pageable pageable);


    /**
     * Get the "id" teacherSubjectSchoolYear.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<TeacherSubjectSchoolYearDTO> findOne(Long id);

    /**
     * Delete the "id" teacherSubjectSchoolYear.
     *
     * @param id the id of the entity
     */
    void delete(Long id);

    /**
     * Search for the teacherSubjectSchoolYear corresponding to the query.
     *
     * @param query the query of the search
     * 
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<TeacherSubjectSchoolYearDTO> search(String query, Pageable pageable);
}
