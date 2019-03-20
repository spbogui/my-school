package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.DiplomaService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.DiplomaDTO;
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
 * REST controller for managing Diploma.
 */
@RestController
@RequestMapping("/api")
public class DiplomaResource {

    private final Logger log = LoggerFactory.getLogger(DiplomaResource.class);

    private static final String ENTITY_NAME = "diploma";

    private final DiplomaService diplomaService;

    public DiplomaResource(DiplomaService diplomaService) {
        this.diplomaService = diplomaService;
    }

    /**
     * POST  /diplomas : Create a new diploma.
     *
     * @param diplomaDTO the diplomaDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new diplomaDTO, or with status 400 (Bad Request) if the diploma has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/diplomas")
    public ResponseEntity<DiplomaDTO> createDiploma(@Valid @RequestBody DiplomaDTO diplomaDTO) throws URISyntaxException {
        log.debug("REST request to save Diploma : {}", diplomaDTO);
        if (diplomaDTO.getId() != null) {
            throw new BadRequestAlertException("A new diploma cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiplomaDTO result = diplomaService.save(diplomaDTO);
        return ResponseEntity.created(new URI("/api/diplomas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /diplomas : Updates an existing diploma.
     *
     * @param diplomaDTO the diplomaDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated diplomaDTO,
     * or with status 400 (Bad Request) if the diplomaDTO is not valid,
     * or with status 500 (Internal Server Error) if the diplomaDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/diplomas")
    public ResponseEntity<DiplomaDTO> updateDiploma(@Valid @RequestBody DiplomaDTO diplomaDTO) throws URISyntaxException {
        log.debug("REST request to update Diploma : {}", diplomaDTO);
        if (diplomaDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        DiplomaDTO result = diplomaService.save(diplomaDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, diplomaDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /diplomas : get all the diplomas.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of diplomas in body
     */
    @GetMapping("/diplomas")
    public ResponseEntity<List<DiplomaDTO>> getAllDiplomas(Pageable pageable) {
        log.debug("REST request to get a page of Diplomas");
        Page<DiplomaDTO> page = diplomaService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/diplomas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /diplomas/:id : get the "id" diploma.
     *
     * @param id the id of the diplomaDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the diplomaDTO, or with status 404 (Not Found)
     */
    @GetMapping("/diplomas/{id}")
    public ResponseEntity<DiplomaDTO> getDiploma(@PathVariable Long id) {
        log.debug("REST request to get Diploma : {}", id);
        Optional<DiplomaDTO> diplomaDTO = diplomaService.findOne(id);
        return ResponseUtil.wrapOrNotFound(diplomaDTO);
    }

    /**
     * DELETE  /diplomas/:id : delete the "id" diploma.
     *
     * @param id the id of the diplomaDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/diplomas/{id}")
    public ResponseEntity<Void> deleteDiploma(@PathVariable Long id) {
        log.debug("REST request to delete Diploma : {}", id);
        diplomaService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/diplomas?query=:query : search for the diploma corresponding
     * to the query.
     *
     * @param query the query of the diploma search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/diplomas")
    public ResponseEntity<List<DiplomaDTO>> searchDiplomas(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Diplomas for query {}", query);
        Page<DiplomaDTO> page = diplomaService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/diplomas");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
