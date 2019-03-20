package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.PermissionAgreementService;
import io.github.jhipster.application.domain.PermissionAgreement;
import io.github.jhipster.application.repository.PermissionAgreementRepository;
import io.github.jhipster.application.repository.search.PermissionAgreementSearchRepository;
import io.github.jhipster.application.service.dto.PermissionAgreementDTO;
import io.github.jhipster.application.service.mapper.PermissionAgreementMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing PermissionAgreement.
 */
@Service
@Transactional
public class PermissionAgreementServiceImpl implements PermissionAgreementService {

    private final Logger log = LoggerFactory.getLogger(PermissionAgreementServiceImpl.class);

    private final PermissionAgreementRepository permissionAgreementRepository;

    private final PermissionAgreementMapper permissionAgreementMapper;

    private final PermissionAgreementSearchRepository permissionAgreementSearchRepository;

    public PermissionAgreementServiceImpl(PermissionAgreementRepository permissionAgreementRepository, PermissionAgreementMapper permissionAgreementMapper, PermissionAgreementSearchRepository permissionAgreementSearchRepository) {
        this.permissionAgreementRepository = permissionAgreementRepository;
        this.permissionAgreementMapper = permissionAgreementMapper;
        this.permissionAgreementSearchRepository = permissionAgreementSearchRepository;
    }

    /**
     * Save a permissionAgreement.
     *
     * @param permissionAgreementDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PermissionAgreementDTO save(PermissionAgreementDTO permissionAgreementDTO) {
        log.debug("Request to save PermissionAgreement : {}", permissionAgreementDTO);
        PermissionAgreement permissionAgreement = permissionAgreementMapper.toEntity(permissionAgreementDTO);
        permissionAgreement = permissionAgreementRepository.save(permissionAgreement);
        PermissionAgreementDTO result = permissionAgreementMapper.toDto(permissionAgreement);
        permissionAgreementSearchRepository.save(permissionAgreement);
        return result;
    }

    /**
     * Get all the permissionAgreements.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PermissionAgreementDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PermissionAgreements");
        return permissionAgreementRepository.findAll(pageable)
            .map(permissionAgreementMapper::toDto);
    }


    /**
     * Get one permissionAgreement by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<PermissionAgreementDTO> findOne(Long id) {
        log.debug("Request to get PermissionAgreement : {}", id);
        return permissionAgreementRepository.findById(id)
            .map(permissionAgreementMapper::toDto);
    }

    /**
     * Delete the permissionAgreement by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PermissionAgreement : {}", id);
        permissionAgreementRepository.deleteById(id);
        permissionAgreementSearchRepository.deleteById(id);
    }

    /**
     * Search for the permissionAgreement corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PermissionAgreementDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of PermissionAgreements for query {}", query);
        return permissionAgreementSearchRepository.search(queryStringQuery(query), pageable)
            .map(permissionAgreementMapper::toDto);
    }
}
