package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.ClassSessionType;
import io.github.jhipster.application.service.ClassSessionTypeService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ClassSessionType.
 */
@RestController
@RequestMapping("/api")
public class ClassSessionTypeResource {

    private final Logger log = LoggerFactory.getLogger(ClassSessionTypeResource.class);

    private static final String ENTITY_NAME = "classSessionType";

    private final ClassSessionTypeService classSessionTypeService;

    public ClassSessionTypeResource(ClassSessionTypeService classSessionTypeService) {
        this.classSessionTypeService = classSessionTypeService;
    }

    /**
     * POST  /class-session-types : Create a new classSessionType.
     *
     * @param classSessionType the classSessionType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classSessionType, or with status 400 (Bad Request) if the classSessionType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-session-types")
    public ResponseEntity<ClassSessionType> createClassSessionType(@Valid @RequestBody ClassSessionType classSessionType) throws URISyntaxException {
        log.debug("REST request to save ClassSessionType : {}", classSessionType);
        if (classSessionType.getId() != null) {
            throw new BadRequestAlertException("A new classSessionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassSessionType result = classSessionTypeService.save(classSessionType);
        return ResponseEntity.created(new URI("/api/class-session-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-session-types : Updates an existing classSessionType.
     *
     * @param classSessionType the classSessionType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classSessionType,
     * or with status 400 (Bad Request) if the classSessionType is not valid,
     * or with status 500 (Internal Server Error) if the classSessionType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-session-types")
    public ResponseEntity<ClassSessionType> updateClassSessionType(@Valid @RequestBody ClassSessionType classSessionType) throws URISyntaxException {
        log.debug("REST request to update ClassSessionType : {}", classSessionType);
        if (classSessionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassSessionType result = classSessionTypeService.save(classSessionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classSessionType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-session-types : get all the classSessionTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of classSessionTypes in body
     */
    @GetMapping("/class-session-types")
    public List<ClassSessionType> getAllClassSessionTypes() {
        log.debug("REST request to get all ClassSessionTypes");
        return classSessionTypeService.findAll();
    }

    /**
     * GET  /class-session-types/:id : get the "id" classSessionType.
     *
     * @param id the id of the classSessionType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classSessionType, or with status 404 (Not Found)
     */
    @GetMapping("/class-session-types/{id}")
    public ResponseEntity<ClassSessionType> getClassSessionType(@PathVariable Long id) {
        log.debug("REST request to get ClassSessionType : {}", id);
        Optional<ClassSessionType> classSessionType = classSessionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classSessionType);
    }

    /**
     * DELETE  /class-session-types/:id : delete the "id" classSessionType.
     *
     * @param id the id of the classSessionType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-session-types/{id}")
    public ResponseEntity<Void> deleteClassSessionType(@PathVariable Long id) {
        log.debug("REST request to delete ClassSessionType : {}", id);
        classSessionTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/class-session-types?query=:query : search for the classSessionType corresponding
     * to the query.
     *
     * @param query the query of the classSessionType search
     * @return the result of the search
     */
    @GetMapping("/_search/class-session-types")
    public List<ClassSessionType> searchClassSessionTypes(@RequestParam String query) {
        log.debug("REST request to search ClassSessionTypes for query {}", query);
        return classSessionTypeService.search(query);
    }

}
