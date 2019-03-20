package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.TeacherMissingSessionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TeacherMissingSessionDTO;
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
 * REST controller for managing TeacherMissingSession.
 */
@RestController
@RequestMapping("/api")
public class TeacherMissingSessionResource {

    private final Logger log = LoggerFactory.getLogger(TeacherMissingSessionResource.class);

    private static final String ENTITY_NAME = "teacherMissingSession";

    private final TeacherMissingSessionService teacherMissingSessionService;

    public TeacherMissingSessionResource(TeacherMissingSessionService teacherMissingSessionService) {
        this.teacherMissingSessionService = teacherMissingSessionService;
    }

    /**
     * POST  /teacher-missing-sessions : Create a new teacherMissingSession.
     *
     * @param teacherMissingSessionDTO the teacherMissingSessionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherMissingSessionDTO, or with status 400 (Bad Request) if the teacherMissingSession has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teacher-missing-sessions")
    public ResponseEntity<TeacherMissingSessionDTO> createTeacherMissingSession(@Valid @RequestBody TeacherMissingSessionDTO teacherMissingSessionDTO) throws URISyntaxException {
        log.debug("REST request to save TeacherMissingSession : {}", teacherMissingSessionDTO);
        if (teacherMissingSessionDTO.getId() != null) {
            throw new BadRequestAlertException("A new teacherMissingSession cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeacherMissingSessionDTO result = teacherMissingSessionService.save(teacherMissingSessionDTO);
        return ResponseEntity.created(new URI("/api/teacher-missing-sessions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teacher-missing-sessions : Updates an existing teacherMissingSession.
     *
     * @param teacherMissingSessionDTO the teacherMissingSessionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherMissingSessionDTO,
     * or with status 400 (Bad Request) if the teacherMissingSessionDTO is not valid,
     * or with status 500 (Internal Server Error) if the teacherMissingSessionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teacher-missing-sessions")
    public ResponseEntity<TeacherMissingSessionDTO> updateTeacherMissingSession(@Valid @RequestBody TeacherMissingSessionDTO teacherMissingSessionDTO) throws URISyntaxException {
        log.debug("REST request to update TeacherMissingSession : {}", teacherMissingSessionDTO);
        if (teacherMissingSessionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeacherMissingSessionDTO result = teacherMissingSessionService.save(teacherMissingSessionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherMissingSessionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teacher-missing-sessions : get all the teacherMissingSessions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teacherMissingSessions in body
     */
    @GetMapping("/teacher-missing-sessions")
    public ResponseEntity<List<TeacherMissingSessionDTO>> getAllTeacherMissingSessions(Pageable pageable) {
        log.debug("REST request to get a page of TeacherMissingSessions");
        Page<TeacherMissingSessionDTO> page = teacherMissingSessionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teacher-missing-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /teacher-missing-sessions/:id : get the "id" teacherMissingSession.
     *
     * @param id the id of the teacherMissingSessionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherMissingSessionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teacher-missing-sessions/{id}")
    public ResponseEntity<TeacherMissingSessionDTO> getTeacherMissingSession(@PathVariable Long id) {
        log.debug("REST request to get TeacherMissingSession : {}", id);
        Optional<TeacherMissingSessionDTO> teacherMissingSessionDTO = teacherMissingSessionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacherMissingSessionDTO);
    }

    /**
     * DELETE  /teacher-missing-sessions/:id : delete the "id" teacherMissingSession.
     *
     * @param id the id of the teacherMissingSessionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teacher-missing-sessions/{id}")
    public ResponseEntity<Void> deleteTeacherMissingSession(@PathVariable Long id) {
        log.debug("REST request to delete TeacherMissingSession : {}", id);
        teacherMissingSessionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/teacher-missing-sessions?query=:query : search for the teacherMissingSession corresponding
     * to the query.
     *
     * @param query the query of the teacherMissingSession search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/teacher-missing-sessions")
    public ResponseEntity<List<TeacherMissingSessionDTO>> searchTeacherMissingSessions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TeacherMissingSessions for query {}", query);
        Page<TeacherMissingSessionDTO> page = teacherMissingSessionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/teacher-missing-sessions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
