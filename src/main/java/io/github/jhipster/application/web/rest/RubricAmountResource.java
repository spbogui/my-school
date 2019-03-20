package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.RubricAmountService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.RubricAmountDTO;
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
 * REST controller for managing RubricAmount.
 */
@RestController
@RequestMapping("/api")
public class RubricAmountResource {

    private final Logger log = LoggerFactory.getLogger(RubricAmountResource.class);

    private static final String ENTITY_NAME = "rubricAmount";

    private final RubricAmountService rubricAmountService;

    public RubricAmountResource(RubricAmountService rubricAmountService) {
        this.rubricAmountService = rubricAmountService;
    }

    /**
     * POST  /rubric-amounts : Create a new rubricAmount.
     *
     * @param rubricAmountDTO the rubricAmountDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rubricAmountDTO, or with status 400 (Bad Request) if the rubricAmount has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rubric-amounts")
    public ResponseEntity<RubricAmountDTO> createRubricAmount(@Valid @RequestBody RubricAmountDTO rubricAmountDTO) throws URISyntaxException {
        log.debug("REST request to save RubricAmount : {}", rubricAmountDTO);
        if (rubricAmountDTO.getId() != null) {
            throw new BadRequestAlertException("A new rubricAmount cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RubricAmountDTO result = rubricAmountService.save(rubricAmountDTO);
        return ResponseEntity.created(new URI("/api/rubric-amounts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rubric-amounts : Updates an existing rubricAmount.
     *
     * @param rubricAmountDTO the rubricAmountDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rubricAmountDTO,
     * or with status 400 (Bad Request) if the rubricAmountDTO is not valid,
     * or with status 500 (Internal Server Error) if the rubricAmountDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rubric-amounts")
    public ResponseEntity<RubricAmountDTO> updateRubricAmount(@Valid @RequestBody RubricAmountDTO rubricAmountDTO) throws URISyntaxException {
        log.debug("REST request to update RubricAmount : {}", rubricAmountDTO);
        if (rubricAmountDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RubricAmountDTO result = rubricAmountService.save(rubricAmountDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rubricAmountDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rubric-amounts : get all the rubricAmounts.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rubricAmounts in body
     */
    @GetMapping("/rubric-amounts")
    public ResponseEntity<List<RubricAmountDTO>> getAllRubricAmounts(Pageable pageable) {
        log.debug("REST request to get a page of RubricAmounts");
        Page<RubricAmountDTO> page = rubricAmountService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rubric-amounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rubric-amounts/:id : get the "id" rubricAmount.
     *
     * @param id the id of the rubricAmountDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rubricAmountDTO, or with status 404 (Not Found)
     */
    @GetMapping("/rubric-amounts/{id}")
    public ResponseEntity<RubricAmountDTO> getRubricAmount(@PathVariable Long id) {
        log.debug("REST request to get RubricAmount : {}", id);
        Optional<RubricAmountDTO> rubricAmountDTO = rubricAmountService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rubricAmountDTO);
    }

    /**
     * DELETE  /rubric-amounts/:id : delete the "id" rubricAmount.
     *
     * @param id the id of the rubricAmountDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rubric-amounts/{id}")
    public ResponseEntity<Void> deleteRubricAmount(@PathVariable Long id) {
        log.debug("REST request to delete RubricAmount : {}", id);
        rubricAmountService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/rubric-amounts?query=:query : search for the rubricAmount corresponding
     * to the query.
     *
     * @param query the query of the rubricAmount search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/rubric-amounts")
    public ResponseEntity<List<RubricAmountDTO>> searchRubricAmounts(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of RubricAmounts for query {}", query);
        Page<RubricAmountDTO> page = rubricAmountService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/rubric-amounts");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
