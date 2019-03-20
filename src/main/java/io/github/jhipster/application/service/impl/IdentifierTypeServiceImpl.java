package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.IdentifierTypeService;
import io.github.jhipster.application.domain.IdentifierType;
import io.github.jhipster.application.repository.IdentifierTypeRepository;
import io.github.jhipster.application.repository.search.IdentifierTypeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing IdentifierType.
 */
@Service
@Transactional
public class IdentifierTypeServiceImpl implements IdentifierTypeService {

    private final Logger log = LoggerFactory.getLogger(IdentifierTypeServiceImpl.class);

    private final IdentifierTypeRepository identifierTypeRepository;

    private final IdentifierTypeSearchRepository identifierTypeSearchRepository;

    public IdentifierTypeServiceImpl(IdentifierTypeRepository identifierTypeRepository, IdentifierTypeSearchRepository identifierTypeSearchRepository) {
        this.identifierTypeRepository = identifierTypeRepository;
        this.identifierTypeSearchRepository = identifierTypeSearchRepository;
    }

    /**
     * Save a identifierType.
     *
     * @param identifierType the entity to save
     * @return the persisted entity
     */
    @Override
    public IdentifierType save(IdentifierType identifierType) {
        log.debug("Request to save IdentifierType : {}", identifierType);
        IdentifierType result = identifierTypeRepository.save(identifierType);
        identifierTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the identifierTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdentifierType> findAll() {
        log.debug("Request to get all IdentifierTypes");
        return identifierTypeRepository.findAll();
    }


    /**
     * Get one identifierType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<IdentifierType> findOne(Long id) {
        log.debug("Request to get IdentifierType : {}", id);
        return identifierTypeRepository.findById(id);
    }

    /**
     * Delete the identifierType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete IdentifierType : {}", id);
        identifierTypeRepository.deleteById(id);
        identifierTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the identifierType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<IdentifierType> search(String query) {
        log.debug("Request to search IdentifierTypes for query {}", query);
        return StreamSupport
            .stream(identifierTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
