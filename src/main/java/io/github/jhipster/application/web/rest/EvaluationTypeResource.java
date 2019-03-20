package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.EvaluationType;
import io.github.jhipster.application.service.EvaluationTypeService;
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
 * REST controller for managing EvaluationType.
 */
@RestController
@RequestMapping("/api")
public class EvaluationTypeResource {

    private final Logger log = LoggerFactory.getLogger(EvaluationTypeResource.class);

    private static final String ENTITY_NAME = "evaluationType";

    private final EvaluationTypeService evaluationTypeService;

    public EvaluationTypeResource(EvaluationTypeService evaluationTypeService) {
        this.evaluationTypeService = evaluationTypeService;
    }

    /**
     * POST  /evaluation-types : Create a new evaluationType.
     *
     * @param evaluationType the evaluationType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new evaluationType, or with status 400 (Bad Request) if the evaluationType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/evaluation-types")
    public ResponseEntity<EvaluationType> createEvaluationType(@Valid @RequestBody EvaluationType evaluationType) throws URISyntaxException {
        log.debug("REST request to save EvaluationType : {}", evaluationType);
        if (evaluationType.getId() != null) {
            throw new BadRequestAlertException("A new evaluationType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EvaluationType result = evaluationTypeService.save(evaluationType);
        return ResponseEntity.created(new URI("/api/evaluation-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /evaluation-types : Updates an existing evaluationType.
     *
     * @param evaluationType the evaluationType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated evaluationType,
     * or with status 400 (Bad Request) if the evaluationType is not valid,
     * or with status 500 (Internal Server Error) if the evaluationType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/evaluation-types")
    public ResponseEntity<EvaluationType> updateEvaluationType(@Valid @RequestBody EvaluationType evaluationType) throws URISyntaxException {
        log.debug("REST request to update EvaluationType : {}", evaluationType);
        if (evaluationType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EvaluationType result = evaluationTypeService.save(evaluationType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, evaluationType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /evaluation-types : get all the evaluationTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of evaluationTypes in body
     */
    @GetMapping("/evaluation-types")
    public List<EvaluationType> getAllEvaluationTypes() {
        log.debug("REST request to get all EvaluationTypes");
        return evaluationTypeService.findAll();
    }

    /**
     * GET  /evaluation-types/:id : get the "id" evaluationType.
     *
     * @param id the id of the evaluationType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the evaluationType, or with status 404 (Not Found)
     */
    @GetMapping("/evaluation-types/{id}")
    public ResponseEntity<EvaluationType> getEvaluationType(@PathVariable Long id) {
        log.debug("REST request to get EvaluationType : {}", id);
        Optional<EvaluationType> evaluationType = evaluationTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(evaluationType);
    }

    /**
     * DELETE  /evaluation-types/:id : delete the "id" evaluationType.
     *
     * @param id the id of the evaluationType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/evaluation-types/{id}")
    public ResponseEntity<Void> deleteEvaluationType(@PathVariable Long id) {
        log.debug("REST request to delete EvaluationType : {}", id);
        evaluationTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/evaluation-types?query=:query : search for the evaluationType corresponding
     * to the query.
     *
     * @param query the query of the evaluationType search
     * @return the result of the search
     */
    @GetMapping("/_search/evaluation-types")
    public List<EvaluationType> searchEvaluationTypes(@RequestParam String query) {
        log.debug("REST request to search EvaluationTypes for query {}", query);
        return evaluationTypeService.search(query);
    }

}
