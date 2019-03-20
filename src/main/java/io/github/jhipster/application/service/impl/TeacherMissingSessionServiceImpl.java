package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.TeacherMissingSessionService;
import io.github.jhipster.application.domain.TeacherMissingSession;
import io.github.jhipster.application.repository.TeacherMissingSessionRepository;
import io.github.jhipster.application.repository.search.TeacherMissingSessionSearchRepository;
import io.github.jhipster.application.service.dto.TeacherMissingSessionDTO;
import io.github.jhipster.application.service.mapper.TeacherMissingSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing TeacherMissingSession.
 */
@Service
@Transactional
public class TeacherMissingSessionServiceImpl implements TeacherMissingSessionService {

    private final Logger log = LoggerFactory.getLogger(TeacherMissingSessionServiceImpl.class);

    private final TeacherMissingSessionRepository teacherMissingSessionRepository;

    private final TeacherMissingSessionMapper teacherMissingSessionMapper;

    private final TeacherMissingSessionSearchRepository teacherMissingSessionSearchRepository;

    public TeacherMissingSessionServiceImpl(TeacherMissingSessionRepository teacherMissingSessionRepository, TeacherMissingSessionMapper teacherMissingSessionMapper, TeacherMissingSessionSearchRepository teacherMissingSessionSearchRepository) {
        this.teacherMissingSessionRepository = teacherMissingSessionRepository;
        this.teacherMissingSessionMapper = teacherMissingSessionMapper;
        this.teacherMissingSessionSearchRepository = teacherMissingSessionSearchRepository;
    }

    /**
     * Save a teacherMissingSession.
     *
     * @param teacherMissingSessionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public TeacherMissingSessionDTO save(TeacherMissingSessionDTO teacherMissingSessionDTO) {
        log.debug("Request to save TeacherMissingSession : {}", teacherMissingSessionDTO);
        TeacherMissingSession teacherMissingSession = teacherMissingSessionMapper.toEntity(teacherMissingSessionDTO);
        teacherMissingSession = teacherMissingSessionRepository.save(teacherMissingSession);
        TeacherMissingSessionDTO result = teacherMissingSessionMapper.toDto(teacherMissingSession);
        teacherMissingSessionSearchRepository.save(teacherMissingSession);
        return result;
    }

    /**
     * Get all the teacherMissingSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherMissingSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all TeacherMissingSessions");
        return teacherMissingSessionRepository.findAll(pageable)
            .map(teacherMissingSessionMapper::toDto);
    }


    /**
     * Get one teacherMissingSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<TeacherMissingSessionDTO> findOne(Long id) {
        log.debug("Request to get TeacherMissingSession : {}", id);
        return teacherMissingSessionRepository.findById(id)
            .map(teacherMissingSessionMapper::toDto);
    }

    /**
     * Delete the teacherMissingSession by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete TeacherMissingSession : {}", id);
        teacherMissingSessionRepository.deleteById(id);
        teacherMissingSessionSearchRepository.deleteById(id);
    }

    /**
     * Search for the teacherMissingSession corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<TeacherMissingSessionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of TeacherMissingSessions for query {}", query);
        return teacherMissingSessionSearchRepository.search(queryStringQuery(query), pageable)
            .map(teacherMissingSessionMapper::toDto);
    }
}
