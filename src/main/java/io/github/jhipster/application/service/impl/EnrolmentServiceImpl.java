package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.EnrolmentService;
import io.github.jhipster.application.domain.Enrolment;
import io.github.jhipster.application.repository.EnrolmentRepository;
import io.github.jhipster.application.repository.search.EnrolmentSearchRepository;
import io.github.jhipster.application.service.dto.EnrolmentDTO;
import io.github.jhipster.application.service.mapper.EnrolmentMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Enrolment.
 */
@Service
@Transactional
public class EnrolmentServiceImpl implements EnrolmentService {

    private final Logger log = LoggerFactory.getLogger(EnrolmentServiceImpl.class);

    private final EnrolmentRepository enrolmentRepository;

    private final EnrolmentMapper enrolmentMapper;

    private final EnrolmentSearchRepository enrolmentSearchRepository;

    public EnrolmentServiceImpl(EnrolmentRepository enrolmentRepository, EnrolmentMapper enrolmentMapper, EnrolmentSearchRepository enrolmentSearchRepository) {
        this.enrolmentRepository = enrolmentRepository;
        this.enrolmentMapper = enrolmentMapper;
        this.enrolmentSearchRepository = enrolmentSearchRepository;
    }

    /**
     * Save a enrolment.
     *
     * @param enrolmentDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public EnrolmentDTO save(EnrolmentDTO enrolmentDTO) {
        log.debug("Request to save Enrolment : {}", enrolmentDTO);
        Enrolment enrolment = enrolmentMapper.toEntity(enrolmentDTO);
        enrolment = enrolmentRepository.save(enrolment);
        EnrolmentDTO result = enrolmentMapper.toDto(enrolment);
        enrolmentSearchRepository.save(enrolment);
        return result;
    }

    /**
     * Get all the enrolments.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnrolmentDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Enrolments");
        return enrolmentRepository.findAll(pageable)
            .map(enrolmentMapper::toDto);
    }


    /**
     * Get one enrolment by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<EnrolmentDTO> findOne(Long id) {
        log.debug("Request to get Enrolment : {}", id);
        return enrolmentRepository.findById(id)
            .map(enrolmentMapper::toDto);
    }

    /**
     * Delete the enrolment by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Enrolment : {}", id);
        enrolmentRepository.deleteById(id);
        enrolmentSearchRepository.deleteById(id);
    }

    /**
     * Search for the enrolment corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<EnrolmentDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Enrolments for query {}", query);
        return enrolmentSearchRepository.search(queryStringQuery(query), pageable)
            .map(enrolmentMapper::toDto);
    }
}
