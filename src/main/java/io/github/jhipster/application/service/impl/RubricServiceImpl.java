package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RubricService;
import io.github.jhipster.application.domain.Rubric;
import io.github.jhipster.application.repository.RubricRepository;
import io.github.jhipster.application.repository.search.RubricSearchRepository;
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
 * Service Implementation for managing Rubric.
 */
@Service
@Transactional
public class RubricServiceImpl implements RubricService {

    private final Logger log = LoggerFactory.getLogger(RubricServiceImpl.class);

    private final RubricRepository rubricRepository;

    private final RubricSearchRepository rubricSearchRepository;

    public RubricServiceImpl(RubricRepository rubricRepository, RubricSearchRepository rubricSearchRepository) {
        this.rubricRepository = rubricRepository;
        this.rubricSearchRepository = rubricSearchRepository;
    }

    /**
     * Save a rubric.
     *
     * @param rubric the entity to save
     * @return the persisted entity
     */
    @Override
    public Rubric save(Rubric rubric) {
        log.debug("Request to save Rubric : {}", rubric);
        Rubric result = rubricRepository.save(rubric);
        rubricSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the rubrics.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rubric> findAll() {
        log.debug("Request to get all Rubrics");
        return rubricRepository.findAll();
    }


    /**
     * Get one rubric by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Rubric> findOne(Long id) {
        log.debug("Request to get Rubric : {}", id);
        return rubricRepository.findById(id);
    }

    /**
     * Delete the rubric by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rubric : {}", id);
        rubricRepository.deleteById(id);
        rubricSearchRepository.deleteById(id);
    }

    /**
     * Search for the rubric corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<Rubric> search(String query) {
        log.debug("Request to search Rubrics for query {}", query);
        return StreamSupport
            .stream(rubricSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
