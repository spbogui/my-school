package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Regimen;
import io.github.jhipster.application.service.RegimenService;
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
 * REST controller for managing Regimen.
 */
@RestController
@RequestMapping("/api")
public class RegimenResource {

    private final Logger log = LoggerFactory.getLogger(RegimenResource.class);

    private static final String ENTITY_NAME = "regimen";

    private final RegimenService regimenService;

    public RegimenResource(RegimenService regimenService) {
        this.regimenService = regimenService;
    }

    /**
     * POST  /regimen : Create a new regimen.
     *
     * @param regimen the regimen to create
     * @return the ResponseEntity with status 201 (Created) and with body the new regimen, or with status 400 (Bad Request) if the regimen has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/regimen")
    public ResponseEntity<Regimen> createRegimen(@Valid @RequestBody Regimen regimen) throws URISyntaxException {
        log.debug("REST request to save Regimen : {}", regimen);
        if (regimen.getId() != null) {
            throw new BadRequestAlertException("A new regimen cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Regimen result = regimenService.save(regimen);
        return ResponseEntity.created(new URI("/api/regimen/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /regimen : Updates an existing regimen.
     *
     * @param regimen the regimen to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated regimen,
     * or with status 400 (Bad Request) if the regimen is not valid,
     * or with status 500 (Internal Server Error) if the regimen couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/regimen")
    public ResponseEntity<Regimen> updateRegimen(@Valid @RequestBody Regimen regimen) throws URISyntaxException {
        log.debug("REST request to update Regimen : {}", regimen);
        if (regimen.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Regimen result = regimenService.save(regimen);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, regimen.getId().toString()))
            .body(result);
    }

    /**
     * GET  /regimen : get all the regimen.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of regimen in body
     */
    @GetMapping("/regimen")
    public List<Regimen> getAllRegimen() {
        log.debug("REST request to get all Regimen");
        return regimenService.findAll();
    }

    /**
     * GET  /regimen/:id : get the "id" regimen.
     *
     * @param id the id of the regimen to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the regimen, or with status 404 (Not Found)
     */
    @GetMapping("/regimen/{id}")
    public ResponseEntity<Regimen> getRegimen(@PathVariable Long id) {
        log.debug("REST request to get Regimen : {}", id);
        Optional<Regimen> regimen = regimenService.findOne(id);
        return ResponseUtil.wrapOrNotFound(regimen);
    }

    /**
     * DELETE  /regimen/:id : delete the "id" regimen.
     *
     * @param id the id of the regimen to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/regimen/{id}")
    public ResponseEntity<Void> deleteRegimen(@PathVariable Long id) {
        log.debug("REST request to delete Regimen : {}", id);
        regimenService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/regimen?query=:query : search for the regimen corresponding
     * to the query.
     *
     * @param query the query of the regimen search
     * @return the result of the search
     */
    @GetMapping("/_search/regimen")
    public List<Regimen> searchRegimen(@RequestParam String query) {
        log.debug("REST request to search Regimen for query {}", query);
        return regimenService.search(query);
    }

}
