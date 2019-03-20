package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.StaffService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.StaffDTO;
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
 * REST controller for managing Staff.
 */
@RestController
@RequestMapping("/api")
public class StaffResource {

    private final Logger log = LoggerFactory.getLogger(StaffResource.class);

    private static final String ENTITY_NAME = "staff";

    private final StaffService staffService;

    public StaffResource(StaffService staffService) {
        this.staffService = staffService;
    }

    /**
     * POST  /staff : Create a new staff.
     *
     * @param staffDTO the staffDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new staffDTO, or with status 400 (Bad Request) if the staff has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/staff")
    public ResponseEntity<StaffDTO> createStaff(@RequestBody StaffDTO staffDTO) throws URISyntaxException {
        log.debug("REST request to save Staff : {}", staffDTO);
        if (staffDTO.getId() != null) {
            throw new BadRequestAlertException("A new staff cannot already have an ID", ENTITY_NAME, "idexists");
        }
        StaffDTO result = staffService.save(staffDTO);
        return ResponseEntity.created(new URI("/api/staff/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /staff : Updates an existing staff.
     *
     * @param staffDTO the staffDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated staffDTO,
     * or with status 400 (Bad Request) if the staffDTO is not valid,
     * or with status 500 (Internal Server Error) if the staffDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/staff")
    public ResponseEntity<StaffDTO> updateStaff(@RequestBody StaffDTO staffDTO) throws URISyntaxException {
        log.debug("REST request to update Staff : {}", staffDTO);
        if (staffDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        StaffDTO result = staffService.save(staffDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, staffDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /staff : get all the staff.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of staff in body
     */
    @GetMapping("/staff")
    public ResponseEntity<List<StaffDTO>> getAllStaff(Pageable pageable) {
        log.debug("REST request to get a page of Staff");
        Page<StaffDTO> page = staffService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/staff");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /staff/:id : get the "id" staff.
     *
     * @param id the id of the staffDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the staffDTO, or with status 404 (Not Found)
     */
    @GetMapping("/staff/{id}")
    public ResponseEntity<StaffDTO> getStaff(@PathVariable Long id) {
        log.debug("REST request to get Staff : {}", id);
        Optional<StaffDTO> staffDTO = staffService.findOne(id);
        return ResponseUtil.wrapOrNotFound(staffDTO);
    }

    /**
     * DELETE  /staff/:id : delete the "id" staff.
     *
     * @param id the id of the staffDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/staff/{id}")
    public ResponseEntity<Void> deleteStaff(@PathVariable Long id) {
        log.debug("REST request to delete Staff : {}", id);
        staffService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/staff?query=:query : search for the staff corresponding
     * to the query.
     *
     * @param query the query of the staff search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/staff")
    public ResponseEntity<List<StaffDTO>> searchStaff(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Staff for query {}", query);
        Page<StaffDTO> page = staffService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/staff");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
