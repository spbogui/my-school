package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ProgramService;
import io.github.jhipster.application.domain.Program;
import io.github.jhipster.application.repository.ProgramRepository;
import io.github.jhipster.application.repository.search.ProgramSearchRepository;
import io.github.jhipster.application.service.dto.ProgramDTO;
import io.github.jhipster.application.service.mapper.ProgramMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Program.
 */
@Service
@Transactional
public class ProgramServiceImpl implements ProgramService {

    private final Logger log = LoggerFactory.getLogger(ProgramServiceImpl.class);

    private final ProgramRepository programRepository;

    private final ProgramMapper programMapper;

    private final ProgramSearchRepository programSearchRepository;

    public ProgramServiceImpl(ProgramRepository programRepository, ProgramMapper programMapper, ProgramSearchRepository programSearchRepository) {
        this.programRepository = programRepository;
        this.programMapper = programMapper;
        this.programSearchRepository = programSearchRepository;
    }

    /**
     * Save a program.
     *
     * @param programDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ProgramDTO save(ProgramDTO programDTO) {
        log.debug("Request to save Program : {}", programDTO);
        Program program = programMapper.toEntity(programDTO);
        program = programRepository.save(program);
        ProgramDTO result = programMapper.toDto(program);
        programSearchRepository.save(program);
        return result;
    }

    /**
     * Get all the programs.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Programs");
        return programRepository.findAll(pageable)
            .map(programMapper::toDto);
    }


    /**
     * Get one program by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ProgramDTO> findOne(Long id) {
        log.debug("Request to get Program : {}", id);
        return programRepository.findById(id)
            .map(programMapper::toDto);
    }

    /**
     * Delete the program by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Program : {}", id);
        programRepository.deleteById(id);
        programSearchRepository.deleteById(id);
    }

    /**
     * Search for the program corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ProgramDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Programs for query {}", query);
        return programSearchRepository.search(queryStringQuery(query), pageable)
            .map(programMapper::toDto);
    }
}
