package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RegimenService;
import io.github.jhipster.application.domain.Regimen;
import io.github.jhipster.application.repository.RegimenRepository;
import io.github.jhipster.application.repository.search.RegimenSearchRepository;
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
 * Service Implementation for managing Regimen.
 */
@Service
@Transactional
public class RegimenServiceImpl implements RegimenService {

    private final Logger log = LoggerFactory.getLogger(RegimenServiceImpl.class);

    private final RegimenRepository regimenRepository;

    private final RegimenSearchRepository regimenSearchRepository;

    public RegimenServiceImpl(RegimenRepository regimenRepository, RegimenSearchRepository regimenSearchRepository) {
        this.regimenRepository = regimenRepository;
        this.regimenSearchRepository = regimenSearchRepository;
    }

    /**
     * Save a regimen.
     *
     * @param regimen the entity to save
     * @return the persisted entity
     */
    @Override
    public Regimen save(Regimen regimen) {
        log.debug("Request to save Regimen : {}", regimen);
        Regimen result = regimenRepository.save(regimen);
        regimenSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the regimen.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Regimen> findAll() {
        log.debug("Request to get all Regimen");
        return regimenRepository.findAll();
    }


    /**
     * Get one regimen by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Regimen> findOne(Long id) {
        log.debug("Request to get Regimen : {}", id);
        return regimenRepository.findById(id);
    }

    /**
     * Delete the regimen by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Regimen : {}", id);
        regimenRepository.deleteById(id);
        regimenSearchRepository.deleteById(id);
    }

    /**
     * Search for the regimen corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Regimen> search(String query) {
        log.debug("Request to search Regimen for query {}", query);
        return StreamSupport
            .stream(regimenSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
