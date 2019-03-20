package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ClassRoomService;
import io.github.jhipster.application.domain.ClassRoom;
import io.github.jhipster.application.repository.ClassRoomRepository;
import io.github.jhipster.application.repository.search.ClassRoomSearchRepository;
import io.github.jhipster.application.service.dto.ClassRoomDTO;
import io.github.jhipster.application.service.mapper.ClassRoomMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing ClassRoom.
 */
@Service
@Transactional
public class ClassRoomServiceImpl implements ClassRoomService {

    private final Logger log = LoggerFactory.getLogger(ClassRoomServiceImpl.class);

    private final ClassRoomRepository classRoomRepository;

    private final ClassRoomMapper classRoomMapper;

    private final ClassRoomSearchRepository classRoomSearchRepository;

    public ClassRoomServiceImpl(ClassRoomRepository classRoomRepository, ClassRoomMapper classRoomMapper, ClassRoomSearchRepository classRoomSearchRepository) {
        this.classRoomRepository = classRoomRepository;
        this.classRoomMapper = classRoomMapper;
        this.classRoomSearchRepository = classRoomSearchRepository;
    }

    /**
     * Save a classRoom.
     *
     * @param classRoomDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassRoomDTO save(ClassRoomDTO classRoomDTO) {
        log.debug("Request to save ClassRoom : {}", classRoomDTO);
        ClassRoom classRoom = classRoomMapper.toEntity(classRoomDTO);
        classRoom = classRoomRepository.save(classRoom);
        ClassRoomDTO result = classRoomMapper.toDto(classRoom);
        classRoomSearchRepository.save(classRoom);
        return result;
    }

    /**
     * Get all the classRooms.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassRoomDTO> findAll(Pageable pageable) {
        log.debug("Request to get all ClassRooms");
        return classRoomRepository.findAll(pageable)
            .map(classRoomMapper::toDto);
    }


    /**
     * Get one classRoom by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassRoomDTO> findOne(Long id) {
        log.debug("Request to get ClassRoom : {}", id);
        return classRoomRepository.findById(id)
            .map(classRoomMapper::toDto);
    }

    /**
     * Delete the classRoom by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassRoom : {}", id);
        classRoomRepository.deleteById(id);
        classRoomSearchRepository.deleteById(id);
    }

    /**
     * Search for the classRoom corresponding to the query.
     *
     * @param query the query of the search
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<ClassRoomDTO> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of ClassRooms for query {}", query);
        return classRoomSearchRepository.search(queryStringQuery(query), pageable)
            .map(classRoomMapper::toDto);
    }
}
