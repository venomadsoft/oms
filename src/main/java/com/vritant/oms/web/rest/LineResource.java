package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Line;
import com.vritant.oms.repository.LineRepository;
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
 * REST controller for managing Line.
 */
@RestController
@RequestMapping("/api")
public class LineResource {

    private final Logger log = LoggerFactory.getLogger(LineResource.class);

    @Inject
    private LineRepository lineRepository;

    /**
     * POST  /lines -> Create a new line.
     */
    @RequestMapping(value = "/lines",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Line> createLine(@Valid @RequestBody Line line) throws URISyntaxException {
        log.debug("REST request to save Line : {}", line);
        if (line.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new line cannot already have an ID").body(null);
        }
        Line result = lineRepository.save(line);
        return ResponseEntity.created(new URI("/api/lines/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("line", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /lines -> Updates an existing line.
     */
    @RequestMapping(value = "/lines",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Line> updateLine(@Valid @RequestBody Line line) throws URISyntaxException {
        log.debug("REST request to update Line : {}", line);
        if (line.getId() == null) {
            return createLine(line);
        }
        Line result = lineRepository.save(line);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("line", line.getId().toString()))
            .body(result);
    }

    /**
     * GET  /lines -> get all the lines.
     */
    @RequestMapping(value = "/lines",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Line> getAllLines() {
        log.debug("REST request to get all Lines");
        return lineRepository.findAll();
    }

    /**
     * GET  /lines/:id -> get the "id" line.
     */
    @RequestMapping(value = "/lines/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Line> getLine(@PathVariable Long id) {
        log.debug("REST request to get Line : {}", id);
        return Optional.ofNullable(lineRepository.findOne(id))
            .map(line -> new ResponseEntity<>(
                line,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /lines/:id -> delete the "id" line.
     */
    @RequestMapping(value = "/lines/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteLine(@PathVariable Long id) {
        log.debug("REST request to delete Line : {}", id);
        lineRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("line", id.toString())).build();
    }
}
