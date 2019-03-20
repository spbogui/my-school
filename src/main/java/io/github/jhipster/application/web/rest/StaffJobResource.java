package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.StaffJobService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.StaffJobDTO;
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
 * REST controller for managing StaffJob.
 */
@RestController
@RequestMapping("/api")
public class StaffJobResource {

    private final Logger log = LoggerFactory.getLogger(StaffJobResource.class);

    private static final String ENTITY_NAME = "staffJob";

    private final StaffJobService staffJobService;

    public StaffJobResource(StaffJobService staffJobService) {
        this.staffJobService = staffJobService;
    }

    /**
     * POST  /staff-jobs : Create a new staffJob.
     *
     * @param staffJobDTO the staffJobDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new staffJobDTO, or with status 400 (Bad Request) if the staffJob has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/staff-jobs")
    public ResponseEntity<StaffJobDTO> createStaffJob(@Valid @RequestBody StaffJobDTO staffJobDTO) throws URISyntaxException {
        log.debug("REST request to save StaffJob : {}", staffJobDTO);
        if (staffJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new staffJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaffJobDTO result = staffJobService.save(staffJobDTO);
        return ResponseEntity.created(new URI("/api/staff-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /staff-jobs : Updates an existing staffJob.
     *
     * @param staffJobDTO the staffJobDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated staffJobDTO,
     * or with status 400 (Bad Request) if the staffJobDTO is not valid,
     * or with status 500 (Internal Server Error) if the staffJobDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/staff-jobs")
    public ResponseEntity<StaffJobDTO> updateStaffJob(@Valid @RequestBody StaffJobDTO staffJobDTO) throws URISyntaxException {
        log.debug("REST request to update StaffJob : {}", staffJobDTO);
        if (staffJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaffJobDTO result = staffJobService.save(staffJobDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, staffJobDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /staff-jobs : get all the staffJobs.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of staffJobs in body
     */
    @GetMapping("/staff-jobs")
    public ResponseEntity<List<StaffJobDTO>> getAllStaffJobs(Pageable pageable) {
        log.debug("REST request to get a page of StaffJobs");
        Page<StaffJobDTO> page = staffJobService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staff-jobs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /staff-jobs/:id : get the "id" staffJob.
     *
     * @param id the id of the staffJobDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the staffJobDTO, or with status 404 (Not Found)
     */
    @GetMapping("/staff-jobs/{id}")
    public ResponseEntity<StaffJobDTO> getStaffJob(@PathVariable Long id) {
        log.debug("REST request to get StaffJob : {}", id);
        Optional<StaffJobDTO> staffJobDTO = staffJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffJobDTO);
    }

    /**
     * DELETE  /staff-jobs/:id : delete the "id" staffJob.
     *
     * @param id the id of the staffJobDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/staff-jobs/{id}")
    public ResponseEntity<Void> deleteStaffJob(@PathVariable Long id) {
        log.debug("REST request to delete StaffJob : {}", id);
        staffJobService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/staff-jobs?query=:query : search for the staffJob corresponding
     * to the query.
     *
     * @param query the query of the staffJob search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/staff-jobs")
    public ResponseEntity<List<StaffJobDTO>> searchStaffJobs(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of StaffJobs for query {}", query);
        Page<StaffJobDTO> page = staffJobService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/staff-jobs");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
