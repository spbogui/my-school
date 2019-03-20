package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ActorIdentifierNumberService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ActorIdentifierNumberDTO;
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
 * REST controller for managing ActorIdentifierNumber.
 */
@RestController
@RequestMapping("/api")
public class ActorIdentifierNumberResource {

    private final Logger log = LoggerFactory.getLogger(ActorIdentifierNumberResource.class);

    private static final String ENTITY_NAME = "actorIdentifierNumber";

    private final ActorIdentifierNumberService actorIdentifierNumberService;

    public ActorIdentifierNumberResource(ActorIdentifierNumberService actorIdentifierNumberService) {
        this.actorIdentifierNumberService = actorIdentifierNumberService;
    }

    /**
     * POST  /actor-identifier-numbers : Create a new actorIdentifierNumber.
     *
     * @param actorIdentifierNumberDTO the actorIdentifierNumberDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actorIdentifierNumberDTO, or with status 400 (Bad Request) if the actorIdentifierNumber has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actor-identifier-numbers")
    public ResponseEntity<ActorIdentifierNumberDTO> createActorIdentifierNumber(@Valid @RequestBody ActorIdentifierNumberDTO actorIdentifierNumberDTO) throws URISyntaxException {
        log.debug("REST request to save ActorIdentifierNumber : {}", actorIdentifierNumberDTO);
        if (actorIdentifierNumberDTO.getId() != null) {
            throw new BadRequestAlertException("A new actorIdentifierNumber cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActorIdentifierNumberDTO result = actorIdentifierNumberService.save(actorIdentifierNumberDTO);
        return ResponseEntity.created(new URI("/api/actor-identifier-numbers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actor-identifier-numbers : Updates an existing actorIdentifierNumber.
     *
     * @param actorIdentifierNumberDTO the actorIdentifierNumberDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actorIdentifierNumberDTO,
     * or with status 400 (Bad Request) if the actorIdentifierNumberDTO is not valid,
     * or with status 500 (Internal Server Error) if the actorIdentifierNumberDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actor-identifier-numbers")
    public ResponseEntity<ActorIdentifierNumberDTO> updateActorIdentifierNumber(@Valid @RequestBody ActorIdentifierNumberDTO actorIdentifierNumberDTO) throws URISyntaxException {
        log.debug("REST request to update ActorIdentifierNumber : {}", actorIdentifierNumberDTO);
        if (actorIdentifierNumberDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActorIdentifierNumberDTO result = actorIdentifierNumberService.save(actorIdentifierNumberDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actorIdentifierNumberDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actor-identifier-numbers : get all the actorIdentifierNumbers.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actorIdentifierNumbers in body
     */
    @GetMapping("/actor-identifier-numbers")
    public ResponseEntity<List<ActorIdentifierNumberDTO>> getAllActorIdentifierNumbers(Pageable pageable) {
        log.debug("REST request to get a page of ActorIdentifierNumbers");
        Page<ActorIdentifierNumberDTO> page = actorIdentifierNumberService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actor-identifier-numbers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /actor-identifier-numbers/:id : get the "id" actorIdentifierNumber.
     *
     * @param id the id of the actorIdentifierNumberDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actorIdentifierNumberDTO, or with status 404 (Not Found)
     */
    @GetMapping("/actor-identifier-numbers/{id}")
    public ResponseEntity<ActorIdentifierNumberDTO> getActorIdentifierNumber(@PathVariable Long id) {
        log.debug("REST request to get ActorIdentifierNumber : {}", id);
        Optional<ActorIdentifierNumberDTO> actorIdentifierNumberDTO = actorIdentifierNumberService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actorIdentifierNumberDTO);
    }

    /**
     * DELETE  /actor-identifier-numbers/:id : delete the "id" actorIdentifierNumber.
     *
     * @param id the id of the actorIdentifierNumberDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actor-identifier-numbers/{id}")
    public ResponseEntity<Void> deleteActorIdentifierNumber(@PathVariable Long id) {
        log.debug("REST request to delete ActorIdentifierNumber : {}", id);
        actorIdentifierNumberService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/actor-identifier-numbers?query=:query : search for the actorIdentifierNumber corresponding
     * to the query.
     *
     * @param query the query of the actorIdentifierNumber search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/actor-identifier-numbers")
    public ResponseEntity<List<ActorIdentifierNumberDTO>> searchActorIdentifierNumbers(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActorIdentifierNumbers for query {}", query);
        Page<ActorIdentifierNumberDTO> page = actorIdentifierNumberService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/actor-identifier-numbers");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
