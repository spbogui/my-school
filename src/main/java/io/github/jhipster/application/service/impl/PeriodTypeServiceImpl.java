package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PeriodTypeService;
import io.github.jhipster.application.domain.PeriodType;
import io.github.jhipster.application.repository.PeriodTypeRepository;
import io.github.jhipster.application.repository.search.PeriodTypeSearchRepository;
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
 * Service Implementation for managing PeriodType.
 */
@Service
@Transactional
public class PeriodTypeServiceImpl implements PeriodTypeService {

    private final Logger log = LoggerFactory.getLogger(PeriodTypeServiceImpl.class);

    private final PeriodTypeRepository periodTypeRepository;

    private final PeriodTypeSearchRepository periodTypeSearchRepository;

    public PeriodTypeServiceImpl(PeriodTypeRepository periodTypeRepository, PeriodTypeSearchRepository periodTypeSearchRepository) {
        this.periodTypeRepository = periodTypeRepository;
        this.periodTypeSearchRepository = periodTypeSearchRepository;
    }

    /**
     * Save a periodType.
     *
     * @param periodType the entity to save
     * @return the persisted entity
     */
    @Override
    public PeriodType save(PeriodType periodType) {
        log.debug("Request to save PeriodType : {}", periodType);
        PeriodType result = periodTypeRepository.save(periodType);
        periodTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the periodTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeriodType> findAll() {
        log.debug("Request to get all PeriodTypes");
        return periodTypeRepository.findAll();
    }


    /**
     * Get one periodType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodType> findOne(Long id) {
        log.debug("Request to get PeriodType : {}", id);
        return periodTypeRepository.findById(id);
    }

    /**
     * Delete the periodType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PeriodType : {}", id);
        periodTypeRepository.deleteById(id);
        periodTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the periodType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<PeriodType> search(String query) {
        log.debug("Request to search PeriodTypes for query {}", query);
        return StreamSupport
            .stream(periodTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
