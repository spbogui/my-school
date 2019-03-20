package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ActorNameService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ActorNameDTO;
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
 * REST controller for managing ActorName.
 */
@RestController
@RequestMapping("/api")
public class ActorNameResource {

    private final Logger log = LoggerFactory.getLogger(ActorNameResource.class);

    private static final String ENTITY_NAME = "actorName";

    private final ActorNameService actorNameService;

    public ActorNameResource(ActorNameService actorNameService) {
        this.actorNameService = actorNameService;
    }

    /**
     * POST  /actor-names : Create a new actorName.
     *
     * @param actorNameDTO the actorNameDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new actorNameDTO, or with status 400 (Bad Request) if the actorName has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/actor-names")
    public ResponseEntity<ActorNameDTO> createActorName(@Valid @RequestBody ActorNameDTO actorNameDTO) throws URISyntaxException {
        log.debug("REST request to save ActorName : {}", actorNameDTO);
        if (actorNameDTO.getId() != null) {
            throw new BadRequestAlertException("A new actorName cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActorNameDTO result = actorNameService.save(actorNameDTO);
        return ResponseEntity.created(new URI("/api/actor-names/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /actor-names : Updates an existing actorName.
     *
     * @param actorNameDTO the actorNameDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated actorNameDTO,
     * or with status 400 (Bad Request) if the actorNameDTO is not valid,
     * or with status 500 (Internal Server Error) if the actorNameDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/actor-names")
    public ResponseEntity<ActorNameDTO> updateActorName(@Valid @RequestBody ActorNameDTO actorNameDTO) throws URISyntaxException {
        log.debug("REST request to update ActorName : {}", actorNameDTO);
        if (actorNameDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActorNameDTO result = actorNameService.save(actorNameDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, actorNameDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /actor-names : get all the actorNames.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of actorNames in body
     */
    @GetMapping("/actor-names")
    public ResponseEntity<List<ActorNameDTO>> getAllActorNames(Pageable pageable) {
        log.debug("REST request to get a page of ActorNames");
        Page<ActorNameDTO> page = actorNameService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/actor-names");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /actor-names/:id : get the "id" actorName.
     *
     * @param id the id of the actorNameDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the actorNameDTO, or with status 404 (Not Found)
     */
    @GetMapping("/actor-names/{id}")
    public ResponseEntity<ActorNameDTO> getActorName(@PathVariable Long id) {
        log.debug("REST request to get ActorName : {}", id);
        Optional<ActorNameDTO> actorNameDTO = actorNameService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actorNameDTO);
    }

    /**
     * DELETE  /actor-names/:id : delete the "id" actorName.
     *
     * @param id the id of the actorNameDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/actor-names/{id}")
    public ResponseEntity<Void> deleteActorName(@PathVariable Long id) {
        log.debug("REST request to delete ActorName : {}", id);
        actorNameService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/actor-names?query=:query : search for the actorName corresponding
     * to the query.
     *
     * @param query the query of the actorName search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/actor-names")
    public ResponseEntity<List<ActorNameDTO>> searchActorNames(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of ActorNames for query {}", query);
        Page<ActorNameDTO> page = actorNameService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/actor-names");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
