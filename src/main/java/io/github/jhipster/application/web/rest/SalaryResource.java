package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.SalaryService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.SalaryDTO;
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
 * REST controller for managing Salary.
 */
@RestController
@RequestMapping("/api")
public class SalaryResource {

    private final Logger log = LoggerFactory.getLogger(SalaryResource.class);

    private static final String ENTITY_NAME = "salary";

    private final SalaryService salaryService;

    public SalaryResource(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    /**
     * POST  /salaries : Create a new salary.
     *
     * @param salaryDTO the salaryDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new salaryDTO, or with status 400 (Bad Request) if the salary has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/salaries")
    public ResponseEntity<SalaryDTO> createSalary(@Valid @RequestBody SalaryDTO salaryDTO) throws URISyntaxException {
        log.debug("REST request to save Salary : {}", salaryDTO);
        if (salaryDTO.getId() != null) {
            throw new BadRequestAlertException("A new salary cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SalaryDTO result = salaryService.save(salaryDTO);
        return ResponseEntity.created(new URI("/api/salaries/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /salaries : Updates an existing salary.
     *
     * @param salaryDTO the salaryDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated salaryDTO,
     * or with status 400 (Bad Request) if the salaryDTO is not valid,
     * or with status 500 (Internal Server Error) if the salaryDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/salaries")
    public ResponseEntity<SalaryDTO> updateSalary(@Valid @RequestBody SalaryDTO salaryDTO) throws URISyntaxException {
        log.debug("REST request to update Salary : {}", salaryDTO);
        if (salaryDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        SalaryDTO result = salaryService.save(salaryDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, salaryDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /salaries : get all the salaries.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of salaries in body
     */
    @GetMapping("/salaries")
    public ResponseEntity<List<SalaryDTO>> getAllSalaries(Pageable pageable) {
        log.debug("REST request to get a page of Salaries");
        Page<SalaryDTO> page = salaryService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /salaries/:id : get the "id" salary.
     *
     * @param id the id of the salaryDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the salaryDTO, or with status 404 (Not Found)
     */
    @GetMapping("/salaries/{id}")
    public ResponseEntity<SalaryDTO> getSalary(@PathVariable Long id) {
        log.debug("REST request to get Salary : {}", id);
        Optional<SalaryDTO> salaryDTO = salaryService.findOne(id);
        return ResponseUtil.wrapOrNotFound(salaryDTO);
    }

    /**
     * DELETE  /salaries/:id : delete the "id" salary.
     *
     * @param id the id of the salaryDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/salaries/{id}")
    public ResponseEntity<Void> deleteSalary(@PathVariable Long id) {
        log.debug("REST request to delete Salary : {}", id);
        salaryService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/salaries?query=:query : search for the salary corresponding
     * to the query.
     *
     * @param query the query of the salary search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/salaries")
    public ResponseEntity<List<SalaryDTO>> searchSalaries(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Salaries for query {}", query);
        Page<SalaryDTO> page = salaryService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/salaries");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
