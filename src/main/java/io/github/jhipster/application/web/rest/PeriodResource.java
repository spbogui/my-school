package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.PeriodService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.PeriodDTO;
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
 * REST controller for managing Period.
 */
@RestController
@RequestMapping("/api")
public class PeriodResource {

    private final Logger log = LoggerFactory.getLogger(PeriodResource.class);

    private static final String ENTITY_NAME = "period";

    private final PeriodService periodService;

    public PeriodResource(PeriodService periodService) {
        this.periodService = periodService;
    }

    /**
     * POST  /periods : Create a new period.
     *
     * @param periodDTO the periodDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodDTO, or with status 400 (Bad Request) if the period has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/periods")
    public ResponseEntity<PeriodDTO> createPeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to save Period : {}", periodDTO);
        if (periodDTO.getId() != null) {
            throw new BadRequestAlertException("A new period cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.created(new URI("/api/periods/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /periods : Updates an existing period.
     *
     * @param periodDTO the periodDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodDTO,
     * or with status 400 (Bad Request) if the periodDTO is not valid,
     * or with status 500 (Internal Server Error) if the periodDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/periods")
    public ResponseEntity<PeriodDTO> updatePeriod(@Valid @RequestBody PeriodDTO periodDTO) throws URISyntaxException {
        log.debug("REST request to update Period : {}", periodDTO);
        if (periodDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodDTO result = periodService.save(periodDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /periods : get all the periods.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of periods in body
     */
    @GetMapping("/periods")
    public ResponseEntity<List<PeriodDTO>> getAllPeriods(Pageable pageable) {
        log.debug("REST request to get a page of Periods");
        Page<PeriodDTO> page = periodService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/periods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /periods/:id : get the "id" period.
     *
     * @param id the id of the periodDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodDTO, or with status 404 (Not Found)
     */
    @GetMapping("/periods/{id}")
    public ResponseEntity<PeriodDTO> getPeriod(@PathVariable Long id) {
        log.debug("REST request to get Period : {}", id);
        Optional<PeriodDTO> periodDTO = periodService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodDTO);
    }

    /**
     * DELETE  /periods/:id : delete the "id" period.
     *
     * @param id the id of the periodDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/periods/{id}")
    public ResponseEntity<Void> deletePeriod(@PathVariable Long id) {
        log.debug("REST request to delete Period : {}", id);
        periodService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/periods?query=:query : search for the period corresponding
     * to the query.
     *
     * @param query the query of the period search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/periods")
    public ResponseEntity<List<PeriodDTO>> searchPeriods(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Periods for query {}", query);
        Page<PeriodDTO> page = periodService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/periods");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
