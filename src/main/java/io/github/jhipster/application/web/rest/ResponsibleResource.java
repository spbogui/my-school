package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.ResponsibleService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.ResponsibleDTO;
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
 * REST controller for managing Responsible.
 */
@RestController
@RequestMapping("/api")
public class ResponsibleResource {

    private final Logger log = LoggerFactory.getLogger(ResponsibleResource.class);

    private static final String ENTITY_NAME = "responsible";

    private final ResponsibleService responsibleService;

    public ResponsibleResource(ResponsibleService responsibleService) {
        this.responsibleService = responsibleService;
    }

    /**
     * POST  /responsibles : Create a new responsible.
     *
     * @param responsibleDTO the responsibleDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new responsibleDTO, or with status 400 (Bad Request) if the responsible has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/responsibles")
    public ResponseEntity<ResponsibleDTO> createResponsible(@RequestBody ResponsibleDTO responsibleDTO) throws URISyntaxException {
        log.debug("REST request to save Responsible : {}", responsibleDTO);
        if (responsibleDTO.getId() != null) {
            throw new BadRequestAlertException("A new responsible cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ResponsibleDTO result = responsibleService.save(responsibleDTO);
        return ResponseEntity.created(new URI("/api/responsibles/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /responsibles : Updates an existing responsible.
     *
     * @param responsibleDTO the responsibleDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated responsibleDTO,
     * or with status 400 (Bad Request) if the responsibleDTO is not valid,
     * or with status 500 (Internal Server Error) if the responsibleDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/responsibles")
    public ResponseEntity<ResponsibleDTO> updateResponsible(@RequestBody ResponsibleDTO responsibleDTO) throws URISyntaxException {
        log.debug("REST request to update Responsible : {}", responsibleDTO);
        if (responsibleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ResponsibleDTO result = responsibleService.save(responsibleDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, responsibleDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /responsibles : get all the responsibles.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of responsibles in body
     */
    @GetMapping("/responsibles")
    public ResponseEntity<List<ResponsibleDTO>> getAllResponsibles(Pageable pageable) {
        log.debug("REST request to get a page of Responsibles");
        Page<ResponsibleDTO> page = responsibleService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/responsibles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /responsibles/:id : get the "id" responsible.
     *
     * @param id the id of the responsibleDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the responsibleDTO, or with status 404 (Not Found)
     */
    @GetMapping("/responsibles/{id}")
    public ResponseEntity<ResponsibleDTO> getResponsible(@PathVariable Long id) {
        log.debug("REST request to get Responsible : {}", id);
        Optional<ResponsibleDTO> responsibleDTO = responsibleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(responsibleDTO);
    }

    /**
     * DELETE  /responsibles/:id : delete the "id" responsible.
     *
     * @param id the id of the responsibleDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/responsibles/{id}")
    public ResponseEntity<Void> deleteResponsible(@PathVariable Long id) {
        log.debug("REST request to delete Responsible : {}", id);
        responsibleService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/responsibles?query=:query : search for the responsible corresponding
     * to the query.
     *
     * @param query the query of the responsible search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/responsibles")
    public ResponseEntity<List<ResponsibleDTO>> searchResponsibles(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Responsibles for query {}", query);
        Page<ResponsibleDTO> page = responsibleService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/responsibles");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
