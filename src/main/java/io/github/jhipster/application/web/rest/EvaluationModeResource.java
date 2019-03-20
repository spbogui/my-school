package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.EvaluationMode;
import io.github.jhipster.application.service.EvaluationModeService;
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
 * REST controller for managing EvaluationMode.
 */
@RestController
@RequestMapping("/api")
public class EvaluationModeResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationModeResource.class);

    private static final String ENTITY_NAME = "evaluationMode";

    private final EvaluationModeService evaluationModeService;

    public EvaluationModeResource(EvaluationModeService evaluationModeService) {
        this.evaluationModeService = evaluationModeService;
    }

    /**
     * POST  /evaluation-modes : Create a new evaluationMode.
     *
     * @param evaluationMode the evaluationMode to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationMode, or with status 400 (Bad Request) if the evaluationMode has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-modes")
    public ResponseEntity<EvaluationMode> createEvaluationMode(@Valid @RequestBody EvaluationMode evaluationMode) throws URISyntaxException {
        log.debug("REST request to save EvaluationMode : {}", evaluationMode);
        if (evaluationMode.getId() != null) {
            throw new BadRequestAlertException("A new evaluationMode cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationMode result = evaluationModeService.save(evaluationMode);
        return ResponseEntity.created(new URI("/api/evaluation-modes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-modes : Updates an existing evaluationMode.
     *
     * @param evaluationMode the evaluationMode to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationMode,
     * or with status 400 (Bad Request) if the evaluationMode is not valid,
     * or with status 500 (Internal Server Error) if the evaluationMode couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-modes")
    public ResponseEntity<EvaluationMode> updateEvaluationMode(@Valid @RequestBody EvaluationMode evaluationMode) throws URISyntaxException {
        log.debug("REST request to update EvaluationMode : {}", evaluationMode);
        if (evaluationMode.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationMode result = evaluationModeService.save(evaluationMode);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluationMode.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-modes : get all the evaluationModes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationModes in body
     */
    @GetMapping("/evaluation-modes")
    public List<EvaluationMode> getAllEvaluationModes() {
        log.debug("REST request to get all EvaluationModes");
        return evaluationModeService.findAll();
    }

    /**
     * GET  /evaluation-modes/:id : get the "id" evaluationMode.
     *
     * @param id the id of the evaluationMode to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationMode, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-modes/{id}")
    public ResponseEntity<EvaluationMode> getEvaluationMode(@PathVariable Long id) {
        log.debug("REST request to get EvaluationMode : {}", id);
        Optional<EvaluationMode> evaluationMode = evaluationModeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationMode);
    }

    /**
     * DELETE  /evaluation-modes/:id : delete the "id" evaluationMode.
     *
     * @param id the id of the evaluationMode to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-modes/{id}")
    public ResponseEntity<Void> deleteEvaluationMode(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationMode : {}", id);
        evaluationModeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-modes?query=:query : search for the evaluationMode corresponding
     * to the query.
     *
     * @param query the query of the evaluationMode search
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-modes")
    public List<EvaluationMode> searchEvaluationModes(@RequestParam String query) {
        log.debug("REST request to search EvaluationModes for query {}", query);
        return evaluationModeService.search(query);
    }

}
