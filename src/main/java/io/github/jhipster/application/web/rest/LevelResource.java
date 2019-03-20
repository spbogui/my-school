package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.Level;
import io.github.jhipster.application.service.LevelService;
import io.github.jhipster.application.web.rest.errors.BadRequestAlertException;
import io.github.jhipster.application.web.rest.util.HeaderUtil;
import io.github.jhipster.application.web.rest.util.PaginationUtil;
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
 * REST controller for managing Level.
 */
@RestController
@RequestMapping("/api")
public class LevelResource {

    private final Logger log = LoggerFactory.getLogger(LevelResource.class);

    private static final String ENTITY_NAME = "level";

    private final LevelService levelService;

    public LevelResource(LevelService levelService) {
        this.levelService = levelService;
    }

    /**
     * POST  /levels : Create a new level.
     *
     * @param level the level to create
     * @return the ResponseEntity with status 201 (Created) and with body the new level, or with status 400 (Bad Request) if the level has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/levels")
    public ResponseEntity<Level> createLevel(@Valid @RequestBody Level level) throws URISyntaxException {
        log.debug("REST request to save Level : {}", level);
        if (level.getId() != null) {
            throw new BadRequestAlertException("A new level cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Level result = levelService.save(level);
        return ResponseEntity.created(new URI("/api/levels/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /levels : Updates an existing level.
     *
     * @param level the level to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated level,
     * or with status 400 (Bad Request) if the level is not valid,
     * or with status 500 (Internal Server Error) if the level couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/levels")
    public ResponseEntity<Level> updateLevel(@Valid @RequestBody Level level) throws URISyntaxException {
        log.debug("REST request to update Level : {}", level);
        if (level.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Level result = levelService.save(level);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, level.getId().toString()))
            .body(result);
    }

    /**
     * GET  /levels : get all the levels.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of levels in body
     */
    @GetMapping("/levels")
    public ResponseEntity<List<Level>> getAllLevels(Pageable pageable) {
        log.debug("REST request to get a page of Levels");
        Page<Level> page = levelService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/levels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /levels/:id : get the "id" level.
     *
     * @param id the id of the level to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the level, or with status 404 (Not Found)
     */
    @GetMapping("/levels/{id}")
    public ResponseEntity<Level> getLevel(@PathVariable Long id) {
        log.debug("REST request to get Level : {}", id);
        Optional<Level> level = levelService.findOne(id);
        return ResponseUtil.wrapOrNotFound(level);
    }

    /**
     * DELETE  /levels/:id : delete the "id" level.
     *
     * @param id the id of the level to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/levels/{id}")
    public ResponseEntity<Void> deleteLevel(@PathVariable Long id) {
        log.debug("REST request to delete Level : {}", id);
        levelService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/levels?query=:query : search for the level corresponding
     * to the query.
     *
     * @param query the query of the level search
     * @param pageable the pagination information
     * @return the result of the search
     */
    @GetMapping("/_search/levels")
    public ResponseEntity<List<Level>> searchLevels(@RequestParam String query, Pageable pageable) {
        log.debug("REST request to search for a page of Levels for query {}", query);
        Page<Level> page = levelService.search(query, pageable);
        HttpHeaders headers = PaginationUtil.generateSearchPaginationHttpHeaders(query, page, "/api/_search/levels");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

}
