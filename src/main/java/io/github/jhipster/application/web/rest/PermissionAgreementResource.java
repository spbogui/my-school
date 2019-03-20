package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.PermissionAgreementService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.PermissionAgreementDTO;
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
 * REST controller for managing PermissionAgreement.
 */
@RestController
@RequestMapping("/api")
public class PermissionAgreementResource {

    private final Logger log = LoggerFactory.getLogger(PermissionAgreementResource.class);

    private static final String ENTITY_NAME = "permissionAgreement";

    private final PermissionAgreementService permissionAgreementService;

    public PermissionAgreementResource(PermissionAgreementService permissionAgreementService) {
        this.permissionAgreementService = permissionAgreementService;
    }

    /**
     * POST  /permission-agreements : Create a new permissionAgreement.
     *
     * @param permissionAgreementDTO the permissionAgreementDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new permissionAgreementDTO, or with status 400 (Bad Request) if the permissionAgreement has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/permission-agreements")
    public ResponseEntity<PermissionAgreementDTO> createPermissionAgreement(@Valid @RequestBody PermissionAgreementDTO permissionAgreementDTO) throws URISyntaxException {
        log.debug("REST request to save PermissionAgreement : {}", permissionAgreementDTO);
        if (permissionAgreementDTO.getId() != null) {
            throw new BadRequestAlertException("A new permissionAgreement cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PermissionAgreementDTO result = permissionAgreementService.save(permissionAgreementDTO);
        return ResponseEntity.created(new URI("/api/permission-agreements/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /permission-agreements : Updates an existing permissionAgreement.
     *
     * @param permissionAgreementDTO the permissionAgreementDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated permissionAgreementDTO,
     * or with status 400 (Bad Request) if the permissionAgreementDTO is not valid,
     * or with status 500 (Internal Server Error) if the permissionAgreementDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/permission-agreements")
    public ResponseEntity<PermissionAgreementDTO> updatePermissionAgreement(@Valid @RequestBody PermissionAgreementDTO permissionAgreementDTO) throws URISyntaxException {
        log.debug("REST request to update PermissionAgreement : {}", permissionAgreementDTO);
        if (permissionAgreementDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        PermissionAgreementDTO result = permissionAgreementService.save(permissionAgreementDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, permissionAgreementDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /permission-agreements : get all the permissionAgreements.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of permissionAgreements in body
     */
    @GetMapping("/permission-agreements")
    public ResponseEntity<List<PermissionAgreementDTO>> getAllPermissionAgreements(Pageable pageable) {
        log.debug("REST request to get a page of PermissionAgreements");
        Page<PermissionAgreementDTO> page = permissionAgreementService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/permission-agreements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /permission-agreements/:id : get the "id" permissionAgreement.
     *
     * @param id the id of the permissionAgreementDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the permissionAgreementDTO, or with status 404 (Not Found)
     */
    @GetMapping("/permission-agreements/{id}")
    public ResponseEntity<PermissionAgreementDTO> getPermissionAgreement(@PathVariable Long id) {
        log.debug("REST request to get PermissionAgreement : {}", id);
        Optional<PermissionAgreementDTO> permissionAgreementDTO = permissionAgreementService.findOne(id);
        return ResponseUtil.wrapOrNotFound(permissionAgreementDTO);
    }

    /**
     * DELETE  /permission-agreements/:id : delete the "id" permissionAgreement.
     *
     * @param id the id of the permissionAgreementDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/permission-agreements/{id}")
    public ResponseEntity<Void> deletePermissionAgreement(@PathVariable Long id) {
        log.debug("REST request to delete PermissionAgreement : {}", id);
        permissionAgreementService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/permission-agreements?query=:query : search for the permissionAgreement corresponding
     * to the query.
     *
     * @param query the query of the permissionAgreement search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/permission-agreements")
    public ResponseEntity<List<PermissionAgreementDTO>> searchPermissionAgreements(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of PermissionAgreements for query {}", query);
        Page<PermissionAgreementDTO> page = permissionAgreementService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/permission-agreements");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
