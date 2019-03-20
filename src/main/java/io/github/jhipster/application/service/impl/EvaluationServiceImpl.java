package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.EvaluationService;
import io.github.jhipster.application.domain.Evaluation;
import io.github.jhipster.application.repository.EvaluationRepository;
import io.github.jhipster.application.repository.search.EvaluationSearchRepository;
import io.github.jhipster.application.service.dto.EvaluationDTO;
import io.github.jhipster.application.service.mapper.EvaluationMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Evaluation.
 */
@Service
@Transactional
public class EvaluationServiceImpl implements EvaluationService {

    private final Logger log = LoggerFactory.getLogger(EvaluationServiceImpl.class);

    private final EvaluationRepository evaluationRepository;

    private final EvaluationMapper evaluationMapper;

    private final EvaluationSearchRepository evaluationSearchRepository;

    public EvaluationServiceImpl(EvaluationRepository evaluationRepository, EvaluationMapper evaluationMapper, EvaluationSearchRepository evaluationSearchRepository) {
        this.evaluationRepository = evaluationRepository;
        this.evaluationMapper = evaluationMapper;
        this.evaluationSearchRepository = evaluationSearchRepository;
    }

    /**
     * Save a evaluation.
     *
     * @param evaluationDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EvaluationDTO save(EvaluationDTO evaluationDTO) {
        log.debug("Request to save Evaluation : {}", evaluationDTO);
        Evaluation evaluation = evaluationMapper.toEntity(evaluationDTO);
        evaluation = evaluationRepository.save(evaluation);
        EvaluationDTO result = evaluationMapper.toDto(evaluation);
        evaluationSearchRepository.save(evaluation);
        return result;
    }

    /**
     * Get all the evaluations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Evaluations");
        return evaluationRepository.findAll(pageable)
            .map(evaluationMapper::toDto);
    }

    /**
     * Get all the Evaluation with eager load of many-to-many relationships.
     *
     * @return the list of entities
     */
    public Page<EvaluationDTO> findAllWithEagerRelationships(Pageable pageable) {
        return evaluationRepository.findAllWithEagerRelationships(pageable).map(evaluationMapper::toDto);
    }
    

    /**
     * Get one evaluation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EvaluationDTO> findOne(Long id) {
        log.debug("Request to get Evaluation : {}", id);
        return evaluationRepository.findOneWithEagerRelationships(id)
            .map(evaluationMapper::toDto);
    }

    /**
     * Delete the evaluation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Evaluation : {}", id);
        evaluationRepository.deleteById(id);
        evaluationSearchRepository.deleteById(id);
    }

    /**
     * Search for the evaluation corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EvaluationDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Evaluations for query {}", query);
        return evaluationSearchRepository.search(queryStringQuery(query), pageable)
            .map(evaluationMapper::toDto);
    }
}
