package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.StudentEvaluationService;
import io.github.jhipster.application.domain.StudentEvaluation;
import io.github.jhipster.application.repository.StudentEvaluationRepository;
import io.github.jhipster.application.repository.search.StudentEvaluationSearchRepository;
import io.github.jhipster.application.service.dto.StudentEvaluationDTO;
import io.github.jhipster.application.service.mapper.StudentEvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentEvaluation.
 */
@Service
@Transactional
public class StudentEvaluationServiceImpl implements StudentEvaluationService {

    private final Logger log = LoggerFactory.getLogger(StudentEvaluationServiceImpl.class);

    private final StudentEvaluationRepository studentEvaluationRepository;

    private final StudentEvaluationMapper studentEvaluationMapper;

    private final StudentEvaluationSearchRepository studentEvaluationSearchRepository;

    public StudentEvaluationServiceImpl(StudentEvaluationRepository studentEvaluationRepository, StudentEvaluationMapper studentEvaluationMapper, StudentEvaluationSearchRepository studentEvaluationSearchRepository) {
        this.studentEvaluationRepository = studentEvaluationRepository;
        this.studentEvaluationMapper = studentEvaluationMapper;
        this.studentEvaluationSearchRepository = studentEvaluationSearchRepository;
    }

    /**
     * Save a studentEvaluation.
     *
     * @param studentEvaluationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentEvaluationDTO save(StudentEvaluationDTO studentEvaluationDTO) {
        log.debug("Request to save StudentEvaluation : {}", studentEvaluationDTO);
        StudentEvaluation studentEvaluation = studentEvaluationMapper.toEntity(studentEvaluationDTO);
        studentEvaluation = studentEvaluationRepository.save(studentEvaluation);
        StudentEvaluationDTO result = studentEvaluationMapper.toDto(studentEvaluation);
        studentEvaluationSearchRepository.save(studentEvaluation);
        return result;
    }

    /**
     * Get all the studentEvaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentEvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentEvaluations");
        return studentEvaluationRepository.findAll(pageable)
            .map(studentEvaluationMapper::toDto);
    }


    /**
     * Get one studentEvaluation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentEvaluationDTO> findOne(Long id) {
        log.debug("Request to get StudentEvaluation : {}", id);
        return studentEvaluationRepository.findById(id)
            .map(studentEvaluationMapper::toDto);
    }

    /**
     * Delete the studentEvaluation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentEvaluation : {}", id);
        studentEvaluationRepository.deleteById(id);
        studentEvaluationSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentEvaluation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentEvaluationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentEvaluations for query {}", query);
        return studentEvaluationSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentEvaluationMapper::toDto);
    }
}
