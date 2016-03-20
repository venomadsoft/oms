package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.Mill;
import com.vritant.oms.repository.MillRepository;
import com.vritant.oms.repository.QualityRepository;
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
 * REST controller for managing Mill.
 */
@RestController
@RequestMapping("/api")
public class MillResource {

    private final Logger log = LoggerFactory.getLogger(MillResource.class);
        
    @Inject
    private MillRepository millRepository;
    @Inject
    private QualityRepository qualityRepository;

    /**
     * POST  /mills -> Create a new mill.
     */
    @RequestMapping(value = "/mills",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mill> createMill(@Valid @RequestBody Mill mill) throws URISyntaxException {
        log.debug("REST request to save Mill : {}", mill);
        if (mill.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("mill", "idexists", "A new mill cannot already have an ID")).body(null);
        }
        Mill result = millRepository.save(mill);
        return ResponseEntity.created(new URI("/api/mills/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("mill", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /mills -> Updates an existing mill.
     */
    @RequestMapping(value = "/mills",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mill> updateMill(@Valid @RequestBody Mill mill) throws URISyntaxException {
        log.debug("REST request to update Mill : {}", mill);
        if (mill.getId() == null) {
            return createMill(mill);
        }
        Mill result = millRepository.save(mill);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("mill", mill.getId().toString()))
            .body(result);
    }

    /**
     * GET  /mills -> get all the mills.
     */
    @RequestMapping(value = "/mills",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Mill> getAllMills() {
        log.debug("REST request to get all Mills");
        return millRepository.findAll();
            }

    /**
     * GET  /mills/:id -> get the "id" mill.
     */
    @RequestMapping(value = "/mills/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Mill> getMill(@PathVariable Long id) {
        log.debug("REST request to get Mill : {}", id);
        return Optional.ofNullable(resolveMill(millRepository.findOne(id)))
            .map(mill -> new ResponseEntity<>(
                mill,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /mills/:id -> delete the "id" mill.
     */
    @RequestMapping(value = "/mills/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteMill(@PathVariable Long id) {
        log.debug("REST request to delete Mill : {}", id);
        millRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("mill", id.toString())).build();
    }

    private Mill resolveMill(Mill mill) {
        if(mill != null) {
            mill.setQualitiess(qualityRepository.findByMillId(mill.getId()));
        }
        return mill;
    }
}
