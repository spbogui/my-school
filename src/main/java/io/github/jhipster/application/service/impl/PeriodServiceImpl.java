package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PeriodService;
import io.github.jhipster.application.domain.Period;
import io.github.jhipster.application.repository.PeriodRepository;
import io.github.jhipster.application.repository.search.PeriodSearchRepository;
import io.github.jhipster.application.service.dto.PeriodDTO;
import io.github.jhipster.application.service.mapper.PeriodMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Period.
 */
@Service
@Transactional
public class PeriodServiceImpl implements PeriodService {

    private final Logger log = LoggerFactory.getLogger(PeriodServiceImpl.class);

    private final PeriodRepository periodRepository;

    private final PeriodMapper periodMapper;

    private final PeriodSearchRepository periodSearchRepository;

    public PeriodServiceImpl(PeriodRepository periodRepository, PeriodMapper periodMapper, PeriodSearchRepository periodSearchRepository) {
        this.periodRepository = periodRepository;
        this.periodMapper = periodMapper;
        this.periodSearchRepository = periodSearchRepository;
    }

    /**
     * Save a period.
     *
     * @param periodDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PeriodDTO save(PeriodDTO periodDTO) {
        log.debug("Request to save Period : {}", periodDTO);
        Period period = periodMapper.toEntity(periodDTO);
        period = periodRepository.save(period);
        PeriodDTO result = periodMapper.toDto(period);
        periodSearchRepository.save(period);
        return result;
    }

    /**
     * Get all the periods.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeriodDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Periods");
        return periodRepository.findAll(pageable)
            .map(periodMapper::toDto);
    }


    /**
     * Get one period by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PeriodDTO> findOne(Long id) {
        log.debug("Request to get Period : {}", id);
        return periodRepository.findById(id)
            .map(periodMapper::toDto);
    }

    /**
     * Delete the period by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Period : {}", id);
        periodRepository.deleteById(id);
        periodSearchRepository.deleteById(id);
    }

    /**
     * Search for the period corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PeriodDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Periods for query {}", query);
        return periodSearchRepository.search(queryStringQuery(query), pageable)
            .map(periodMapper::toDto);
    }
}
