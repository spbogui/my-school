package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Cycle;
import io.github.jhipster.application.service.CycleService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
 * REST controller for managing Cycle.
 */
@RestController
@RequestMapping("/api")
public class CycleResource {

    private final Logger log = LoggerFactory.getLogger(CycleResource.class);

    private static final String ENTITY_NAME = "cycle";

    private final CycleService cycleService;

    public CycleResource(CycleService cycleService) {
        this.cycleService = cycleService;
    }

    /**
     * POST  /cycles : Create a new cycle.
     *
     * @param cycle the cycle to create
     * @return the ResponseEntity with status 201 (Created) and with body the new cycle, or with status 400 (Bad Request) if the cycle has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/cycles")
    public ResponseEntity<Cycle> createCycle(@Valid @RequestBody Cycle cycle) throws URISyntaxException {
        log.debug("REST request to save Cycle : {}", cycle);
        if (cycle.getId() != null) {
            throw new BadRequestAlertException("A new cycle cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Cycle result = cycleService.save(cycle);
        return ResponseEntity.created(new URI("/api/cycles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /cycles : Updates an existing cycle.
     *
     * @param cycle the cycle to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated cycle,
     * or with status 400 (Bad Request) if the cycle is not valid,
     * or with status 500 (Internal Server Error) if the cycle couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/cycles")
    public ResponseEntity<Cycle> updateCycle(@Valid @RequestBody Cycle cycle) throws URISyntaxException {
        log.debug("REST request to update Cycle : {}", cycle);
        if (cycle.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Cycle result = cycleService.save(cycle);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, cycle.getId().toString()))
            .body(result);
    }

    /**
     * GET  /cycles : get all the cycles.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of cycles in body
     */
    @GetMapping("/cycles")
    public List<Cycle> getAllCycles() {
        log.debug("REST request to get all Cycles");
        return cycleService.findAll();
    }

    /**
     * GET  /cycles/:id : get the "id" cycle.
     *
     * @param id the id of the cycle to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the cycle, or with status 404 (Not Found)
     */
    @GetMapping("/cycles/{id}")
    public ResponseEntity<Cycle> getCycle(@PathVariable Long id) {
        log.debug("REST request to get Cycle : {}", id);
        Optional<Cycle> cycle = cycleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(cycle);
    }

    /**
     * DELETE  /cycles/:id : delete the "id" cycle.
     *
     * @param id the id of the cycle to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/cycles/{id}")
    public ResponseEntity<Void> deleteCycle(@PathVariable Long id) {
        log.debug("REST request to delete Cycle : {}", id);
        cycleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/cycles?query=:query : search for the cycle corresponding
     * to the query.
     *
     * @param query the query of the cycle search
     * @return the result of the search
     */
    @GetMapping("/_search/cycles")
    public List<Cycle> searchCycles(@RequestParam String query) {
        log.debug("REST request to search Cycles for query {}", query);
        return cycleService.search(query);
    }

}
