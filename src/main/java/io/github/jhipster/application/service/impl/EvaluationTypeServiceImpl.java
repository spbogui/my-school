package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.EvaluationTypeService;
import io.github.jhipster.application.domain.EvaluationType;
import io.github.jhipster.application.repository.EvaluationTypeRepository;
import io.github.jhipster.application.repository.search.EvaluationTypeSearchRepository;
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
 * Service Implementation for managing EvaluationType.
 */
@Service
@Transactional
public class EvaluationTypeServiceImpl implements EvaluationTypeService {

    private final Logger log = LoggerFactory.getLogger(EvaluationTypeServiceImpl.class);

    private final EvaluationTypeRepository evaluationTypeRepository;

    private final EvaluationTypeSearchRepository evaluationTypeSearchRepository;

    public EvaluationTypeServiceImpl(EvaluationTypeRepository evaluationTypeRepository, EvaluationTypeSearchRepository evaluationTypeSearchRepository) {
        this.evaluationTypeRepository = evaluationTypeRepository;
        this.evaluationTypeSearchRepository = evaluationTypeSearchRepository;
    }

    /**
     * Save a evaluationType.
     *
     * @param evaluationType the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluationType save(EvaluationType evaluationType) {
        log.debug("Request to save EvaluationType : {}", evaluationType);
        EvaluationType result = evaluationTypeRepository.save(evaluationType);
        evaluationTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the evaluationTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationType> findAll() {
        log.debug("Request to get all EvaluationTypes");
        return evaluationTypeRepository.findAll();
    }


    /**
     * Get one evaluationType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationType> findOne(Long id) {
        log.debug("Request to get EvaluationType : {}", id);
        return evaluationTypeRepository.findById(id);
    }

    /**
     * Delete the evaluationType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete EvaluationType : {}", id);
        evaluationTypeRepository.deleteById(id);
        evaluationTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the evaluationType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<EvaluationType> search(String query) {
        log.debug("Request to search EvaluationTypes for query {}", query);
        return StreamSupport
            .stream(evaluationTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
