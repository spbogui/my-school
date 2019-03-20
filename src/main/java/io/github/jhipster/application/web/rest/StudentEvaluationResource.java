package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.StudentEvaluationService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.StudentEvaluationDTO;
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
 * REST controller for managing StudentEvaluation.
 */
@RestController
@RequestMapping("/api")
public class StudentEvaluationResource {

    private final Logger log = LoggerFactory.getLogger(StudentEvaluationResource.class);

    private static final String ENTITY_NAME = "studentEvaluation";

    private final StudentEvaluationService studentEvaluationService;

    public StudentEvaluationResource(StudentEvaluationService studentEvaluationService) {
        this.studentEvaluationService = studentEvaluationService;
    }

    /**
     * POST  /student-evaluations : Create a new studentEvaluation.
     *
     * @param studentEvaluationDTO the studentEvaluationDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentEvaluationDTO, or with status 400 (Bad Request) if the studentEvaluation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-evaluations")
    public ResponseEntity<StudentEvaluationDTO> createStudentEvaluation(@Valid @RequestBody StudentEvaluationDTO studentEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to save StudentEvaluation : {}", studentEvaluationDTO);
        if (studentEvaluationDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentEvaluation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentEvaluationDTO result = studentEvaluationService.save(studentEvaluationDTO);
        return ResponseEntity.created(new URI("/api/student-evaluations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-evaluations : Updates an existing studentEvaluation.
     *
     * @param studentEvaluationDTO the studentEvaluationDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentEvaluationDTO,
     * or with status 400 (Bad Request) if the studentEvaluationDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentEvaluationDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-evaluations")
    public ResponseEntity<StudentEvaluationDTO> updateStudentEvaluation(@Valid @RequestBody StudentEvaluationDTO studentEvaluationDTO) throws URISyntaxException {
        log.debug("REST request to update StudentEvaluation : {}", studentEvaluationDTO);
        if (studentEvaluationDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentEvaluationDTO result = studentEvaluationService.save(studentEvaluationDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentEvaluationDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-evaluations : get all the studentEvaluations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentEvaluations in body
     */
    @GetMapping("/student-evaluations")
    public ResponseEntity<List<StudentEvaluationDTO>> getAllStudentEvaluations(Pageable pageable) {
        log.debug("REST request to get a page of StudentEvaluations");
        Page<StudentEvaluationDTO> page = studentEvaluationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-evaluations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-evaluations/:id : get the "id" studentEvaluation.
     *
     * @param id the id of the studentEvaluationDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentEvaluationDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-evaluations/{id}")
    public ResponseEntity<StudentEvaluationDTO> getStudentEvaluation(@PathVariable Long id) {
        log.debug("REST request to get StudentEvaluation : {}", id);
        Optional<StudentEvaluationDTO> studentEvaluationDTO = studentEvaluationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentEvaluationDTO);
    }

    /**
     * DELETE  /student-evaluations/:id : delete the "id" studentEvaluation.
     *
     * @param id the id of the studentEvaluationDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-evaluations/{id}")
    public ResponseEntity<Void> deleteStudentEvaluation(@PathVariable Long id) {
        log.debug("REST request to delete StudentEvaluation : {}", id);
        studentEvaluationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-evaluations?query=:query : search for the studentEvaluation corresponding
     * to the query.
     *
     * @param query the query of the studentEvaluation search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-evaluations")
    public ResponseEntity<List<StudentEvaluationDTO>> searchStudentEvaluations(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentEvaluations for query {}", query);
        Page<StudentEvaluationDTO> page = studentEvaluationService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-evaluations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
