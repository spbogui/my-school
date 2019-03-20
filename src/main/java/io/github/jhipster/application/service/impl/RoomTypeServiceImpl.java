package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.RoomTypeService;
import io.github.jhipster.application.domain.RoomType;
import io.github.jhipster.application.repository.RoomTypeRepository;
import io.github.jhipster.application.repository.search.RoomTypeSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing RoomType.
 */
@Service
@Transactional
public class RoomTypeServiceImpl implements RoomTypeService {

    private final Logger log = LoggerFactory.getLogger(RoomTypeServiceImpl.class);

    private final RoomTypeRepository roomTypeRepository;

    private final RoomTypeSearchRepository roomTypeSearchRepository;

    public RoomTypeServiceImpl(RoomTypeRepository roomTypeRepository, RoomTypeSearchRepository roomTypeSearchRepository) {
        this.roomTypeRepository = roomTypeRepository;
        this.roomTypeSearchRepository = roomTypeSearchRepository;
    }

    /**
     * Save a roomType.
     *
     * @param roomType the entity to save
     * @return the persisted entity
     */
    @Override
    public RoomType save(RoomType roomType) {
        log.debug("Request to save RoomType : {}", roomType);
        RoomType result = roomTypeRepository.save(roomType);
        roomTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the roomTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomType> findAll() {
        log.debug("Request to get all RoomTypes");
        return roomTypeRepository.findAll();
    }


    /**
     * Get one roomType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RoomType> findOne(Long id) {
        log.debug("Request to get RoomType : {}", id);
        return roomTypeRepository.findById(id);
    }

    /**
     * Delete the roomType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RoomType : {}", id);
        roomTypeRepository.deleteById(id);
        roomTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the roomType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RoomType> search(String query) {
        log.debug("Request to search RoomTypes for query {}", query);
        return StreamSupport
            .stream(roomTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
