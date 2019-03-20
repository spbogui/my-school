package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TeacherSubjectSchoolYearService;
import io.github.jhipster.application.domain.TeacherSubjectSchoolYear;
import io.github.jhipster.application.repository.TeacherSubjectSchoolYearRepository;
import io.github.jhipster.application.repository.search.TeacherSubjectSchoolYearSearchRepository;
import io.github.jhipster.application.service.dto.TeacherSubjectSchoolYearDTO;
import io.github.jhipster.application.service.mapper.TeacherSubjectSchoolYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TeacherSubjectSchoolYear.
 */
@Service
@Transactional
public class TeacherSubjectSchoolYearServiceImpl implements TeacherSubjectSchoolYearService {

    private final Logger log = LoggerFactory.getLogger(TeacherSubjectSchoolYearServiceImpl.class);

    private final TeacherSubjectSchoolYearRepository teacherSubjectSchoolYearRepository;

    private final TeacherSubjectSchoolYearMapper teacherSubjectSchoolYearMapper;

    private final TeacherSubjectSchoolYearSearchRepository teacherSubjectSchoolYearSearchRepository;

    public TeacherSubjectSchoolYearServiceImpl(TeacherSubjectSchoolYearRepository teacherSubjectSchoolYearRepository, TeacherSubjectSchoolYearMapper teacherSubjectSchoolYearMapper, TeacherSubjectSchoolYearSearchRepository teacherSubjectSchoolYearSearchRepository) {
        this.teacherSubjectSchoolYearRepository = teacherSubjectSchoolYearRepository;
        this.teacherSubjectSchoolYearMapper = teacherSubjectSchoolYearMapper;
        this.teacherSubjectSchoolYearSearchRepository = teacherSubjectSchoolYearSearchRepository;
    }

    /**
     * Save a teacherSubjectSchoolYear.
     *
     * @param teacherSubjectSchoolYearDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeacherSubjectSchoolYearDTO save(TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO) {
        log.debug("Request to save TeacherSubjectSchoolYear : {}", teacherSubjectSchoolYearDTO);
        TeacherSubjectSchoolYear teacherSubjectSchoolYear = teacherSubjectSchoolYearMapper.toEntity(teacherSubjectSchoolYearDTO);
        teacherSubjectSchoolYear = teacherSubjectSchoolYearRepository.save(teacherSubjectSchoolYear);
        TeacherSubjectSchoolYearDTO result = teacherSubjectSchoolYearMapper.toDto(teacherSubjectSchoolYear);
        teacherSubjectSchoolYearSearchRepository.save(teacherSubjectSchoolYear);
        return result;
    }

    /**
     * Get all the teacherSubjectSchoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherSubjectSchoolYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeacherSubjectSchoolYears");
        return teacherSubjectSchoolYearRepository.findAll(pageable)
            .map(teacherSubjectSchoolYearMapper::toDto);
    }


    /**
     * Get one teacherSubjectSchoolYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeacherSubjectSchoolYearDTO> findOne(Long id) {
        log.debug("Request to get TeacherSubjectSchoolYear : {}", id);
        return teacherSubjectSchoolYearRepository.findById(id)
            .map(teacherSubjectSchoolYearMapper::toDto);
    }

    /**
     * Delete the teacherSubjectSchoolYear by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeacherSubjectSchoolYear : {}", id);
        teacherSubjectSchoolYearRepository.deleteById(id);
        teacherSubjectSchoolYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the teacherSubjectSchoolYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherSubjectSchoolYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TeacherSubjectSchoolYears for query {}", query);
        return teacherSubjectSchoolYearSearchRepository.search(queryStringQuery(query), pageable)
            .map(teacherSubjectSchoolYearMapper::toDto);
    }
}
