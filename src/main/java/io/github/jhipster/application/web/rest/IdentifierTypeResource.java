package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.IdentifierType;
import io.github.jhipster.application.service.IdentifierTypeService;
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
 * REST controller for managing IdentifierType.
 */
@RestController
@RequestMapping("/api")
public class IdentifierTypeResource {

    private final Logger log = LoggerFactory.getLogger(IdentifierTypeResource.class);

    private static final String ENTITY_NAME = "identifierType";

    private final IdentifierTypeService identifierTypeService;

    public IdentifierTypeResource(IdentifierTypeService identifierTypeService) {
        this.identifierTypeService = identifierTypeService;
    }

    /**
     * POST  /identifier-types : Create a new identifierType.
     *
     * @param identifierType the identifierType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new identifierType, or with status 400 (Bad Request) if the identifierType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/identifier-types")
    public ResponseEntity<IdentifierType> createIdentifierType(@Valid @RequestBody IdentifierType identifierType) throws URISyntaxException {
        log.debug("REST request to save IdentifierType : {}", identifierType);
        if (identifierType.getId() != null) {
            throw new BadRequestAlertException("A new identifierType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        IdentifierType result = identifierTypeService.save(identifierType);
        return ResponseEntity.created(new URI("/api/identifier-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /identifier-types : Updates an existing identifierType.
     *
     * @param identifierType the identifierType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated identifierType,
     * or with status 400 (Bad Request) if the identifierType is not valid,
     * or with status 500 (Internal Server Error) if the identifierType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/identifier-types")
    public ResponseEntity<IdentifierType> updateIdentifierType(@Valid @RequestBody IdentifierType identifierType) throws URISyntaxException {
        log.debug("REST request to update IdentifierType : {}", identifierType);
        if (identifierType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        IdentifierType result = identifierTypeService.save(identifierType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, identifierType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /identifier-types : get all the identifierTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of identifierTypes in body
     */
    @GetMapping("/identifier-types")
    public List<IdentifierType> getAllIdentifierTypes() {
        log.debug("REST request to get all IdentifierTypes");
        return identifierTypeService.findAll();
    }

    /**
     * GET  /identifier-types/:id : get the "id" identifierType.
     *
     * @param id the id of the identifierType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the identifierType, or with status 404 (Not Found)
     */
    @GetMapping("/identifier-types/{id}")
    public ResponseEntity<IdentifierType> getIdentifierType(@PathVariable Long id) {
        log.debug("REST request to get IdentifierType : {}", id);
        Optional<IdentifierType> identifierType = identifierTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(identifierType);
    }

    /**
     * DELETE  /identifier-types/:id : delete the "id" identifierType.
     *
     * @param id the id of the identifierType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/identifier-types/{id}")
    public ResponseEntity<Void> deleteIdentifierType(@PathVariable Long id) {
        log.debug("REST request to delete IdentifierType : {}", id);
        identifierTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/identifier-types?query=:query : search for the identifierType corresponding
     * to the query.
     *
     * @param query the query of the identifierType search
     * @return the result of the search
     */
    @GetMapping("/_search/identifier-types")
    public List<IdentifierType> searchIdentifierTypes(@RequestParam String query) {
        log.debug("REST request to search IdentifierTypes for query {}", query);
        return identifierTypeService.search(query);
    }

}
