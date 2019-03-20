package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RubricAmountService;
import io.github.jhipster.application.domain.RubricAmount;
import io.github.jhipster.application.repository.RubricAmountRepository;
import io.github.jhipster.application.repository.search.RubricAmountSearchRepository;
import io.github.jhipster.application.service.dto.RubricAmountDTO;
import io.github.jhipster.application.service.mapper.RubricAmountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RubricAmount.
 */
@Service
@Transactional
public class RubricAmountServiceImpl implements RubricAmountService {

    private final Logger log = LoggerFactory.getLogger(RubricAmountServiceImpl.class);

    private final RubricAmountRepository rubricAmountRepository;

    private final RubricAmountMapper rubricAmountMapper;

    private final RubricAmountSearchRepository rubricAmountSearchRepository;

    public RubricAmountServiceImpl(RubricAmountRepository rubricAmountRepository, RubricAmountMapper rubricAmountMapper, RubricAmountSearchRepository rubricAmountSearchRepository) {
        this.rubricAmountRepository = rubricAmountRepository;
        this.rubricAmountMapper = rubricAmountMapper;
        this.rubricAmountSearchRepository = rubricAmountSearchRepository;
    }

    /**
     * Save a rubricAmount.
     *
     * @param rubricAmountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public RubricAmountDTO save(RubricAmountDTO rubricAmountDTO) {
        log.debug("Request to save RubricAmount : {}", rubricAmountDTO);
        RubricAmount rubricAmount = rubricAmountMapper.toEntity(rubricAmountDTO);
        rubricAmount = rubricAmountRepository.save(rubricAmount);
        RubricAmountDTO result = rubricAmountMapper.toDto(rubricAmount);
        rubricAmountSearchRepository.save(rubricAmount);
        return result;
    }

    /**
     * Get all the rubricAmounts.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubricAmountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RubricAmounts");
        return rubricAmountRepository.findAll(pageable)
            .map(rubricAmountMapper::toDto);
    }


    /**
     * Get one rubricAmount by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RubricAmountDTO> findOne(Long id) {
        log.debug("Request to get RubricAmount : {}", id);
        return rubricAmountRepository.findById(id)
            .map(rubricAmountMapper::toDto);
    }

    /**
     * Delete the rubricAmount by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RubricAmount : {}", id);
        rubricAmountRepository.deleteById(id);
        rubricAmountSearchRepository.deleteById(id);
    }

    /**
     * Search for the rubricAmount corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RubricAmountDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of RubricAmounts for query {}", query);
        return rubricAmountSearchRepository.search(queryStringQuery(query), pageable)
            .map(rubricAmountMapper::toDto);
    }
}
