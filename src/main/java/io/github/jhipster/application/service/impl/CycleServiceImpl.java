package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.CycleService;
import io.github.jhipster.application.domain.Cycle;
import io.github.jhipster.application.repository.CycleRepository;
import io.github.jhipster.application.repository.search.CycleSearchRepository;
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
 * Service Implementation for managing Cycle.
 */
@Service
@Transactional
public class CycleServiceImpl implements CycleService {

    private final Logger log = LoggerFactory.getLogger(CycleServiceImpl.class);

    private final CycleRepository cycleRepository;

    private final CycleSearchRepository cycleSearchRepository;

    public CycleServiceImpl(CycleRepository cycleRepository, CycleSearchRepository cycleSearchRepository) {
        this.cycleRepository = cycleRepository;
        this.cycleSearchRepository = cycleSearchRepository;
    }

    /**
     * Save a cycle.
     *
     * @param cycle the entity to save
     * @return the persisted entity
     */
    @Override
    public Cycle save(Cycle cycle) {
        log.debug("Request to save Cycle : {}", cycle);
        Cycle result = cycleRepository.save(cycle);
        cycleSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the cycles.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cycle> findAll() {
        log.debug("Request to get all Cycles");
        return cycleRepository.findAll();
    }


    /**
     * Get one cycle by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Cycle> findOne(Long id) {
        log.debug("Request to get Cycle : {}", id);
        return cycleRepository.findById(id);
    }

    /**
     * Delete the cycle by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Cycle : {}", id);
        cycleRepository.deleteById(id);
        cycleSearchRepository.deleteById(id);
    }

    /**
     * Search for the cycle corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Cycle> search(String query) {
        log.debug("Request to search Cycles for query {}", query);
        return StreamSupport
            .stream(cycleSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
