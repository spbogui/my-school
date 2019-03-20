package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.SchoolSchoolYearService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.SchoolSchoolYearDTO;
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
 * REST controller for managing SchoolSchoolYear.
 */
@RestController
@RequestMapping("/api")
public class SchoolSchoolYearResource {

    private final Logger log = LoggerFactory.getLogger(SchoolSchoolYearResource.class);

    private static final String ENTITY_NAME = "schoolSchoolYear";

    private final SchoolSchoolYearService schoolSchoolYearService;

    public SchoolSchoolYearResource(SchoolSchoolYearService schoolSchoolYearService) {
        this.schoolSchoolYearService = schoolSchoolYearService;
    }

    /**
     * POST  /school-school-years : Create a new schoolSchoolYear.
     *
     * @param schoolSchoolYearDTO the schoolSchoolYearDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new schoolSchoolYearDTO, or with status 400 (Bad Request) if the schoolSchoolYear has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/school-school-years")
    public ResponseEntity<SchoolSchoolYearDTO> createSchoolSchoolYear(@RequestBody SchoolSchoolYearDTO schoolSchoolYearDTO) throws URISyntaxException {
        log.debug("REST request to save SchoolSchoolYear : {}", schoolSchoolYearDTO);
        if (schoolSchoolYearDTO.getId() != null) {
            throw new BadRequestAlertException("A new schoolSchoolYear cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SchoolSchoolYearDTO result = schoolSchoolYearService.save(schoolSchoolYearDTO);
        return ResponseEntity.created(new URI("/api/school-school-years/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /school-school-years : Updates an existing schoolSchoolYear.
     *
     * @param schoolSchoolYearDTO the schoolSchoolYearDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated schoolSchoolYearDTO,
     * or with status 400 (Bad Request) if the schoolSchoolYearDTO is not valid,
     * or with status 500 (Internal Server Error) if the schoolSchoolYearDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/school-school-years")
    public ResponseEntity<SchoolSchoolYearDTO> updateSchoolSchoolYear(@RequestBody SchoolSchoolYearDTO schoolSchoolYearDTO) throws URISyntaxException {
        log.debug("REST request to update SchoolSchoolYear : {}", schoolSchoolYearDTO);
        if (schoolSchoolYearDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SchoolSchoolYearDTO result = schoolSchoolYearService.save(schoolSchoolYearDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, schoolSchoolYearDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /school-school-years : get all the schoolSchoolYears.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of schoolSchoolYears in body
     */
    @GetMapping("/school-school-years")
    public ResponseEntity<List<SchoolSchoolYearDTO>> getAllSchoolSchoolYears(Pageable pageable) {
        log.debug("REST request to get a page of SchoolSchoolYears");
        Page<SchoolSchoolYearDTO> page = schoolSchoolYearService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/school-school-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /school-school-years/:id : get the "id" schoolSchoolYear.
     *
     * @param id the id of the schoolSchoolYearDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the schoolSchoolYearDTO, or with status 404 (Not Found)
     */
    @GetMapping("/school-school-years/{id}")
    public ResponseEntity<SchoolSchoolYearDTO> getSchoolSchoolYear(@PathVariable Long id) {
        log.debug("REST request to get SchoolSchoolYear : {}", id);
        Optional<SchoolSchoolYearDTO> schoolSchoolYearDTO = schoolSchoolYearService.findOne(id);
        return ResponseUtil.wrapOrNotFound(schoolSchoolYearDTO);
    }

    /**
     * DELETE  /school-school-years/:id : delete the "id" schoolSchoolYear.
     *
     * @param id the id of the schoolSchoolYearDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/school-school-years/{id}")
    public ResponseEntity<Void> deleteSchoolSchoolYear(@PathVariable Long id) {
        log.debug("REST request to delete SchoolSchoolYear : {}", id);
        schoolSchoolYearService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/school-school-years?query=:query : search for the schoolSchoolYear corresponding
     * to the query.
     *
     * @param query the query of the schoolSchoolYear search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/school-school-years")
    public ResponseEntity<List<SchoolSchoolYearDTO>> searchSchoolSchoolYears(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of SchoolSchoolYears for query {}", query);
        Page<SchoolSchoolYearDTO> page = schoolSchoolYearService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/school-school-years");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
