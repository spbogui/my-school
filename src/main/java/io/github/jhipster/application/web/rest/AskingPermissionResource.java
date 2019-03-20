package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.AskingPermissionService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.AskingPermissionDTO;
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
 * REST controller for managing AskingPermission.
 */
@RestController
@RequestMapping("/api")
public class AskingPermissionResource {

    private final Logger log = LoggerFactory.getLogger(AskingPermissionResource.class);

    private static final String ENTITY_NAME = "askingPermission";

    private final AskingPermissionService askingPermissionService;

    public AskingPermissionResource(AskingPermissionService askingPermissionService) {
        this.askingPermissionService = askingPermissionService;
    }

    /**
     * POST  /asking-permissions : Create a new askingPermission.
     *
     * @param askingPermissionDTO the askingPermissionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new askingPermissionDTO, or with status 400 (Bad Request) if the askingPermission has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/asking-permissions")
    public ResponseEntity<AskingPermissionDTO> createAskingPermission(@Valid @RequestBody AskingPermissionDTO askingPermissionDTO) throws URISyntaxException {
        log.debug("REST request to save AskingPermission : {}", askingPermissionDTO);
        if (askingPermissionDTO.getId() != null) {
            throw new BadRequestAlertException("A new askingPermission cannot already have an ID", ENTITY_NAME, "idexists");
        }
        AskingPermissionDTO result = askingPermissionService.save(askingPermissionDTO);
        return ResponseEntity.created(new URI("/api/asking-permissions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /asking-permissions : Updates an existing askingPermission.
     *
     * @param askingPermissionDTO the askingPermissionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated askingPermissionDTO,
     * or with status 400 (Bad Request) if the askingPermissionDTO is not valid,
     * or with status 500 (Internal Server Error) if the askingPermissionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/asking-permissions")
    public ResponseEntity<AskingPermissionDTO> updateAskingPermission(@Valid @RequestBody AskingPermissionDTO askingPermissionDTO) throws URISyntaxException {
        log.debug("REST request to update AskingPermission : {}", askingPermissionDTO);
        if (askingPermissionDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        AskingPermissionDTO result = askingPermissionService.save(askingPermissionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, askingPermissionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /asking-permissions : get all the askingPermissions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of askingPermissions in body
     */
    @GetMapping("/asking-permissions")
    public ResponseEntity<List<AskingPermissionDTO>> getAllAskingPermissions(Pageable pageable) {
        log.debug("REST request to get a page of AskingPermissions");
        Page<AskingPermissionDTO> page = askingPermissionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/asking-permissions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /asking-permissions/:id : get the "id" askingPermission.
     *
     * @param id the id of the askingPermissionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the askingPermissionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/asking-permissions/{id}")
    public ResponseEntity<AskingPermissionDTO> getAskingPermission(@PathVariable Long id) {
        log.debug("REST request to get AskingPermission : {}", id);
        Optional<AskingPermissionDTO> askingPermissionDTO = askingPermissionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(askingPermissionDTO);
    }

    /**
     * DELETE  /asking-permissions/:id : delete the "id" askingPermission.
     *
     * @param id the id of the askingPermissionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/asking-permissions/{id}")
    public ResponseEntity<Void> deleteAskingPermission(@PathVariable Long id) {
        log.debug("REST request to delete AskingPermission : {}", id);
        askingPermissionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/asking-permissions?query=:query : search for the askingPermission corresponding
     * to the query.
     *
     * @param query the query of the askingPermission search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/asking-permissions")
    public ResponseEntity<List<AskingPermissionDTO>> searchAskingPermissions(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of AskingPermissions for query {}", query);
        Page<AskingPermissionDTO> page = askingPermissionService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/asking-permissions");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
