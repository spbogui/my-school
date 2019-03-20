package io.github.jhipster.application.service.impl;

import io.github.jhipster.application.service.ClassSessionTypeService;
import io.github.jhipster.application.domain.ClassSessionType;
import io.github.jhipster.application.repository.ClassSessionTypeRepository;
import io.github.jhipster.application.repository.search.ClassSessionTypeSearchRepository;
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
 * Service Implementation for managing ClassSessionType.
 */
@Service
@Transactional
public class ClassSessionTypeServiceImpl implements ClassSessionTypeService {

    private final Logger log = LoggerFactory.getLogger(ClassSessionTypeServiceImpl.class);

    private final ClassSessionTypeRepository classSessionTypeRepository;

    private final ClassSessionTypeSearchRepository classSessionTypeSearchRepository;

    public ClassSessionTypeServiceImpl(ClassSessionTypeRepository classSessionTypeRepository, ClassSessionTypeSearchRepository classSessionTypeSearchRepository) {
        this.classSessionTypeRepository = classSessionTypeRepository;
        this.classSessionTypeSearchRepository = classSessionTypeSearchRepository;
    }

    /**
     * Save a classSessionType.
     *
     * @param classSessionType the entity to save
     * @return the persisted entity
     */
    @Override
    public ClassSessionType save(ClassSessionType classSessionType) {
        log.debug("Request to save ClassSessionType : {}", classSessionType);
        ClassSessionType result = classSessionTypeRepository.save(classSessionType);
        classSessionTypeSearchRepository.save(result);
        return result;
    }

    /**
     * Get all the classSessionTypes.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassSessionType> findAll() {
        log.debug("Request to get all ClassSessionTypes");
        return classSessionTypeRepository.findAll();
    }


    /**
     * Get one classSessionType by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<ClassSessionType> findOne(Long id) {
        log.debug("Request to get ClassSessionType : {}", id);
        return classSessionTypeRepository.findById(id);
    }

    /**
     * Delete the classSessionType by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete ClassSessionType : {}", id);
        classSessionTypeRepository.deleteById(id);
        classSessionTypeSearchRepository.deleteById(id);
    }

    /**
     * Search for the classSessionType corresponding to the query.
     *
     * @param query the query of the search
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<ClassSessionType> search(String query) {
        log.debug("Request to search ClassSessionTypes for query {}", query);
        return StreamSupport
            .stream(classSessionTypeSearchRepository.search(queryStringQuery(query)).spliterator(), false)
            .collect(Collectors.toList());
    }
}
