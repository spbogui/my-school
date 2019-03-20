package io.github.jhipster.application.web.rest;
import io.github.jhipster.application.domain.RoomType;
import io.github.jhipster.application.service.RoomTypeService;
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
 * REST controller for managing RoomType.
 */
@RestController
@RequestMapping("/api")
public class RoomTypeResource {

    private final Logger log = LoggerFactory.getLogger(RoomTypeResource.class);

    private static final String ENTITY_NAME = "roomType";

    private final RoomTypeService roomTypeService;

    public RoomTypeResource(RoomTypeService roomTypeService) {
        this.roomTypeService = roomTypeService;
    }

    /**
     * POST  /room-types : Create a new roomType.
     *
     * @param roomType the roomType to create
     * @return the ResponseEntity with status 201 (Created) and with body the new roomType, or with status 400 (Bad Request) if the roomType has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/room-types")
    public ResponseEntity<RoomType> createRoomType(@Valid @RequestBody RoomType roomType) throws URISyntaxException {
        log.debug("REST request to save RoomType : {}", roomType);
        if (roomType.getId() != null) {
            throw new BadRequestAlertException("A new roomType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RoomType result = roomTypeService.save(roomType);
        return ResponseEntity.created(new URI("/api/room-types/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /room-types : Updates an existing roomType.
     *
     * @param roomType the roomType to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated roomType,
     * or with status 400 (Bad Request) if the roomType is not valid,
     * or with status 500 (Internal Server Error) if the roomType couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/room-types")
    public ResponseEntity<RoomType> updateRoomType(@Valid @RequestBody RoomType roomType) throws URISyntaxException {
        log.debug("REST request to update RoomType : {}", roomType);
        if (roomType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RoomType result = roomTypeService.save(roomType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, roomType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /room-types : get all the roomTypes.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of roomTypes in body
     */
    @GetMapping("/room-types")
    public List<RoomType> getAllRoomTypes() {
        log.debug("REST request to get all RoomTypes");
        return roomTypeService.findAll();
    }

    /**
     * GET  /room-types/:id : get the "id" roomType.
     *
     * @param id the id of the roomType to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the roomType, or with status 404 (Not Found)
     */
    @GetMapping("/room-types/{id}")
    public ResponseEntity<RoomType> getRoomType(@PathVariable Long id) {
        log.debug("REST request to get RoomType : {}", id);
        Optional<RoomType> roomType = roomTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(roomType);
    }

    /**
     * DELETE  /room-types/:id : delete the "id" roomType.
     *
     * @param id the id of the roomType to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/room-types/{id}")
    public ResponseEntity<Void> deleteRoomType(@PathVariable Long id) {
        log.debug("REST request to delete RoomType : {}", id);
        roomTypeService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }

    /**
     * SEARCH  /_search/room-types?query=:query : search for the roomType corresponding
     * to the query.
     *
     * @param query the query of the roomType search
     * @return the result of the search
     */
    @GetMapping("/_search/room-types")
    public List<RoomType> searchRoomTypes(@RequestParam String query) {
        log.debug("REST request to search RoomTypes for query {}", query);
        return roomTypeService.search(query);
    }

}
