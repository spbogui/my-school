package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.TeacherSubjectSchoolYearService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.TeacherSubjectSchoolYearDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing TeacherSubjectSchoolYear.
 */
@RestController
@RequestMapping("/api")
public class TeacherSubjectSchoolYearResource {

    private final Logger log = LoggerFactory.getLogger(TeacherSubjectSchoolYearResource.class);

    private static final String ENTITY_NAME = "teacherSubjectSchoolYear";

    private final TeacherSubjectSchoolYearService teacherSubjectSchoolYearService;

    public TeacherSubjectSchoolYearResource(TeacherSubjectSchoolYearService teacherSubjectSchoolYearService) {
        this.teacherSubjectSchoolYearService = teacherSubjectSchoolYearService;
    }

    /**
     * POST  /teacher-subject-school-years : Create a new teacherSubjectSchoolYear.
     *
     * @param teacherSubjectSchoolYearDTO the teacherSubjectSchoolYearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new teacherSubjectSchoolYearDTO, or with status 400 (Bad Request) if the teacherSubjectSchoolYear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/teacher-subject-school-years")
    public ResponseEntity<TeacherSubjectSchoolYearDTO> createTeacherSubjectSchoolYear(@RequestBody TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO) throws URISyntaxException {
        log.debug("REST request to save TeacherSubjectSchoolYear : {}", teacherSubjectSchoolYearDTO);
        if (teacherSubjectSchoolYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new teacherSubjectSchoolYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TeacherSubjectSchoolYearDTO result = teacherSubjectSchoolYearService.save(teacherSubjectSchoolYearDTO);
        return ResponseEntity.created(new URI("/api/teacher-subject-school-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /teacher-subject-school-years : Updates an existing teacherSubjectSchoolYear.
     *
     * @param teacherSubjectSchoolYearDTO the teacherSubjectSchoolYearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated teacherSubjectSchoolYearDTO,
     * or with status 400 (Bad Request) if the teacherSubjectSchoolYearDTO is not valid,
     * or with status 500 (Internal Server Error) if the teacherSubjectSchoolYearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/teacher-subject-school-years")
    public ResponseEntity<TeacherSubjectSchoolYearDTO> updateTeacherSubjectSchoolYear(@RequestBody TeacherSubjectSchoolYearDTO teacherSubjectSchoolYearDTO) throws URISyntaxException {
        log.debug("REST request to update TeacherSubjectSchoolYear : {}", teacherSubjectSchoolYearDTO);
        if (teacherSubjectSchoolYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TeacherSubjectSchoolYearDTO result = teacherSubjectSchoolYearService.save(teacherSubjectSchoolYearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, teacherSubjectSchoolYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /teacher-subject-school-years : get all the teacherSubjectSchoolYears.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of teacherSubjectSchoolYears in body
     */
    @GetMapping("/teacher-subject-school-years")
    public ResponseEntity<List<TeacherSubjectSchoolYearDTO>> getAllTeacherSubjectSchoolYears(Pageable pageable) {
        log.debug("REST request to get a page of TeacherSubjectSchoolYears");
        Page<TeacherSubjectSchoolYearDTO> page = teacherSubjectSchoolYearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/teacher-subject-school-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /teacher-subject-school-years/:id : get the "id" teacherSubjectSchoolYear.
     *
     * @param id the id of the teacherSubjectSchoolYearDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the teacherSubjectSchoolYearDTO, or with status 404 (Not Found)
     */
    @GetMapping("/teacher-subject-school-years/{id}")
    public ResponseEntity<TeacherSubjectSchoolYearDTO> getTeacherSubjectSchoolYear(@PathVariable Long id) {
        log.debug("REST request to get TeacherSubjectSchoolYear : {}", id);
        Optional<TeacherSubjectSchoolYearDTO> teacherSubjectSchoolYearDTO = teacherSubjectSchoolYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(teacherSubjectSchoolYearDTO);
    }

    /**
     * DELETE  /teacher-subject-school-years/:id : delete the "id" teacherSubjectSchoolYear.
     *
     * @param id the id of the teacherSubjectSchoolYearDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/teacher-subject-school-years/{id}")
    public ResponseEntity<Void> deleteTeacherSubjectSchoolYear(@PathVariable Long id) {
        log.debug("REST request to delete TeacherSubjectSchoolYear : {}", id);
        teacherSubjectSchoolYearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/teacher-subject-school-years?query=:query : search for the teacherSubjectSchoolYear corresponding
     * to the query.
     *
     * @param query the query of the teacherSubjectSchoolYear search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/teacher-subject-school-years")
    public ResponseEntity<List<TeacherSubjectSchoolYearDTO>> searchTeacherSubjectSchoolYears(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of TeacherSubjectSchoolYears for query {}", query);
        Page<TeacherSubjectSchoolYearDTO> page = teacherSubjectSchoolYearService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/teacher-subject-school-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
