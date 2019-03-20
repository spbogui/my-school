package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.SchoolYearService;
import io.github.jhipster.application.domain.SchoolYear;
import io.github.jhipster.application.repository.SchoolYearRepository;
import io.github.jhipster.application.repository.search.SchoolYearSearchRepository;
import io.github.jhipster.application.service.dto.SchoolYearDTO;
import io.github.jhipster.application.service.mapper.SchoolYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SchoolYear.
 */
@Service
@Transactional
public class SchoolYearServiceImpl implements SchoolYearService {

    private final Logger log = LoggerFactory.getLogger(SchoolYearServiceImpl.class);

    private final SchoolYearRepository schoolYearRepository;

    private final SchoolYearMapper schoolYearMapper;

    private final SchoolYearSearchRepository schoolYearSearchRepository;

    public SchoolYearServiceImpl(SchoolYearRepository schoolYearRepository, SchoolYearMapper schoolYearMapper, SchoolYearSearchRepository schoolYearSearchRepository) {
        this.schoolYearRepository = schoolYearRepository;
        this.schoolYearMapper = schoolYearMapper;
        this.schoolYearSearchRepository = schoolYearSearchRepository;
    }

    /**
     * Save a schoolYear.
     *
     * @param schoolYearDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchoolYearDTO save(SchoolYearDTO schoolYearDTO) {
        log.debug("Request to save SchoolYear : {}", schoolYearDTO);
        SchoolYear schoolYear = schoolYearMapper.toEntity(schoolYearDTO);
        schoolYear = schoolYearRepository.save(schoolYear);
        SchoolYearDTO result = schoolYearMapper.toDto(schoolYear);
        schoolYearSearchRepository.save(schoolYear);
        return result;
    }

    /**
     * Get all the schoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchoolYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolYears");
        return schoolYearRepository.findAll(pageable)
            .map(schoolYearMapper::toDto);
    }


    /**
     * Get one schoolYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolYearDTO> findOne(Long id) {
        log.debug("Request to get SchoolYear : {}", id);
        return schoolYearRepository.findById(id)
            .map(schoolYearMapper::toDto);
    }

    /**
     * Delete the schoolYear by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchoolYear : {}", id);
        schoolYearRepository.deleteById(id);
        schoolYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the schoolYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchoolYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SchoolYears for query {}", query);
        return schoolYearSearchRepository.search(queryStringQuery(query), pageable)
            .map(schoolYearMapper::toDto);
    }
}
