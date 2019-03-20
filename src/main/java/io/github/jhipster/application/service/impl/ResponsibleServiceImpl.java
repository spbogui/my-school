package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ResponsibleService;
import io.github.jhipster.application.domain.Responsible;
import io.github.jhipster.application.repository.ResponsibleRepository;
import io.github.jhipster.application.repository.search.ResponsibleSearchRepository;
import io.github.jhipster.application.service.dto.ResponsibleDTO;
import io.github.jhipster.application.service.mapper.ResponsibleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Responsible.
 */
@Service
@Transactional
public class ResponsibleServiceImpl implements ResponsibleService {

    private final Logger log = LoggerFactory.getLogger(ResponsibleServiceImpl.class);

    private final ResponsibleRepository responsibleRepository;

    private final ResponsibleMapper responsibleMapper;

    private final ResponsibleSearchRepository responsibleSearchRepository;

    public ResponsibleServiceImpl(ResponsibleRepository responsibleRepository, ResponsibleMapper responsibleMapper, ResponsibleSearchRepository responsibleSearchRepository) {
        this.responsibleRepository = responsibleRepository;
        this.responsibleMapper = responsibleMapper;
        this.responsibleSearchRepository = responsibleSearchRepository;
    }

    /**
     * Save a responsible.
     *
     * @param responsibleDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ResponsibleDTO save(ResponsibleDTO responsibleDTO) {
        log.debug("Request to save Responsible : {}", responsibleDTO);
        Responsible responsible = responsibleMapper.toEntity(responsibleDTO);
        responsible = responsibleRepository.save(responsible);
        ResponsibleDTO result = responsibleMapper.toDto(responsible);
        responsibleSearchRepository.save(responsible);
        return result;
    }

    /**
     * Get all the responsibles.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponsibleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Responsibles");
        return responsibleRepository.findAll(pageable)
            .map(responsibleMapper::toDto);
    }


    /**
     * Get one responsible by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ResponsibleDTO> findOne(Long id) {
        log.debug("Request to get Responsible : {}", id);
        return responsibleRepository.findById(id)
            .map(responsibleMapper::toDto);
    }

    /**
     * Delete the responsible by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Responsible : {}", id);
        responsibleRepository.deleteById(id);
        responsibleSearchRepository.deleteById(id);
    }

    /**
     * Search for the responsible corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ResponsibleDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Responsibles for query {}", query);
        return responsibleSearchRepository.search(queryStringQuery(query), pageable)
            .map(responsibleMapper::toDto);
    }
}
