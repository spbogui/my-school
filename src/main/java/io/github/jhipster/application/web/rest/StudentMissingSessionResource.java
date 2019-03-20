package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.StudentMissingSessionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.StudentMissingSessionDTO;
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
 * REST controller for managing StudentMissingSession.
 */
@RestController
@RequestMapping("/api")
public class StudentMissingSessionResource {

    private final Logger log = LoggerFactory.getLogger(StudentMissingSessionResource.class);

    private static final String ENTITY_NAME = "studentMissingSession";

    private final StudentMissingSessionService studentMissingSessionService;

    public StudentMissingSessionResource(StudentMissingSessionService studentMissingSessionService) {
        this.studentMissingSessionService = studentMissingSessionService;
    }

    /**
     * POST  /student-missing-sessions : Create a new studentMissingSession.
     *
     * @param studentMissingSessionDTO the studentMissingSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentMissingSessionDTO, or with status 400 (Bad Request) if the studentMissingSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-missing-sessions")
    public ResponseEntity<StudentMissingSessionDTO> createStudentMissingSession(@Valid @RequestBody StudentMissingSessionDTO studentMissingSessionDTO) throws URISyntaxException {
        log.debug("REST request to save StudentMissingSession : {}", studentMissingSessionDTO);
        if (studentMissingSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentMissingSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentMissingSessionDTO result = studentMissingSessionService.save(studentMissingSessionDTO);
        return ResponseEntity.created(new URI("/api/student-missing-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-missing-sessions : Updates an existing studentMissingSession.
     *
     * @param studentMissingSessionDTO the studentMissingSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentMissingSessionDTO,
     * or with status 400 (Bad Request) if the studentMissingSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentMissingSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-missing-sessions")
    public ResponseEntity<StudentMissingSessionDTO> updateStudentMissingSession(@Valid @RequestBody StudentMissingSessionDTO studentMissingSessionDTO) throws URISyntaxException {
        log.debug("REST request to update StudentMissingSession : {}", studentMissingSessionDTO);
        if (studentMissingSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentMissingSessionDTO result = studentMissingSessionService.save(studentMissingSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentMissingSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-missing-sessions : get all the studentMissingSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentMissingSessions in body
     */
    @GetMapping("/student-missing-sessions")
    public ResponseEntity<List<StudentMissingSessionDTO>> getAllStudentMissingSessions(Pageable pageable) {
        log.debug("REST request to get a page of StudentMissingSessions");
        Page<StudentMissingSessionDTO> page = studentMissingSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-missing-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-missing-sessions/:id : get the "id" studentMissingSession.
     *
     * @param id the id of the studentMissingSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentMissingSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-missing-sessions/{id}")
    public ResponseEntity<StudentMissingSessionDTO> getStudentMissingSession(@PathVariable Long id) {
        log.debug("REST request to get StudentMissingSession : {}", id);
        Optional<StudentMissingSessionDTO> studentMissingSessionDTO = studentMissingSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentMissingSessionDTO);
    }

    /**
     * DELETE  /student-missing-sessions/:id : delete the "id" studentMissingSession.
     *
     * @param id the id of the studentMissingSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-missing-sessions/{id}")
    public ResponseEntity<Void> deleteStudentMissingSession(@PathVariable Long id) {
        log.debug("REST request to delete StudentMissingSession : {}", id);
        studentMissingSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-missing-sessions?query=:query : search for the studentMissingSession corresponding
     * to the query.
     *
     * @param query the query of the studentMissingSession search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-missing-sessions")
    public ResponseEntity<List<StudentMissingSessionDTO>> searchStudentMissingSessions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentMissingSessions for query {}", query);
        Page<StudentMissingSessionDTO> page = studentMissingSessionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-missing-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
