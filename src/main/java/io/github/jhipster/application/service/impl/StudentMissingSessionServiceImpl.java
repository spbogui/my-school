package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.StudentMissingSessionService;
import io.github.jhipster.application.domain.StudentMissingSession;
import io.github.jhipster.application.repository.StudentMissingSessionRepository;
import io.github.jhipster.application.repository.search.StudentMissingSessionSearchRepository;
import io.github.jhipster.application.service.dto.StudentMissingSessionDTO;
import io.github.jhipster.application.service.mapper.StudentMissingSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StudentMissingSession.
 */
@Service
@Transactional
public class StudentMissingSessionServiceImpl implements StudentMissingSessionService {

    private final Logger log = LoggerFactory.getLogger(StudentMissingSessionServiceImpl.class);

    private final StudentMissingSessionRepository studentMissingSessionRepository;

    private final StudentMissingSessionMapper studentMissingSessionMapper;

    private final StudentMissingSessionSearchRepository studentMissingSessionSearchRepository;

    public StudentMissingSessionServiceImpl(StudentMissingSessionRepository studentMissingSessionRepository, StudentMissingSessionMapper studentMissingSessionMapper, StudentMissingSessionSearchRepository studentMissingSessionSearchRepository) {
        this.studentMissingSessionRepository = studentMissingSessionRepository;
        this.studentMissingSessionMapper = studentMissingSessionMapper;
        this.studentMissingSessionSearchRepository = studentMissingSessionSearchRepository;
    }

    /**
     * Save a studentMissingSession.
     *
     * @param studentMissingSessionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StudentMissingSessionDTO save(StudentMissingSessionDTO studentMissingSessionDTO) {
        log.debug("Request to save StudentMissingSession : {}", studentMissingSessionDTO);
        StudentMissingSession studentMissingSession = studentMissingSessionMapper.toEntity(studentMissingSessionDTO);
        studentMissingSession = studentMissingSessionRepository.save(studentMissingSession);
        StudentMissingSessionDTO result = studentMissingSessionMapper.toDto(studentMissingSession);
        studentMissingSessionSearchRepository.save(studentMissingSession);
        return result;
    }

    /**
     * Get all the studentMissingSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentMissingSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StudentMissingSessions");
        return studentMissingSessionRepository.findAll(pageable)
            .map(studentMissingSessionMapper::toDto);
    }


    /**
     * Get one studentMissingSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StudentMissingSessionDTO> findOne(Long id) {
        log.debug("Request to get StudentMissingSession : {}", id);
        return studentMissingSessionRepository.findById(id)
            .map(studentMissingSessionMapper::toDto);
    }

    /**
     * Delete the studentMissingSession by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StudentMissingSession : {}", id);
        studentMissingSessionRepository.deleteById(id);
        studentMissingSessionSearchRepository.deleteById(id);
    }

    /**
     * Search for the studentMissingSession corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StudentMissingSessionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StudentMissingSessions for query {}", query);
        return studentMissingSessionSearchRepository.search(queryStringQuery(query), pageable)
            .map(studentMissingSessionMapper::toDto);
    }
}
