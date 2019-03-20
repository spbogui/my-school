package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RelationshipTypeService;
import io.github.jhipster.application.domain.RelationshipType;
import io.github.jhipster.application.repository.RelationshipTypeRepository;
import io.github.jhipster.application.repository.search.RelationshipTypeSearchRepository;
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
 * Service Implementation for managing RelationshipType.
 */
@Service
@Transactional
public class RelationshipTypeServiceImpl implements RelationshipTypeService {

    private final Logger log = LoggerFactory.getLogger(RelationshipTypeServiceImpl.class);

    private final RelationshipTypeRepository relationshipTypeRepository;

    private final RelationshipTypeSearchRepository relationshipTypeSearchRepository;

    public RelationshipTypeServiceImpl(RelationshipTypeRepository relationshipTypeRepository, RelationshipTypeSearchRepository relationshipTypeSearchRepository) {
        this.relationshipTypeRepository = relationshipTypeRepository;
        this.relationshipTypeSearchRepository = relationshipTypeSearchRepository;
    }

    /**
     * Save a relationshipType.
     *
     * @param relationshipType the entity to save
     * @return the persisted entity
     */
    @Override
    public RelationshipType save(RelationshipType relationshipType) {
        log.debug("Request to save RelationshipType : {}", relationshipType);
        RelationshipType result = relationshipTypeRepository.save(relationshipType);
        relationshipTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the relationshipTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelationshipType> findAll() {
        log.debug("Request to get all RelationshipTypes");
        return relationshipTypeRepository.findAll();
    }


    /**
     * Get one relationshipType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RelationshipType> findOne(Long id) {
        log.debug("Request to get RelationshipType : {}", id);
        return relationshipTypeRepository.findById(id);
    }

    /**
     * Delete the relationshipType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RelationshipType : {}", id);
        relationshipTypeRepository.deleteById(id);
        relationshipTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the relationshipType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RelationshipType> search(String query) {
        log.debug("Request to search RelationshipTypes for query {}", query);
        return StreamSupport
            .stream(relationshipTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
