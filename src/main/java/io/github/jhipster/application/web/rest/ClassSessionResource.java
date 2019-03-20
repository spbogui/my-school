package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ClassSessionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ClassSessionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
 * REST controller for managing ClassSession.
 */
@RestController
@RequestMapping("/api")
public class ClassSessionResource {

    private final Logger log = LoggerFactory.getLogger(ClassSessionResource.class);

    private static final String ENTITY_NAME = "classSession";

    private final ClassSessionService classSessionService;

    public ClassSessionResource(ClassSessionService classSessionService) {
        this.classSessionService = classSessionService;
    }

    /**
     * POST  /class-sessions : Create a new classSession.
     *
     * @param classSessionDTO the classSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new classSessionDTO, or with status 400 (Bad Request) if the classSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/class-sessions")
    public ResponseEntity<ClassSessionDTO> createClassSession(@Valid @RequestBody ClassSessionDTO classSessionDTO) throws URISyntaxException {
        log.debug("REST request to save ClassSession : {}", classSessionDTO);
        if (classSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new classSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ClassSessionDTO result = classSessionService.save(classSessionDTO);
        return ResponseEntity.created(new URI("/api/class-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /class-sessions : Updates an existing classSession.
     *
     * @param classSessionDTO the classSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated classSessionDTO,
     * or with status 400 (Bad Request) if the classSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the classSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/class-sessions")
    public ResponseEntity<ClassSessionDTO> updateClassSession(@Valid @RequestBody ClassSessionDTO classSessionDTO) throws URISyntaxException {
        log.debug("REST request to update ClassSession : {}", classSessionDTO);
        if (classSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ClassSessionDTO result = classSessionService.save(classSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, classSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /class-sessions : get all the classSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of classSessions in body
     */
    @GetMapping("/class-sessions")
    public ResponseEntity<List<ClassSessionDTO>> getAllClassSessions(Pageable pageable) {
        log.debug("REST request to get a page of ClassSessions");
        Page<ClassSessionDTO> page = classSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/class-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /class-sessions/:id : get the "id" classSession.
     *
     * @param id the id of the classSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the classSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/class-sessions/{id}")
    public ResponseEntity<ClassSessionDTO> getClassSession(@PathVariable Long id) {
        log.debug("REST request to get ClassSession : {}", id);
        Optional<ClassSessionDTO> classSessionDTO = classSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(classSessionDTO);
    }

    /**
     * DELETE  /class-sessions/:id : delete the "id" classSession.
     *
     * @param id the id of the classSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/class-sessions/{id}")
    public ResponseEntity<Void> deleteClassSession(@PathVariable Long id) {
        log.debug("REST request to delete ClassSession : {}", id);
        classSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/class-sessions?query=:query : search for the classSession corresponding
     * to the query.
     *
     * @param query the query of the classSession search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/class-sessions")
    public ResponseEntity<List<ClassSessionDTO>> searchClassSessions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ClassSessions for query {}", query);
        Page<ClassSessionDTO> page = classSessionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/class-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
