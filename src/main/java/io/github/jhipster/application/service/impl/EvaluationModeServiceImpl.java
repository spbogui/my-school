package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.EvaluationModeService;
import io.github.jhipster.application.domain.EvaluationMode;
import io.github.jhipster.application.repository.EvaluationModeRepository;
import io.github.jhipster.application.repository.search.EvaluationModeSearchRepository;
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
 * Service Implementation for managing EvaluationMode.
 */
@Service
@Transactional
public class EvaluationModeServiceImpl implements EvaluationModeService {

    private final Logger log = LoggerFactory.getLogger(EvaluationModeServiceImpl.class);

    private final EvaluationModeRepository evaluationModeRepository;

    private final EvaluationModeSearchRepository evaluationModeSearchRepository;

    public EvaluationModeServiceImpl(EvaluationModeRepository evaluationModeRepository, EvaluationModeSearchRepository evaluationModeSearchRepository) {
        this.evaluationModeRepository = evaluationModeRepository;
        this.evaluationModeSearchRepository = evaluationModeSearchRepository;
    }

    /**
     * Save a evaluationMode.
     *
     * @param evaluationMode the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluationMode save(EvaluationMode evaluationMode) {
        log.debug("Request to save EvaluationMode : {}", evaluationMode);
        EvaluationMode result = evaluationModeRepository.save(evaluationMode);
        evaluationModeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the evaluationModes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationMode> findAll() {
        log.debug("Request to get all EvaluationModes");
        return evaluationModeRepository.findAll();
    }


    /**
     * Get one evaluationMode by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationMode> findOne(Long id) {
        log.debug("Request to get EvaluationMode : {}", id);
        return evaluationModeRepository.findById(id);
    }

    /**
     * Delete the evaluationMode by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationMode : {}", id);
        evaluationModeRepository.deleteById(id);
        evaluationModeSearchRepository.deleteById(id);
    }

    /**
     * Search for the evaluationMode corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationMode> search(String query) {
        log.debug("Request to search EvaluationModes for query {}", query);
        return StreamSupport
            .stream(evaluationModeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
