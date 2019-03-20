package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ClassSessionService;
import io.github.jhipster.application.domain.ClassSession;
import io.github.jhipster.application.repository.ClassSessionRepository;
import io.github.jhipster.application.repository.search.ClassSessionSearchRepository;
import io.github.jhipster.application.service.dto.ClassSessionDTO;
import io.github.jhipster.application.service.mapper.ClassSessionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ClassSession.
 */
@Service
@Transactional
public class ClassSessionServiceImpl implements ClassSessionService {

    private final Logger log = LoggerFactory.getLogger(ClassSessionServiceImpl.class);

    private final ClassSessionRepository classSessionRepository;

    private final ClassSessionMapper classSessionMapper;

    private final ClassSessionSearchRepository classSessionSearchRepository;

    public ClassSessionServiceImpl(ClassSessionRepository classSessionRepository, ClassSessionMapper classSessionMapper, ClassSessionSearchRepository classSessionSearchRepository) {
        this.classSessionRepository = classSessionRepository;
        this.classSessionMapper = classSessionMapper;
        this.classSessionSearchRepository = classSessionSearchRepository;
    }

    /**
     * Save a classSession.
     *
     * @param classSessionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassSessionDTO save(ClassSessionDTO classSessionDTO) {
        log.debug("Request to save ClassSession : {}", classSessionDTO);
        ClassSession classSession = classSessionMapper.toEntity(classSessionDTO);
        classSession = classSessionRepository.save(classSession);
        ClassSessionDTO result = classSessionMapper.toDto(classSession);
        classSessionSearchRepository.save(classSession);
        return result;
    }

    /**
     * Get all the classSessions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassSessionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassSessions");
        return classSessionRepository.findAll(pageable)
            .map(classSessionMapper::toDto);
    }


    /**
     * Get one classSession by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassSessionDTO> findOne(Long id) {
        log.debug("Request to get ClassSession : {}", id);
        return classSessionRepository.findById(id)
            .map(classSessionMapper::toDto);
    }

    /**
     * Delete the classSession by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassSession : {}", id);
        classSessionRepository.deleteById(id);
        classSessionSearchRepository.deleteById(id);
    }

    /**
     * Search for the classSession corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassSessionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassSessions for query {}", query);
        return classSessionSearchRepository.search(queryStringQuery(query), pageable)
            .map(classSessionMapper::toDto);
    }
}
