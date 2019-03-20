package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.DaysService;
import io.github.jhipster.application.domain.Days;
import io.github.jhipster.application.repository.DaysRepository;
import io.github.jhipster.application.repository.search.DaysSearchRepository;
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
 * Service Implementation for managing Days.
 */
@Service
@Transactional
public class DaysServiceImpl implements DaysService {

    private final Logger log = LoggerFactory.getLogger(DaysServiceImpl.class);

    private final DaysRepository daysRepository;

    private final DaysSearchRepository daysSearchRepository;

    public DaysServiceImpl(DaysRepository daysRepository, DaysSearchRepository daysSearchRepository) {
        this.daysRepository = daysRepository;
        this.daysSearchRepository = daysSearchRepository;
    }

    /**
     * Save a days.
     *
     * @param days the entity to save
     * @return the persisted entity
     */
    @Override
    public Days save(Days days) {
        log.debug("Request to save Days : {}", days);
        Days result = daysRepository.save(days);
        daysSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the days.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Days> findAll() {
        log.debug("Request to get all Days");
        return daysRepository.findAll();
    }


    /**
     * Get one days by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Days> findOne(Long id) {
        log.debug("Request to get Days : {}", id);
        return daysRepository.findById(id);
    }

    /**
     * Delete the days by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Days : {}", id);
        daysRepository.deleteById(id);
        daysSearchRepository.deleteById(id);
    }

    /**
     * Search for the days corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Days> search(String query) {
        log.debug("Request to search Days for query {}", query);
        return StreamSupport
            .stream(daysSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
