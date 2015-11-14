package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.NoteType;
import com.vritant.oms.repository.NoteTypeRepository;
import com.vritant.oms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NoteType.
 */
@RestController
@RequestMapping("/api")
public class NoteTypeResource {

    private final Logger log = LoggerFactory.getLogger(NoteTypeResource.class);

    @Inject
    private NoteTypeRepository noteTypeRepository;

    /**
     * POST  /noteTypes -> Create a new noteType.
     */
    @RequestMapping(value = "/noteTypes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteType> createNoteType(@Valid @RequestBody NoteType noteType) throws URISyntaxException {
        log.debug("REST request to save NoteType : {}", noteType);
        if (noteType.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new noteType cannot already have an ID").body(null);
        }
        NoteType result = noteTypeRepository.save(noteType);
        return ResponseEntity.created(new URI("/api/noteTypes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("noteType", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noteTypes -> Updates an existing noteType.
     */
    @RequestMapping(value = "/noteTypes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteType> updateNoteType(@Valid @RequestBody NoteType noteType) throws URISyntaxException {
        log.debug("REST request to update NoteType : {}", noteType);
        if (noteType.getId() == null) {
            return createNoteType(noteType);
        }
        NoteType result = noteTypeRepository.save(noteType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("noteType", noteType.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noteTypes -> get all the noteTypes.
     */
    @RequestMapping(value = "/noteTypes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NoteType> getAllNoteTypes() {
        log.debug("REST request to get all NoteTypes");
        return noteTypeRepository.findAll();
    }

    /**
     * GET  /noteTypes/:id -> get the "id" noteType.
     */
    @RequestMapping(value = "/noteTypes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteType> getNoteType(@PathVariable Long id) {
        log.debug("REST request to get NoteType : {}", id);
        return Optional.ofNullable(noteTypeRepository.findOne(id))
            .map(noteType -> new ResponseEntity<>(
                noteType,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /noteTypes/:id -> delete the "id" noteType.
     */
    @RequestMapping(value = "/noteTypes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNoteType(@PathVariable Long id) {
        log.debug("REST request to delete NoteType : {}", id);
        noteTypeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("noteType", id.toString())).build();
    }
}
