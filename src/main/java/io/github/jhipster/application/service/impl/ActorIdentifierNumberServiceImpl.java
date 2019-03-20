package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ActorIdentifierNumberService;
import io.github.jhipster.application.domain.ActorIdentifierNumber;
import io.github.jhipster.application.repository.ActorIdentifierNumberRepository;
import io.github.jhipster.application.repository.search.ActorIdentifierNumberSearchRepository;
import io.github.jhipster.application.service.dto.ActorIdentifierNumberDTO;
import io.github.jhipster.application.service.mapper.ActorIdentifierNumberMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ActorIdentifierNumber.
 */
@Service
@Transactional
public class ActorIdentifierNumberServiceImpl implements ActorIdentifierNumberService {

    private final Logger log = LoggerFactory.getLogger(ActorIdentifierNumberServiceImpl.class);

    private final ActorIdentifierNumberRepository actorIdentifierNumberRepository;

    private final ActorIdentifierNumberMapper actorIdentifierNumberMapper;

    private final ActorIdentifierNumberSearchRepository actorIdentifierNumberSearchRepository;

    public ActorIdentifierNumberServiceImpl(ActorIdentifierNumberRepository actorIdentifierNumberRepository, ActorIdentifierNumberMapper actorIdentifierNumberMapper, ActorIdentifierNumberSearchRepository actorIdentifierNumberSearchRepository) {
        this.actorIdentifierNumberRepository = actorIdentifierNumberRepository;
        this.actorIdentifierNumberMapper = actorIdentifierNumberMapper;
        this.actorIdentifierNumberSearchRepository = actorIdentifierNumberSearchRepository;
    }

    /**
     * Save a actorIdentifierNumber.
     *
     * @param actorIdentifierNumberDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActorIdentifierNumberDTO save(ActorIdentifierNumberDTO actorIdentifierNumberDTO) {
        log.debug("Request to save ActorIdentifierNumber : {}", actorIdentifierNumberDTO);
        ActorIdentifierNumber actorIdentifierNumber = actorIdentifierNumberMapper.toEntity(actorIdentifierNumberDTO);
        actorIdentifierNumber = actorIdentifierNumberRepository.save(actorIdentifierNumber);
        ActorIdentifierNumberDTO result = actorIdentifierNumberMapper.toDto(actorIdentifierNumber);
        actorIdentifierNumberSearchRepository.save(actorIdentifierNumber);
        return result;
    }

    /**
     * Get all the actorIdentifierNumbers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorIdentifierNumberDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActorIdentifierNumbers");
        return actorIdentifierNumberRepository.findAll(pageable)
            .map(actorIdentifierNumberMapper::toDto);
    }


    /**
     * Get one actorIdentifierNumber by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActorIdentifierNumberDTO> findOne(Long id) {
        log.debug("Request to get ActorIdentifierNumber : {}", id);
        return actorIdentifierNumberRepository.findById(id)
            .map(actorIdentifierNumberMapper::toDto);
    }

    /**
     * Delete the actorIdentifierNumber by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActorIdentifierNumber : {}", id);
        actorIdentifierNumberRepository.deleteById(id);
        actorIdentifierNumberSearchRepository.deleteById(id);
    }

    /**
     * Search for the actorIdentifierNumber corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorIdentifierNumberDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ActorIdentifierNumbers for query {}", query);
        return actorIdentifierNumberSearchRepository.search(queryStringQuery(query), pageable)
            .map(actorIdentifierNumberMapper::toDto);
    }
}
