package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ActorNameService;
import io.github.jhipster.application.domain.ActorName;
import io.github.jhipster.application.repository.ActorNameRepository;
import io.github.jhipster.application.repository.search.ActorNameSearchRepository;
import io.github.jhipster.application.service.dto.ActorNameDTO;
import io.github.jhipster.application.service.mapper.ActorNameMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ActorName.
 */
@Service
@Transactional
public class ActorNameServiceImpl implements ActorNameService {

    private final Logger log = LoggerFactory.getLogger(ActorNameServiceImpl.class);

    private final ActorNameRepository actorNameRepository;

    private final ActorNameMapper actorNameMapper;

    private final ActorNameSearchRepository actorNameSearchRepository;

    public ActorNameServiceImpl(ActorNameRepository actorNameRepository, ActorNameMapper actorNameMapper, ActorNameSearchRepository actorNameSearchRepository) {
        this.actorNameRepository = actorNameRepository;
        this.actorNameMapper = actorNameMapper;
        this.actorNameSearchRepository = actorNameSearchRepository;
    }

    /**
     * Save a actorName.
     *
     * @param actorNameDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ActorNameDTO save(ActorNameDTO actorNameDTO) {
        log.debug("Request to save ActorName : {}", actorNameDTO);
        ActorName actorName = actorNameMapper.toEntity(actorNameDTO);
        actorName = actorNameRepository.save(actorName);
        ActorNameDTO result = actorNameMapper.toDto(actorName);
        actorNameSearchRepository.save(actorName);
        return result;
    }

    /**
     * Get all the actorNames.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorNameDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ActorNames");
        return actorNameRepository.findAll(pageable)
            .map(actorNameMapper::toDto);
    }


    /**
     * Get one actorName by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ActorNameDTO> findOne(Long id) {
        log.debug("Request to get ActorName : {}", id);
        return actorNameRepository.findById(id)
            .map(actorNameMapper::toDto);
    }

    /**
     * Delete the actorName by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ActorName : {}", id);
        actorNameRepository.deleteById(id);
        actorNameSearchRepository.deleteById(id);
    }

    /**
     * Search for the actorName corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ActorNameDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ActorNames for query {}", query);
        return actorNameSearchRepository.search(queryStringQuery(query), pageable)
            .map(actorNameMapper::toDto);
    }
}
