package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ActorRelationshipService;
import io.github.jhipster.application.domain.ActorRelationship;
import io.github.jhipster.application.repository.ActorRelationshipRepository;
import io.github.jhipster.application.repository.search.ActorRelationshipSearchRepository;
import io.github.jhipster.application.service.dto.ActorRelationshipDTO;
import io.github.jhipster.application.service.mapper.ActorRelationshipMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ActorRelationship.
 */
@Service
@Transactional
public class ActorRelationshipServiceImpl implements ActorRelationshipService {

    private final Logger log = LoggerFactory.getLogger(ActorRelationshipServiceImpl.class);

    private final ActorRelationshipRepository actorRelationshipRepository;

    private final ActorRelationshipMapper actorRelationshipMapper;

    private final ActorRelationshipSearchRepository actorRelationshipSearchRepository;

    public ActorRelationshipServiceImpl(ActorRelationshipRepository actorRelationshipRepository, ActorRelationshipMapper actorRelationshipMapper, ActorRelationshipSearchRepository actorRelationshipSearchRepository) {
        this.actorRelationshipRepository = actorRelationshipRepository;
        this.actorRelationshipMapper = actorRelationshipMapper;
        this.actorRelationshipSearchRepository = actorRelationshipSearchRepository;
    }

    /**
     * Save a actorRelationship.
     *
     * @param actorRelationshipDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActorRelationshipDTO save(ActorRelationshipDTO actorRelationshipDTO) {
        log.debug("Request to save ActorRelationship : {}", actorRelationshipDTO);
        ActorRelationship actorRelationship = actorRelationshipMapper.toEntity(actorRelationshipDTO);
        actorRelationship = actorRelationshipRepository.save(actorRelationship);
        ActorRelationshipDTO result = actorRelationshipMapper.toDto(actorRelationship);
        actorRelationshipSearchRepository.save(actorRelationship);
        return result;
    }

    /**
     * Get all the actorRelationships.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorRelationshipDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActorRelationships");
        return actorRelationshipRepository.findAll(pageable)
            .map(actorRelationshipMapper::toDto);
    }


    /**
     * Get one actorRelationship by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActorRelationshipDTO> findOne(Long id) {
        log.debug("Request to get ActorRelationship : {}", id);
        return actorRelationshipRepository.findById(id)
            .map(actorRelationshipMapper::toDto);
    }

    /**
     * Delete the actorRelationship by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActorRelationship : {}", id);
        actorRelationshipRepository.deleteById(id);
        actorRelationshipSearchRepository.deleteById(id);
    }

    /**
     * Search for the actorRelationship corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorRelationshipDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ActorRelationships for query {}", query);
        return actorRelationshipSearchRepository.search(queryStringQuery(query), pageable)
            .map(actorRelationshipMapper::toDto);
    }
}
