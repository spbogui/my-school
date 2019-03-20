package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.AskingPermissionService;
import io.github.jhipster.application.domain.AskingPermission;
import io.github.jhipster.application.repository.AskingPermissionRepository;
import io.github.jhipster.application.repository.search.AskingPermissionSearchRepository;
import io.github.jhipster.application.service.dto.AskingPermissionDTO;
import io.github.jhipster.application.service.mapper.AskingPermissionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing AskingPermission.
 */
@Service
@Transactional
public class AskingPermissionServiceImpl implements AskingPermissionService {

    private final Logger log = LoggerFactory.getLogger(AskingPermissionServiceImpl.class);

    private final AskingPermissionRepository askingPermissionRepository;

    private final AskingPermissionMapper askingPermissionMapper;

    private final AskingPermissionSearchRepository askingPermissionSearchRepository;

    public AskingPermissionServiceImpl(AskingPermissionRepository askingPermissionRepository, AskingPermissionMapper askingPermissionMapper, AskingPermissionSearchRepository askingPermissionSearchRepository) {
        this.askingPermissionRepository = askingPermissionRepository;
        this.askingPermissionMapper = askingPermissionMapper;
        this.askingPermissionSearchRepository = askingPermissionSearchRepository;
    }

    /**
     * Save a askingPermission.
     *
     * @param askingPermissionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public AskingPermissionDTO save(AskingPermissionDTO askingPermissionDTO) {
        log.debug("Request to save AskingPermission : {}", askingPermissionDTO);
        AskingPermission askingPermission = askingPermissionMapper.toEntity(askingPermissionDTO);
        askingPermission = askingPermissionRepository.save(askingPermission);
        AskingPermissionDTO result = askingPermissionMapper.toDto(askingPermission);
        askingPermissionSearchRepository.save(askingPermission);
        return result;
    }

    /**
     * Get all the askingPermissions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AskingPermissionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all AskingPermissions");
        return askingPermissionRepository.findAll(pageable)
            .map(askingPermissionMapper::toDto);
    }


    /**
     * Get one askingPermission by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<AskingPermissionDTO> findOne(Long id) {
        log.debug("Request to get AskingPermission : {}", id);
        return askingPermissionRepository.findById(id)
            .map(askingPermissionMapper::toDto);
    }

    /**
     * Delete the askingPermission by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete AskingPermission : {}", id);
        askingPermissionRepository.deleteById(id);
        askingPermissionSearchRepository.deleteById(id);
    }

    /**
     * Search for the askingPermission corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<AskingPermissionDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of AskingPermissions for query {}", query);
        return askingPermissionSearchRepository.search(queryStringQuery(query), pageable)
            .map(askingPermissionMapper::toDto);
    }
}
