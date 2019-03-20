package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Days;
import io.github.jhipster.application.service.DaysService;
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
 * REST controller for managing Days.
 */
@RestController
@RequestMapping("/api")
public class DaysResource {

    private final Logger log = LoggerFactory.getLogger(DaysResource.class);

    private static final String ENTITY_NAME = "days";

    private final DaysService daysService;

    public DaysResource(DaysService daysService) {
        this.daysService = daysService;
    }

    /**
     * POST  /days : Create a new days.
     *
     * @param days the days to create
     * @return the ResponseEntity with status 201 (Created) and with body the new days, or with status 400 (Bad Request) if the days has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/days")
    public ResponseEntity<Days> createDays(@Valid @RequestBody Days days) throws URISyntaxException {
        log.debug("REST request to save Days : {}", days);
        if (days.getId() != null) {
            throw new BadRequestAlertException("A new days cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Days result = daysService.save(days);
        return ResponseEntity.created(new URI("/api/days/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /days : Updates an existing days.
     *
     * @param days the days to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated days,
     * or with status 400 (Bad Request) if the days is not valid,
     * or with status 500 (Internal Server Error) if the days couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/days")
    public ResponseEntity<Days> updateDays(@Valid @RequestBody Days days) throws URISyntaxException {
        log.debug("REST request to update Days : {}", days);
        if (days.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Days result = daysService.save(days);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, days.getId().toString()))
            .body(result);
    }

    /**
     * GET  /days : get all the days.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of days in body
     */
    @GetMapping("/days")
    public List<Days> getAllDays() {
        log.debug("REST request to get all Days");
        return daysService.findAll();
    }

    /**
     * GET  /days/:id : get the "id" days.
     *
     * @param id the id of the days to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the days, or with status 404 (Not Found)
     */
    @GetMapping("/days/{id}")
    public ResponseEntity<Days> getDays(@PathVariable Long id) {
        log.debug("REST request to get Days : {}", id);
        Optional<Days> days = daysService.findOne(id);
        return ResponseUtil.wrapOrNotFound(days);
    }

    /**
     * DELETE  /days/:id : delete the "id" days.
     *
     * @param id the id of the days to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/days/{id}")
    public ResponseEntity<Void> deleteDays(@PathVariable Long id) {
        log.debug("REST request to delete Days : {}", id);
        daysService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/days?query=:query : search for the days corresponding
     * to the query.
     *
     * @param query the query of the days search
     * @return the result of the search
     */
    @GetMapping("/_search/days")
    public List<Days> searchDays(@RequestParam String query) {
        log.debug("REST request to search Days for query {}", query);
        return daysService.search(query);
    }

}
