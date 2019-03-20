package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.PeriodType;
import io.github.jhipster.application.service.PeriodTypeService;
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
 * REST controller for managing PeriodType.
 */
@RestController
@RequestMapping("/api")
public class PeriodTypeResource {

    private final Logger log = LoggerFactory.getLogger(PeriodTypeResource.class);

    private static final String ENTITY_NAME = "periodType";

    private final PeriodTypeService periodTypeService;

    public PeriodTypeResource(PeriodTypeService periodTypeService) {
        this.periodTypeService = periodTypeService;
    }

    /**
     * POST  /period-types : Create a new periodType.
     *
     * @param periodType the periodType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new periodType, or with status 400 (Bad Request) if the periodType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/period-types")
    public ResponseEntity<PeriodType> createPeriodType(@Valid @RequestBody PeriodType periodType) throws URISyntaxException {
        log.debug("REST request to save PeriodType : {}", periodType);
        if (periodType.getId() != null) {
            throw new BadRequestAlertException("A new periodType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PeriodType result = periodTypeService.save(periodType);
        return ResponseEntity.created(new URI("/api/period-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /period-types : Updates an existing periodType.
     *
     * @param periodType the periodType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated periodType,
     * or with status 400 (Bad Request) if the periodType is not valid,
     * or with status 500 (Internal Server Error) if the periodType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/period-types")
    public ResponseEntity<PeriodType> updatePeriodType(@Valid @RequestBody PeriodType periodType) throws URISyntaxException {
        log.debug("REST request to update PeriodType : {}", periodType);
        if (periodType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PeriodType result = periodTypeService.save(periodType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, periodType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /period-types : get all the periodTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of periodTypes in body
     */
    @GetMapping("/period-types")
    public List<PeriodType> getAllPeriodTypes() {
        log.debug("REST request to get all PeriodTypes");
        return periodTypeService.findAll();
    }

    /**
     * GET  /period-types/:id : get the "id" periodType.
     *
     * @param id the id of the periodType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the periodType, or with status 404 (Not Found)
     */
    @GetMapping("/period-types/{id}")
    public ResponseEntity<PeriodType> getPeriodType(@PathVariable Long id) {
        log.debug("REST request to get PeriodType : {}", id);
        Optional<PeriodType> periodType = periodTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(periodType);
    }

    /**
     * DELETE  /period-types/:id : delete the "id" periodType.
     *
     * @param id the id of the periodType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/period-types/{id}")
    public ResponseEntity<Void> deletePeriodType(@PathVariable Long id) {
        log.debug("REST request to delete PeriodType : {}", id);
        periodTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/period-types?query=:query : search for the periodType corresponding
     * to the query.
     *
     * @param query the query of the periodType search
     * @return the result of the search
     */
    @GetMapping("/_search/period-types")
    public List<PeriodType> searchPeriodTypes(@RequestParam String query) {
        log.debug("REST request to search PeriodTypes for query {}", query);
        return periodTypeService.search(query);
    }

}
