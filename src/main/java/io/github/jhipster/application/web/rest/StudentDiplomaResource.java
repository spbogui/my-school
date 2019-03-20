package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.StudentDiplomaService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.StudentDiplomaDTO;
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
 * REST controller for managing StudentDiploma.
 */
@RestController
@RequestMapping("/api")
public class StudentDiplomaResource {

    private final Logger log = LoggerFactory.getLogger(StudentDiplomaResource.class);

    private static final String ENTITY_NAME = "studentDiploma";

    private final StudentDiplomaService studentDiplomaService;

    public StudentDiplomaResource(StudentDiplomaService studentDiplomaService) {
        this.studentDiplomaService = studentDiplomaService;
    }

    /**
     * POST  /student-diplomas : Create a new studentDiploma.
     *
     * @param studentDiplomaDTO the studentDiplomaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new studentDiplomaDTO, or with status 400 (Bad Request) if the studentDiploma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/student-diplomas")
    public ResponseEntity<StudentDiplomaDTO> createStudentDiploma(@Valid @RequestBody StudentDiplomaDTO studentDiplomaDTO) throws URISyntaxException {
        log.debug("REST request to save StudentDiploma : {}", studentDiplomaDTO);
        if (studentDiplomaDTO.getId() != null) {
            throw new BadRequestAlertException("A new studentDiploma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StudentDiplomaDTO result = studentDiplomaService.save(studentDiplomaDTO);
        return ResponseEntity.created(new URI("/api/student-diplomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /student-diplomas : Updates an existing studentDiploma.
     *
     * @param studentDiplomaDTO the studentDiplomaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated studentDiplomaDTO,
     * or with status 400 (Bad Request) if the studentDiplomaDTO is not valid,
     * or with status 500 (Internal Server Error) if the studentDiplomaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/student-diplomas")
    public ResponseEntity<StudentDiplomaDTO> updateStudentDiploma(@Valid @RequestBody StudentDiplomaDTO studentDiplomaDTO) throws URISyntaxException {
        log.debug("REST request to update StudentDiploma : {}", studentDiplomaDTO);
        if (studentDiplomaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StudentDiplomaDTO result = studentDiplomaService.save(studentDiplomaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, studentDiplomaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /student-diplomas : get all the studentDiplomas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of studentDiplomas in body
     */
    @GetMapping("/student-diplomas")
    public ResponseEntity<List<StudentDiplomaDTO>> getAllStudentDiplomas(Pageable pageable) {
        log.debug("REST request to get a page of StudentDiplomas");
        Page<StudentDiplomaDTO> page = studentDiplomaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/student-diplomas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /student-diplomas/:id : get the "id" studentDiploma.
     *
     * @param id the id of the studentDiplomaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the studentDiplomaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/student-diplomas/{id}")
    public ResponseEntity<StudentDiplomaDTO> getStudentDiploma(@PathVariable Long id) {
        log.debug("REST request to get StudentDiploma : {}", id);
        Optional<StudentDiplomaDTO> studentDiplomaDTO = studentDiplomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(studentDiplomaDTO);
    }

    /**
     * DELETE  /student-diplomas/:id : delete the "id" studentDiploma.
     *
     * @param id the id of the studentDiplomaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/student-diplomas/{id}")
    public ResponseEntity<Void> deleteStudentDiploma(@PathVariable Long id) {
        log.debug("REST request to delete StudentDiploma : {}", id);
        studentDiplomaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/student-diplomas?query=:query : search for the studentDiploma corresponding
     * to the query.
     *
     * @param query the query of the studentDiploma search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/student-diplomas")
    public ResponseEntity<List<StudentDiplomaDTO>> searchStudentDiplomas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StudentDiplomas for query {}", query);
        Page<StudentDiplomaDTO> page = studentDiplomaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/student-diplomas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
