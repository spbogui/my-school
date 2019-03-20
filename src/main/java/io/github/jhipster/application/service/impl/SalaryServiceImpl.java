package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.SalaryService;
import io.github.jhipster.application.domain.Salary;
import io.github.jhipster.application.repository.SalaryRepository;
import io.github.jhipster.application.repository.search.SalarySearchRepository;
import io.github.jhipster.application.service.dto.SalaryDTO;
import io.github.jhipster.application.service.mapper.SalaryMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Salary.
 */
@Service
@Transactional
public class SalaryServiceImpl implements SalaryService {

    private final Logger log = LoggerFactory.getLogger(SalaryServiceImpl.class);

    private final SalaryRepository salaryRepository;

    private final SalaryMapper salaryMapper;

    private final SalarySearchRepository salarySearchRepository;

    public SalaryServiceImpl(SalaryRepository salaryRepository, SalaryMapper salaryMapper, SalarySearchRepository salarySearchRepository) {
        this.salaryRepository = salaryRepository;
        this.salaryMapper = salaryMapper;
        this.salarySearchRepository = salarySearchRepository;
    }

    /**
     * Save a salary.
     *
     * @param salaryDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SalaryDTO save(SalaryDTO salaryDTO) {
        log.debug("Request to save Salary : {}", salaryDTO);
        Salary salary = salaryMapper.toEntity(salaryDTO);
        salary = salaryRepository.save(salary);
        SalaryDTO result = salaryMapper.toDto(salary);
        salarySearchRepository.save(salary);
        return result;
    }

    /**
     * Get all the salaries.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalaryDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Salaries");
        return salaryRepository.findAll(pageable)
            .map(salaryMapper::toDto);
    }


    /**
     * Get one salary by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<SalaryDTO> findOne(Long id) {
        log.debug("Request to get Salary : {}", id);
        return salaryRepository.findById(id)
            .map(salaryMapper::toDto);
    }

    /**
     * Delete the salary by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Salary : {}", id);
        salaryRepository.deleteById(id);
        salarySearchRepository.deleteById(id);
    }

    /**
     * Search for the salary corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SalaryDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Salaries for query {}", query);
        return salarySearchRepository.search(queryStringQuery(query), pageable)
            .map(salaryMapper::toDto);
    }
}
