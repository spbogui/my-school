package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.StaffJobService;
import io.github.jhipster.application.domain.StaffJob;
import io.github.jhipster.application.repository.StaffJobRepository;
import io.github.jhipster.application.repository.search.StaffJobSearchRepository;
import io.github.jhipster.application.service.dto.StaffJobDTO;
import io.github.jhipster.application.service.mapper.StaffJobMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing StaffJob.
 */
@Service
@Transactional
public class StaffJobServiceImpl implements StaffJobService {

    private final Logger log = LoggerFactory.getLogger(StaffJobServiceImpl.class);

    private final StaffJobRepository staffJobRepository;

    private final StaffJobMapper staffJobMapper;

    private final StaffJobSearchRepository staffJobSearchRepository;

    public StaffJobServiceImpl(StaffJobRepository staffJobRepository, StaffJobMapper staffJobMapper, StaffJobSearchRepository staffJobSearchRepository) {
        this.staffJobRepository = staffJobRepository;
        this.staffJobMapper = staffJobMapper;
        this.staffJobSearchRepository = staffJobSearchRepository;
    }

    /**
     * Save a staffJob.
     *
     * @param staffJobDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public StaffJobDTO save(StaffJobDTO staffJobDTO) {
        log.debug("Request to save StaffJob : {}", staffJobDTO);
        StaffJob staffJob = staffJobMapper.toEntity(staffJobDTO);
        staffJob = staffJobRepository.save(staffJob);
        StaffJobDTO result = staffJobMapper.toDto(staffJob);
        staffJobSearchRepository.save(staffJob);
        return result;
    }

    /**
     * Get all the staffJobs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all StaffJobs");
        return staffJobRepository.findAll(pageable)
            .map(staffJobMapper::toDto);
    }


    /**
     * Get one staffJob by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<StaffJobDTO> findOne(Long id) {
        log.debug("Request to get StaffJob : {}", id);
        return staffJobRepository.findById(id)
            .map(staffJobMapper::toDto);
    }

    /**
     * Delete the staffJob by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete StaffJob : {}", id);
        staffJobRepository.deleteById(id);
        staffJobSearchRepository.deleteById(id);
    }

    /**
     * Search for the staffJob corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<StaffJobDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of StaffJobs for query {}", query);
        return staffJobSearchRepository.search(queryStringQuery(query), pageable)
            .map(staffJobMapper::toDto);
    }
}
