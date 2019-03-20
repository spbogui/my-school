package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ActorRelationshipService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ActorRelationshipDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * REST controller for managing ActorRelationship.
 */
@RestController
@RequestMapping("/api")
public class ActorRelationshipResource {

    private final Logger log = LoggerFactory.getLogger(ActorRelationshipResource.class);

    private static final String ENTITY_NAME = "actorRelationship";

    private final ActorRelationshipService actorRelationshipService;

    public ActorRelationshipResource(ActorRelationshipService actorRelationshipService) {
        this.actorRelationshipService = actorRelationshipService;
    }

    /**
     * POST  /actor-relationships : Create a new actorRelationship.
     *
     * @param actorRelationshipDTO the actorRelationshipDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actorRelationshipDTO, or with status 400 (Bad Request) if the actorRelationship has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actor-relationships")
    public ResponseEntity<ActorRelationshipDTO> createActorRelationship(@RequestBody ActorRelationshipDTO actorRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to save ActorRelationship : {}", actorRelationshipDTO);
        if (actorRelationshipDTO.getId() != null) {
            throw new BadRequestAlertException("A new actorRelationship cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActorRelationshipDTO result = actorRelationshipService.save(actorRelationshipDTO);
        return ResponseEntity.created(new URI("/api/actor-relationships/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actor-relationships : Updates an existing actorRelationship.
     *
     * @param actorRelationshipDTO the actorRelationshipDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actorRelationshipDTO,
     * or with status 400 (Bad Request) if the actorRelationshipDTO is not valid,
     * or with status 500 (Internal Server Error) if the actorRelationshipDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actor-relationships")
    public ResponseEntity<ActorRelationshipDTO> updateActorRelationship(@RequestBody ActorRelationshipDTO actorRelationshipDTO) throws URISyntaxException {
        log.debug("REST request to update ActorRelationship : {}", actorRelationshipDTO);
        if (actorRelationshipDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActorRelationshipDTO result = actorRelationshipService.save(actorRelationshipDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actorRelationshipDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actor-relationships : get all the actorRelationships.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actorRelationships in body
     */
    @GetMapping("/actor-relationships")
    public ResponseEntity<List<ActorRelationshipDTO>> getAllActorRelationships(Pageable pageable) {
        log.debug("REST request to get a page of ActorRelationships");
        Page<ActorRelationshipDTO> page = actorRelationshipService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actor-relationships");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /actor-relationships/:id : get the "id" actorRelationship.
     *
     * @param id the id of the actorRelationshipDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actorRelationshipDTO, or with status 404 (Not Found)
     */
    @GetMapping("/actor-relationships/{id}")
    public ResponseEntity<ActorRelationshipDTO> getActorRelationship(@PathVariable Long id) {
        log.debug("REST request to get ActorRelationship : {}", id);
        Optional<ActorRelationshipDTO> actorRelationshipDTO = actorRelationshipService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actorRelationshipDTO);
    }

    /**
     * DELETE  /actor-relationships/:id : delete the "id" actorRelationship.
     *
     * @param id the id of the actorRelationshipDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actor-relationships/{id}")
    public ResponseEntity<Void> deleteActorRelationship(@PathVariable Long id) {
        log.debug("REST request to delete ActorRelationship : {}", id);
        actorRelationshipService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/actor-relationships?query=:query : search for the actorRelationship corresponding
     * to the query.
     *
     * @param query the query of the actorRelationship search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/actor-relationships")
    public ResponseEntity<List<ActorRelationshipDTO>> searchActorRelationships(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActorRelationships for query {}", query);
        Page<ActorRelationshipDTO> page = actorRelationshipService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/actor-relationships");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
