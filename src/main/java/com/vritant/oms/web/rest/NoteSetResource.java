package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.NoteSet;
import com.vritant.oms.repository.NoteSetRepository;
import com.vritant.oms.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing NoteSet.
 */
@RestController
@RequestMapping("/api")
public class NoteSetResource {

    private final Logger log = LoggerFactory.getLogger(NoteSetResource.class);
        
    @Inject
    private NoteSetRepository noteSetRepository;
    
    /**
     * POST  /noteSets -> Create a new noteSet.
     */
    @RequestMapping(value = "/noteSets",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteSet> createNoteSet(@RequestBody NoteSet noteSet) throws URISyntaxException {
        log.debug("REST request to save NoteSet : {}", noteSet);
        if (noteSet.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("noteSet", "idexists", "A new noteSet cannot already have an ID")).body(null);
        }
        NoteSet result = noteSetRepository.save(noteSet);
        return ResponseEntity.created(new URI("/api/noteSets/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("noteSet", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /noteSets -> Updates an existing noteSet.
     */
    @RequestMapping(value = "/noteSets",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteSet> updateNoteSet(@RequestBody NoteSet noteSet) throws URISyntaxException {
        log.debug("REST request to update NoteSet : {}", noteSet);
        if (noteSet.getId() == null) {
            return createNoteSet(noteSet);
        }
        NoteSet result = noteSetRepository.save(noteSet);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("noteSet", noteSet.getId().toString()))
            .body(result);
    }

    /**
     * GET  /noteSets -> get all the noteSets.
     */
    @RequestMapping(value = "/noteSets",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<NoteSet> getAllNoteSets() {
        log.debug("REST request to get all NoteSets");
        return noteSetRepository.findAll();
            }

    /**
     * GET  /noteSets/:id -> get the "id" noteSet.
     */
    @RequestMapping(value = "/noteSets/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<NoteSet> getNoteSet(@PathVariable Long id) {
        log.debug("REST request to get NoteSet : {}", id);
        NoteSet noteSet = noteSetRepository.findOne(id);
        return Optional.ofNullable(noteSet)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /noteSets/:id -> delete the "id" noteSet.
     */
    @RequestMapping(value = "/noteSets/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNoteSet(@PathVariable Long id) {
        log.debug("REST request to delete NoteSet : {}", id);
        noteSetRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("noteSet", id.toString())).build();
    }
}
