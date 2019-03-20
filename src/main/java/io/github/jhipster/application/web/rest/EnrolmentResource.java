package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.service.EnrolmentService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
import io.github.jhipster.application.service.dto.EnrolmentDTO;
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
 * REST controller for managing Enrolment.
 */
@RestController
@RequestMapping("/api")
public class EnrolmentResource {

    private final Logger log = LoggerFactory.getLogger(EnrolmentResource.class);

    private static final String ENTITY_NAME = "enrolment";

    private final EnrolmentService enrolmentService;

    public EnrolmentResource(EnrolmentService enrolmentService) {
        this.enrolmentService = enrolmentService;
    }

    /**
     * POST  /enrolments : Create a new enrolment.
     *
     * @param enrolmentDTO the enrolmentDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new enrolmentDTO, or with status 400 (Bad Request) if the enrolment has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/enrolments")
    public ResponseEntity<EnrolmentDTO> createEnrolment(@Valid @RequestBody EnrolmentDTO enrolmentDTO) throws URISyntaxException {
        log.debug("REST request to save Enrolment : {}", enrolmentDTO);
        if (enrolmentDTO.getId() != null) {
            throw new BadRequestAlertException("A new enrolment cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EnrolmentDTO result = enrolmentService.save(enrolmentDTO);
        return ResponseEntity.created(new URI("/api/enrolments/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /enrolments : Updates an existing enrolment.
     *
     * @param enrolmentDTO the enrolmentDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated enrolmentDTO,
     * or with status 400 (Bad Request) if the enrolmentDTO is not valid,
     * or with status 500 (Internal Server Error) if the enrolmentDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/enrolments")
    public ResponseEntity<EnrolmentDTO> updateEnrolment(@Valid @RequestBody EnrolmentDTO enrolmentDTO) throws URISyntaxException {
        log.debug("REST request to update Enrolment : {}", enrolmentDTO);
        if (enrolmentDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EnrolmentDTO result = enrolmentService.save(enrolmentDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, enrolmentDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /enrolments : get all the enrolments.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of enrolments in body
     */
    @GetMapping("/enrolments")
    public ResponseEntity<List<EnrolmentDTO>> getAllEnrolments(Pageable pageable) {
        log.debug("REST request to get a page of Enrolments");
        Page<EnrolmentDTO> page = enrolmentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/enrolments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /enrolments/:id : get the "id" enrolment.
     *
     * @param id the id of the enrolmentDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the enrolmentDTO, or with status 404 (Not Found)
     */
    @GetMapping("/enrolments/{id}")
    public ResponseEntity<EnrolmentDTO> getEnrolment(@PathVariable Long id) {
        log.debug("REST request to get Enrolment : {}", id);
        Optional<EnrolmentDTO> enrolmentDTO = enrolmentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(enrolmentDTO);
    }

    /**
     * DELETE  /enrolments/:id : delete the "id" enrolment.
     *
     * @param id the id of the enrolmentDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/enrolments/{id}")
    public ResponseEntity<Void> deleteEnrolment(@PathVariable Long id) {
        log.debug("REST request to delete Enrolment : {}", id);
        enrolmentService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/enrolments?query=:query : search for the enrolment corresponding
     * to the query.
     *
     * @param query the query of the enrolment search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/enrolments")
    public ResponseEntity<List<EnrolmentDTO>> searchEnrolments(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Enrolments for query {}", query);
        Page<EnrolmentDTO> page = enrolmentService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/enrolments");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
