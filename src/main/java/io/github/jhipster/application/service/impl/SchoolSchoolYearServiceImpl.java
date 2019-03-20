package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.SchoolSchoolYearService;
import io.github.jhipster.application.domain.SchoolSchoolYear;
import io.github.jhipster.application.repository.SchoolSchoolYearRepository;
import io.github.jhipster.application.repository.search.SchoolSchoolYearSearchRepository;
import io.github.jhipster.application.service.dto.SchoolSchoolYearDTO;
import io.github.jhipster.application.service.mapper.SchoolSchoolYearMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing SchoolSchoolYear.
 */
@Service
@Transactional
public class SchoolSchoolYearServiceImpl implements SchoolSchoolYearService {

    private final Logger log = LoggerFactory.getLogger(SchoolSchoolYearServiceImpl.class);

    private final SchoolSchoolYearRepository schoolSchoolYearRepository;

    private final SchoolSchoolYearMapper schoolSchoolYearMapper;

    private final SchoolSchoolYearSearchRepository schoolSchoolYearSearchRepository;

    public SchoolSchoolYearServiceImpl(SchoolSchoolYearRepository schoolSchoolYearRepository, SchoolSchoolYearMapper schoolSchoolYearMapper, SchoolSchoolYearSearchRepository schoolSchoolYearSearchRepository) {
        this.schoolSchoolYearRepository = schoolSchoolYearRepository;
        this.schoolSchoolYearMapper = schoolSchoolYearMapper;
        this.schoolSchoolYearSearchRepository = schoolSchoolYearSearchRepository;
    }

    /**
     * Save a schoolSchoolYear.
     *
     * @param schoolSchoolYearDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SchoolSchoolYearDTO save(SchoolSchoolYearDTO schoolSchoolYearDTO) {
        log.debug("Request to save SchoolSchoolYear : {}", schoolSchoolYearDTO);
        SchoolSchoolYear schoolSchoolYear = schoolSchoolYearMapper.toEntity(schoolSchoolYearDTO);
        schoolSchoolYear = schoolSchoolYearRepository.save(schoolSchoolYear);
        SchoolSchoolYearDTO result = schoolSchoolYearMapper.toDto(schoolSchoolYear);
        schoolSchoolYearSearchRepository.save(schoolSchoolYear);
        return result;
    }

    /**
     * Get all the schoolSchoolYears.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchoolSchoolYearDTO> findAll(Pageable pageable) {
        log.debug("Request to get all SchoolSchoolYears");
        return schoolSchoolYearRepository.findAll(pageable)
            .map(schoolSchoolYearMapper::toDto);
    }


    /**
     * Get one schoolSchoolYear by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SchoolSchoolYearDTO> findOne(Long id) {
        log.debug("Request to get SchoolSchoolYear : {}", id);
        return schoolSchoolYearRepository.findById(id)
            .map(schoolSchoolYearMapper::toDto);
    }

    /**
     * Delete the schoolSchoolYear by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete SchoolSchoolYear : {}", id);
        schoolSchoolYearRepository.deleteById(id);
        schoolSchoolYearSearchRepository.deleteById(id);
    }

    /**
     * Search for the schoolSchoolYear corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SchoolSchoolYearDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of SchoolSchoolYears for query {}", query);
        return schoolSchoolYearSearchRepository.search(queryStringQuery(query), pageable)
            .map(schoolSchoolYearMapper::toDto);
    }
}
