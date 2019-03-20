package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.LeaveHoliDayService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.LeaveHoliDayDTO;
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
 * REST controller for managing LeaveHoliDay.
 */
@RestController
@RequestMapping("/api")
public class LeaveHoliDayResource {

    private final Logger log = LoggerFactory.getLogger(LeaveHoliDayResource.class);

    private static final String ENTITY_NAME = "leaveHoliDay";

    private final LeaveHoliDayService leaveHoliDayService;

    public LeaveHoliDayResource(LeaveHoliDayService leaveHoliDayService) {
        this.leaveHoliDayService = leaveHoliDayService;
    }

    /**
     * POST  /leave-holi-days : Create a new leaveHoliDay.
     *
     * @param leaveHoliDayDTO the leaveHoliDayDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new leaveHoliDayDTO, or with status 400 (Bad Request) if the leaveHoliDay has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/leave-holi-days")
    public ResponseEntity<LeaveHoliDayDTO> createLeaveHoliDay(@Valid @RequestBody LeaveHoliDayDTO leaveHoliDayDTO) throws URISyntaxException {
        log.debug("REST request to save LeaveHoliDay : {}", leaveHoliDayDTO);
        if (leaveHoliDayDTO.getId() != null) {
            throw new BadRequestAlertException("A new leaveHoliDay cannot already have an ID", ENTITY_NAME, "idexists");
        }
        LeaveHoliDayDTO result = leaveHoliDayService.save(leaveHoliDayDTO);
        return ResponseEntity.created(new URI("/api/leave-holi-days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /leave-holi-days : Updates an existing leaveHoliDay.
     *
     * @param leaveHoliDayDTO the leaveHoliDayDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated leaveHoliDayDTO,
     * or with status 400 (Bad Request) if the leaveHoliDayDTO is not valid,
     * or with status 500 (Internal Server Error) if the leaveHoliDayDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/leave-holi-days")
    public ResponseEntity<LeaveHoliDayDTO> updateLeaveHoliDay(@Valid @RequestBody LeaveHoliDayDTO leaveHoliDayDTO) throws URISyntaxException {
        log.debug("REST request to update LeaveHoliDay : {}", leaveHoliDayDTO);
        if (leaveHoliDayDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        LeaveHoliDayDTO result = leaveHoliDayService.save(leaveHoliDayDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, leaveHoliDayDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /leave-holi-days : get all the leaveHoliDays.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of leaveHoliDays in body
     */
    @GetMapping("/leave-holi-days")
    public ResponseEntity<List<LeaveHoliDayDTO>> getAllLeaveHoliDays(Pageable pageable) {
        log.debug("REST request to get a page of LeaveHoliDays");
        Page<LeaveHoliDayDTO> page = leaveHoliDayService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/leave-holi-days");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /leave-holi-days/:id : get the "id" leaveHoliDay.
     *
     * @param id the id of the leaveHoliDayDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the leaveHoliDayDTO, or with status 404 (Not Found)
     */
    @GetMapping("/leave-holi-days/{id}")
    public ResponseEntity<LeaveHoliDayDTO> getLeaveHoliDay(@PathVariable Long id) {
        log.debug("REST request to get LeaveHoliDay : {}", id);
        Optional<LeaveHoliDayDTO> leaveHoliDayDTO = leaveHoliDayService.findOne(id);
        return ResponseUtil.wrapOrNotFound(leaveHoliDayDTO);
    }

    /**
     * DELETE  /leave-holi-days/:id : delete the "id" leaveHoliDay.
     *
     * @param id the id of the leaveHoliDayDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/leave-holi-days/{id}")
    public ResponseEntity<Void> deleteLeaveHoliDay(@PathVariable Long id) {
        log.debug("REST request to delete LeaveHoliDay : {}", id);
        leaveHoliDayService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/leave-holi-days?query=:query : search for the leaveHoliDay corresponding
     * to the query.
     *
     * @param query the query of the leaveHoliDay search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/leave-holi-days")
    public ResponseEntity<List<LeaveHoliDayDTO>> searchLeaveHoliDays(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of LeaveHoliDays for query {}", query);
        Page<LeaveHoliDayDTO> page = leaveHoliDayService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/leave-holi-days");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
