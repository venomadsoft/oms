package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Note;
import com.vritant.oms.repository.NoteRepository;
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
 * REST controller for managing Note.
 */
@RestController
@RequestMapping("/api")
public class NoteResource {

    private final Logger log = LoggerFactory.getLogger(NoteResource.class);

    @Inject
    private NoteRepository noteRepository;

    /**
     * POST  /notes -> Create a new note.
     */
    @RequestMapping(value = "/notes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Note> createNote(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to save Note : {}", note);
        if (note.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new note cannot already have an ID").body(null);
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.created(new URI("/api/notes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("note", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /notes -> Updates an existing note.
     */
    @RequestMapping(value = "/notes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Note> updateNote(@RequestBody Note note) throws URISyntaxException {
        log.debug("REST request to update Note : {}", note);
        if (note.getId() == null) {
            return createNote(note);
        }
        Note result = noteRepository.save(note);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("note", note.getId().toString()))
            .body(result);
    }

    /**
     * GET  /notes -> get all the notes.
     */
    @RequestMapping(value = "/notes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Note> getAllNotes() {
        log.debug("REST request to get all Notes");
        return noteRepository.findAll();
    }

    /**
     * GET  /notes/:id -> get the "id" note.
     */
    @RequestMapping(value = "/notes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Note> getNote(@PathVariable Long id) {
        log.debug("REST request to get Note : {}", id);
        return Optional.ofNullable(noteRepository.findOne(id))
            .map(note -> new ResponseEntity<>(
                note,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /notes/:id -> delete the "id" note.
     */
    @RequestMapping(value = "/notes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteNote(@PathVariable Long id) {
        log.debug("REST request to delete Note : {}", id);
        noteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("note", id.toString())).build();
    }
}
