package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Rubric;
import io.github.jhipster.application.service.RubricService;
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
 * REST controller for managing Rubric.
 */
@RestController
@RequestMapping("/api")
public class RubricResource {

    private final Logger log = LoggerFactory.getLogger(RubricResource.class);

    private static final String ENTITY_NAME = "rubric";

    private final RubricService rubricService;

    public RubricResource(RubricService rubricService) {
        this.rubricService = rubricService;
    }

    /**
     * POST  /rubrics : Create a new rubric.
     *
     * @param rubric the rubric to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rubric, or with status 400 (Bad Request) if the rubric has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rubrics")
    public ResponseEntity<Rubric> createRubric(@Valid @RequestBody Rubric rubric) throws URISyntaxException {
        log.debug("REST request to save Rubric : {}", rubric);
        if (rubric.getId() != null) {
            throw new BadRequestAlertException("A new rubric cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Rubric result = rubricService.save(rubric);
        return ResponseEntity.created(new URI("/api/rubrics/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rubrics : Updates an existing rubric.
     *
     * @param rubric the rubric to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rubric,
     * or with status 400 (Bad Request) if the rubric is not valid,
     * or with status 500 (Internal Server Error) if the rubric couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rubrics")
    public ResponseEntity<Rubric> updateRubric(@Valid @RequestBody Rubric rubric) throws URISyntaxException {
        log.debug("REST request to update Rubric : {}", rubric);
        if (rubric.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Rubric result = rubricService.save(rubric);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rubric.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rubrics : get all the rubrics.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rubrics in body
     */
    @GetMapping("/rubrics")
    public List<Rubric> getAllRubrics() {
        log.debug("REST request to get all Rubrics");
        return rubricService.findAll();
    }

    /**
     * GET  /rubrics/:id : get the "id" rubric.
     *
     * @param id the id of the rubric to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rubric, or with status 404 (Not Found)
     */
    @GetMapping("/rubrics/{id}")
    public ResponseEntity<Rubric> getRubric(@PathVariable Long id) {
        log.debug("REST request to get Rubric : {}", id);
        Optional<Rubric> rubric = rubricService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubric);
    }

    /**
     * DELETE  /rubrics/:id : delete the "id" rubric.
     *
     * @param id the id of the rubric to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rubrics/{id}")
    public ResponseEntity<Void> deleteRubric(@PathVariable Long id) {
        log.debug("REST request to delete Rubric : {}", id);
        rubricService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rubrics?query=:query : search for the rubric corresponding
     * to the query.
     *
     * @param query the query of the rubric search
     * @return the result of the search
     */
    @GetMapping("/_search/rubrics")
    public List<Rubric> searchRubrics(@RequestParam String query) {
        log.debug("REST request to search Rubrics for query {}", query);
        return rubricService.search(query);
    }

}
