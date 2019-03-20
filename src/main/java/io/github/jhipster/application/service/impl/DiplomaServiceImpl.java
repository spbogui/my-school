package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.DiplomaService;
import io.github.jhipster.application.domain.Diploma;
import io.github.jhipster.application.repository.DiplomaRepository;
import io.github.jhipster.application.repository.search.DiplomaSearchRepository;
import io.github.jhipster.application.service.dto.DiplomaDTO;
import io.github.jhipster.application.service.mapper.DiplomaMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Diploma.
 */
@Service
@Transactional
public class DiplomaServiceImpl implements DiplomaService {

    private final Logger log = LoggerFactory.getLogger(DiplomaServiceImpl.class);

    private final DiplomaRepository diplomaRepository;

    private final DiplomaMapper diplomaMapper;

    private final DiplomaSearchRepository diplomaSearchRepository;

    public DiplomaServiceImpl(DiplomaRepository diplomaRepository, DiplomaMapper diplomaMapper, DiplomaSearchRepository diplomaSearchRepository) {
        this.diplomaRepository = diplomaRepository;
        this.diplomaMapper = diplomaMapper;
        this.diplomaSearchRepository = diplomaSearchRepository;
    }

    /**
     * Save a diploma.
     *
     * @param diplomaDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiplomaDTO save(DiplomaDTO diplomaDTO) {
        log.debug("Request to save Diploma : {}", diplomaDTO);
        Diploma diploma = diplomaMapper.toEntity(diplomaDTO);
        diploma = diplomaRepository.save(diploma);
        DiplomaDTO result = diplomaMapper.toDto(diploma);
        diplomaSearchRepository.save(diploma);
        return result;
    }

    /**
     * Get all the diplomas.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiplomaDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Diplomas");
        return diplomaRepository.findAll(pageable)
            .map(diplomaMapper::toDto);
    }


    /**
     * Get one diploma by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<DiplomaDTO> findOne(Long id) {
        log.debug("Request to get Diploma : {}", id);
        return diplomaRepository.findById(id)
            .map(diplomaMapper::toDto);
    }

    /**
     * Delete the diploma by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Diploma : {}", id);
        diplomaRepository.deleteById(id);
        diplomaSearchRepository.deleteById(id);
    }

    /**
     * Search for the diploma corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiplomaDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Diplomas for query {}", query);
        return diplomaSearchRepository.search(queryStringQuery(query), pageable)
            .map(diplomaMapper::toDto);
    }
}
