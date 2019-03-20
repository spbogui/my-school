package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.StudentDiplomaService;
import io.github.jhipster.application.domain.StudentDiploma;
import io.github.jhipster.application.repository.StudentDiplomaRepository;
import io.github.jhipster.application.repository.search.StudentDiplomaSearchRepository;
import io.github.jhipster.application.service.dto.StudentDiplomaDTO;
import io.github.jhipster.application.service.mapper.StudentDiplomaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentDiploma.
 */
@Service
@Transactional
public class StudentDiplomaServiceImpl implements StudentDiplomaService {

    private final Logger log = LoggerFactory.getLogger(StudentDiplomaServiceImpl.class);

    private final StudentDiplomaRepository studentDiplomaRepository;

    private final StudentDiplomaMapper studentDiplomaMapper;

    private final StudentDiplomaSearchRepository studentDiplomaSearchRepository;

    public StudentDiplomaServiceImpl(StudentDiplomaRepository studentDiplomaRepository, StudentDiplomaMapper studentDiplomaMapper, StudentDiplomaSearchRepository studentDiplomaSearchRepository) {
        this.studentDiplomaRepository = studentDiplomaRepository;
        this.studentDiplomaMapper = studentDiplomaMapper;
        this.studentDiplomaSearchRepository = studentDiplomaSearchRepository;
    }

    /**
     * Save a studentDiploma.
     *
     * @param studentDiplomaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentDiplomaDTO save(StudentDiplomaDTO studentDiplomaDTO) {
        log.debug("Request to save StudentDiploma : {}", studentDiplomaDTO);
        StudentDiploma studentDiploma = studentDiplomaMapper.toEntity(studentDiplomaDTO);
        studentDiploma = studentDiplomaRepository.save(studentDiploma);
        StudentDiplomaDTO result = studentDiplomaMapper.toDto(studentDiploma);
        studentDiplomaSearchRepository.save(studentDiploma);
        return result;
    }

    /**
     * Get all the studentDiplomas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDiplomaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentDiplomas");
        return studentDiplomaRepository.findAll(pageable)
            .map(studentDiplomaMapper::toDto);
    }


    /**
     * Get one studentDiploma by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentDiplomaDTO> findOne(Long id) {
        log.debug("Request to get StudentDiploma : {}", id);
        return studentDiplomaRepository.findById(id)
            .map(studentDiplomaMapper::toDto);
    }

    /**
     * Delete the studentDiploma by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentDiploma : {}", id);
        studentDiplomaRepository.deleteById(id);
        studentDiplomaSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentDiploma corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentDiplomaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentDiplomas for query {}", query);
        return studentDiplomaSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentDiplomaMapper::toDto);
    }
}
