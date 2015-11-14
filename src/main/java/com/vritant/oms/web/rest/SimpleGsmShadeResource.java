package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.SimpleGsmShade;
import com.vritant.oms.repository.SimpleGsmShadeRepository;
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
 * REST controller for managing SimpleGsmShade.
 */
@RestController
@RequestMapping("/api")
public class SimpleGsmShadeResource {

    private final Logger log = LoggerFactory.getLogger(SimpleGsmShadeResource.class);

    @Inject
    private SimpleGsmShadeRepository simpleGsmShadeRepository;

    /**
     * POST  /simpleGsmShades -> Create a new simpleGsmShade.
     */
    @RequestMapping(value = "/simpleGsmShades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SimpleGsmShade> createSimpleGsmShade(@Valid @RequestBody SimpleGsmShade simpleGsmShade) throws URISyntaxException {
        log.debug("REST request to save SimpleGsmShade : {}", simpleGsmShade);
        if (simpleGsmShade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new simpleGsmShade cannot already have an ID").body(null);
        }
        SimpleGsmShade result = simpleGsmShadeRepository.save(simpleGsmShade);
        return ResponseEntity.created(new URI("/api/simpleGsmShades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("simpleGsmShade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /simpleGsmShades -> Updates an existing simpleGsmShade.
     */
    @RequestMapping(value = "/simpleGsmShades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SimpleGsmShade> updateSimpleGsmShade(@Valid @RequestBody SimpleGsmShade simpleGsmShade) throws URISyntaxException {
        log.debug("REST request to update SimpleGsmShade : {}", simpleGsmShade);
        if (simpleGsmShade.getId() == null) {
            return createSimpleGsmShade(simpleGsmShade);
        }
        SimpleGsmShade result = simpleGsmShadeRepository.save(simpleGsmShade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("simpleGsmShade", simpleGsmShade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /simpleGsmShades -> get all the simpleGsmShades.
     */
    @RequestMapping(value = "/simpleGsmShades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<SimpleGsmShade> getAllSimpleGsmShades() {
        log.debug("REST request to get all SimpleGsmShades");
        return simpleGsmShadeRepository.findAll();
    }

    /**
     * GET  /simpleGsmShades/:id -> get the "id" simpleGsmShade.
     */
    @RequestMapping(value = "/simpleGsmShades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<SimpleGsmShade> getSimpleGsmShade(@PathVariable Long id) {
        log.debug("REST request to get SimpleGsmShade : {}", id);
        return Optional.ofNullable(simpleGsmShadeRepository.findOne(id))
            .map(simpleGsmShade -> new ResponseEntity<>(
                simpleGsmShade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /simpleGsmShades/:id -> delete the "id" simpleGsmShade.
     */
    @RequestMapping(value = "/simpleGsmShades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteSimpleGsmShade(@PathVariable Long id) {
        log.debug("REST request to delete SimpleGsmShade : {}", id);
        simpleGsmShadeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("simpleGsmShade", id.toString())).build();
    }
}
