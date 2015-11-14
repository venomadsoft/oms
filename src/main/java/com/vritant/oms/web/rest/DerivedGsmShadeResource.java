package com.vritant.oms.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.vritant.oms.domain.DerivedGsmShade;
import com.vritant.oms.repository.DerivedGsmShadeRepository;
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
 * REST controller for managing DerivedGsmShade.
 */
@RestController
@RequestMapping("/api")
public class DerivedGsmShadeResource {

    private final Logger log = LoggerFactory.getLogger(DerivedGsmShadeResource.class);

    @Inject
    private DerivedGsmShadeRepository derivedGsmShadeRepository;

    /**
     * POST  /derivedGsmShades -> Create a new derivedGsmShade.
     */
    @RequestMapping(value = "/derivedGsmShades",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DerivedGsmShade> createDerivedGsmShade(@Valid @RequestBody DerivedGsmShade derivedGsmShade) throws URISyntaxException {
        log.debug("REST request to save DerivedGsmShade : {}", derivedGsmShade);
        if (derivedGsmShade.getId() != null) {
            return ResponseEntity.badRequest().header("Failure", "A new derivedGsmShade cannot already have an ID").body(null);
        }
        DerivedGsmShade result = derivedGsmShadeRepository.save(derivedGsmShade);
        return ResponseEntity.created(new URI("/api/derivedGsmShades/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("derivedGsmShade", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /derivedGsmShades -> Updates an existing derivedGsmShade.
     */
    @RequestMapping(value = "/derivedGsmShades",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DerivedGsmShade> updateDerivedGsmShade(@Valid @RequestBody DerivedGsmShade derivedGsmShade) throws URISyntaxException {
        log.debug("REST request to update DerivedGsmShade : {}", derivedGsmShade);
        if (derivedGsmShade.getId() == null) {
            return createDerivedGsmShade(derivedGsmShade);
        }
        DerivedGsmShade result = derivedGsmShadeRepository.save(derivedGsmShade);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("derivedGsmShade", derivedGsmShade.getId().toString()))
            .body(result);
    }

    /**
     * GET  /derivedGsmShades -> get all the derivedGsmShades.
     */
    @RequestMapping(value = "/derivedGsmShades",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<DerivedGsmShade> getAllDerivedGsmShades() {
        log.debug("REST request to get all DerivedGsmShades");
        return derivedGsmShadeRepository.findAll();
    }

    /**
     * GET  /derivedGsmShades/:id -> get the "id" derivedGsmShade.
     */
    @RequestMapping(value = "/derivedGsmShades/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<DerivedGsmShade> getDerivedGsmShade(@PathVariable Long id) {
        log.debug("REST request to get DerivedGsmShade : {}", id);
        return Optional.ofNullable(derivedGsmShadeRepository.findOne(id))
            .map(derivedGsmShade -> new ResponseEntity<>(
                derivedGsmShade,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /derivedGsmShades/:id -> delete the "id" derivedGsmShade.
     */
    @RequestMapping(value = "/derivedGsmShades/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteDerivedGsmShade(@PathVariable Long id) {
        log.debug("REST request to delete DerivedGsmShade : {}", id);
        derivedGsmShadeRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("derivedGsmShade", id.toString())).build();
    }
}
